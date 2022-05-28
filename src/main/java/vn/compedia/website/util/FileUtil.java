package vn.compedia.website.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileUtil {
    private static Logger log = LoggerFactory.getLogger(CommonUtil.class);

    public final static String EXT_PDF = "pdf";
    private static final String SEPARATOR = "/";
    public final static String EXT_EXCEL = "xlsx,xls,XLSX,XLS";
    public final static String EXT_OFFICE = "xls,xlsx,doc,docx,ppt";

    public static String saveFile(UploadedFile uploadedFile) {
        return save(uploadedFile);
    }

    public static String saveImageFile(UploadedFile uploadedFile) {
        return saveResize(uploadedFile);
    }

    public static String getFolder() {
        String todayFolder = DateUtil.getTodayFolder();
        String folder = todayFolder + File.separator;
        File inFiles = new File(folder);
        if (!inFiles.exists() && !inFiles.mkdirs()) {
            log.error("Can't create folder");
        }
        return folder;
    }

    private static String save(Part uploadedFile) {
        String todayFolder = DateUtil.getTodayFolder();
        String fileId = generateFileId();

        String folder = getFolder();
        File file = new File(folder + File.separator + fileId + "." + FilenameUtils.getExtension(uploadedFile.getSubmittedFileName()));
        try {
            if (file.exists() && file.delete()) {
                log.info("Delete file with exists!");
            }
            FileUtils.copyInputStreamToFile(uploadedFile.getInputStream(), file);
            return todayFolder + SEPARATOR + fileId + "." + FilenameUtils.getExtension(uploadedFile.getSubmittedFileName());
        } catch (IOException e) {
            log.error("Save file error", e);
            return null;
        }
    }

    private static String save(UploadedFile uploadedFile) {
        String todayFolder = DateUtil.getTodayFolder();
        String fileId = generateFileId();

        String folder = getFolder();
        File file = new File(folder + File.separator + fileId + "." + FilenameUtils.getExtension(uploadedFile.getFileName()));
        try {
            if (file.exists() && file.delete()) {
                log.info("Delete file with exists!");
            }
            FileUtils.copyInputStreamToFile(uploadedFile.getInputStream(), file);
            return todayFolder + SEPARATOR + fileId + "." + FilenameUtils.getExtension(uploadedFile.getFileName());
        } catch (IOException e) {
            log.error("Save file error", e);
            return null;
        }
    }

    private static String saveResize(UploadedFile uploadedFile) {
        String todayFolder = DateUtil.getTodayFolder();
        String fileId = generateFileId();

        String folder = getFolder();
        File file = new File(folder + File.separator + fileId + "." + FilenameUtils.getExtension(uploadedFile.getFileName()));
        String path = folder + fileId + "." + FilenameUtils.getExtension(uploadedFile.getFileName());
        double percent = 1;
        try {
            if (file.exists() && file.delete()) {
                log.info("Delete file with exists!");
            }
            resize(uploadedFile, path, percent);
            return todayFolder + File.separator + fileId + "." + FilenameUtils.getExtension(uploadedFile.getFileName());
        } catch (IOException e) {
            log.error("Save file error", e);
            return null;
        }
    }

    public static void resize(UploadedFile uploadedFile, String path, double percent) throws IOException {
        byte[] image = uploadedFile.getContent();
        BufferedImage inputImage = ImageIO.read(new ByteArrayInputStream(image));
        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        resize(uploadedFile, path, scaledWidth, scaledHeight);
    }

    public static void resize(UploadedFile uploadedFile, String path, int scaledWidth, int scaledHeight) throws IOException {
        // Reads input image
        byte[] image = uploadedFile.getContent();
        BufferedImage inputImage = ImageIO.read(new ByteArrayInputStream(image));

        // Creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // Scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // Extracts extension of output file
        String formatName = path.substring(path.lastIndexOf(".") + 1);

        // Writes to output file
        ImageIO.write(outputImage, formatName, new File(path));
    }

    public static void remove(String path) {
        try {
            Files.deleteIfExists(Paths.get(FacesUtil.getServletContext().getRealPath(path)));
        } catch (Exception e) {
            log.error("Remove file error", e);
        }
    }

    public static void remove(List<String> paths) {
        try {
            for (String path : paths) {
                FileUtil.remove(path);
            }
        } catch (Exception e) {
            log.error("Remove file error", e);
        }
    }

    // Get stream for download file from database
    public static StreamedContent getDownloadFileFromDatabase(String databaseFilePath) {
        return getDownloadFile(FacesUtil.getServletContext().getRealPath(getFilePathFromDatabase(databaseFilePath)));
    }

    // Get stream for download file
    private static StreamedContent getDownloadFile(String filePath) {
        try {
            return new DefaultStreamedContent(
                    new FileInputStream(new File(filePath)),
                    FacesContext.getCurrentInstance().getExternalContext().getMimeType(filePath),
                    FilenameUtils.getName(filePath));
        } catch (FileNotFoundException e) {
            log.error("Download file error", e);
        }
        return null;
    }

    // Get file download and set again real name file before download
    public static StreamedContent getDownloadFileFromDatabase(String databaseFilePath, String dataBaseFileName) {
        return getDownloadFile(FacesUtil.getServletContext().getRealPath(getFilePathFromDatabase(databaseFilePath)), dataBaseFileName);
    }

    // Get stream for download file
    private static StreamedContent getDownloadFile(String filePath, String fileName) {
        try {
            return new DefaultStreamedContent(
                    new FileInputStream(new File(filePath)),
                    FacesContext.getCurrentInstance().getExternalContext().getMimeType(filePath),
                    FilenameUtils.getName(fileName != null ? fileName : filePath));
        } catch (FileNotFoundException e) {
            log.error("Download file error", e);
        }
        return null;
    }

    public static String getFilePathFromDatabase(String databaseFilePath) {
        return databaseFilePath;
    }

    // Create file id (unique)
    private synchronized static String generateFileId() {
        return DateUtil.getCurrentDateStr() + RandomStringUtils.randomAlphanumeric(16);
    }

    public static String getFileExtFromFileName(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static boolean isPdfFileExt(String fileName) {
        return StringUtils.equalsIgnoreCase(getFileExtFromFileName(fileName), EXT_PDF);
    }

    public static boolean isOfficeFileExt(String fileName) {
        return StringUtils.contains(EXT_OFFICE, getFileExtFromFileName(fileName).toLowerCase());
    }

    public static boolean isExcelFileExt(String fileName) {
        if (StringUtils.isBlank(EXT_EXCEL) || StringUtils.isBlank(fileName)) {
            return false;
        }
        List<String> fileTypeList = Arrays.asList(EXT_EXCEL.split(","));
        return fileTypeList.contains(getFileExtFromFileName(fileName));
    }

    public static boolean isAcceptFileType(String fileName) {
        String fileTypsString = getAcceptFileString();
        if (StringUtils.isBlank(fileTypsString) || StringUtils.isBlank(fileName)) {
            return false;
        }
        List<String> fileTypeList = Arrays.asList(fileTypsString.split(","));
        return fileTypeList.contains(getFileExtFromFileName(fileName));
    }

    public static String getAcceptFileString() {
        return PropertiesUtil.getProperty("accept_file_types");
    }

    public static String getAcceptFileAudioString() {
        return PropertiesUtil.getProperty("accept_file_audio_types");
    }

    public static boolean isAcceptFilePDFType(String fileName) {
        String fileTypeString = getAcceptFilePDFString();
        if (StringUtils.isBlank(fileTypeString) || StringUtils.isBlank(fileName)) {
            return false;
        }
        List<String> fileTypeList = Arrays.asList(fileTypeString.split(","));
        return fileTypeList.contains(getFileExtFromFileName(fileName));
    }

    public static boolean isAcceptFilePDFAndDocxType(String fileName) {
        String fileTypeString = getAcceptFilePDFAndDOCXString();
        if (StringUtils.isBlank(fileTypeString) || StringUtils.isBlank(fileName)) {
            return false;
        }
        List<String> fileTypeList = Arrays.asList(fileTypeString.split(","));
        return fileTypeList.contains(getFileExtFromFileName(fileName));
    }

    public static String getAcceptFilePDFString() {
        return PropertiesUtil.getProperty("accept_file_types_pdf");
    }

    public static String getAcceptFilePDFAndDOCXString() {
        return PropertiesUtil.getProperty("accept_file_types_pdf_docx");
    }

    public static boolean isAcceptImageType(String fileName) {
        String fileTypeString = getAcceptImageString();
        if (StringUtils.isBlank(fileTypeString) || StringUtils.isBlank(fileName)) {
            return false;
        }
        List<String> fileTypeList = Arrays.asList(fileTypeString.split(","));
        return fileTypeList.contains(getFileExtFromFileName(fileName));
    }

    public static boolean isAcceptImageType(UploadedFile uploadedFile) {
        return !isAcceptImageType(uploadedFile.getFileName().toLowerCase());
    }

    public static String getAcceptImageString() {
        return PropertiesUtil.getProperty("accept_image_types");
    }

    public static boolean isAudioFileExt(String fileName) {
        String fileTypeString = getAcceptFileAudioString();
        if (StringUtils.isBlank(fileTypeString) || StringUtils.isBlank(fileName)) {
            return false;
        }
        List<String> fileTypeList = Arrays.asList(fileTypeString.split(","));
        return fileTypeList.contains(getFileExtFromFileName(fileName));
    }

    public static boolean isAcceptFileAudioType(UploadedFile uploadedFile) {
        return isAudioFileExt(uploadedFile.getFileName().toLowerCase());
    }
}
