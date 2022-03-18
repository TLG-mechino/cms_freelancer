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

    public final static String EXT_PDF = "pdf";
    public final static String EXT_ZIP = "zip";
    public final static String EXT_EXCEL = "xlsx,xls,XLSX,XLS";
    public final static String EXT_AUDIO = "mp4,mp3,wma,wav,flac,aac,ogg";
    public final static String EXT_OFFICE = "xls,xlsx,doc,docx,ppt";
    public static final String FOLDER_STORAGE_FILE = "storage_file";
    private static final String FOLDER_NAME_PARENT = "resources";
    private static final String FOLDER_NAME_IMAGE = "upload";
    private static final String FOLDER_NAME_FILE = "upload_file";
    private static final String SEPARATOR = "/";
    public static String pathReturn = "";
    public static String[] FILE_EXCEL = {"xls", "xlsx", "xlsm"};
    private static Logger log = LoggerFactory.getLogger(CommonUtil.class);

    // Save file if success then return file path, else return null
    public static String saveFile(UploadedFile uploadedFile) {
        return save(uploadedFile, FOLDER_NAME_FILE);
    }

    // Save file if success then return file path, else return null
    public static String saveFile(Part uploadedFile) {
        return save(uploadedFile, FOLDER_NAME_FILE);
    }

    public static String saveImageFile(UploadedFile uploadedFile) throws IOException {
        return saveResize(uploadedFile, FOLDER_NAME_IMAGE);
    }

    public static String getFolder(String folderName) {
        String todayFolder = DateUtil.getTodayFolder();
        String folder = FacesUtil.getServletContext().getRealPath(
                File.separator + FOLDER_NAME_PARENT
                        + File.separator + folderName
                        + File.separator + todayFolder
                        + File.separator);
        File inFiles = new File(folder);
        if (!inFiles.exists() && !inFiles.mkdirs()) {
            log.error("Can't create folder");
        }
        return folder;
    }

    public static String getAccpetFileString() {
        return PropertiesUtil.getProperty("accept_file_types_pdf");
    }

    private static String save(Part uploadedFile, String folderName) {
        String todayFolder = DateUtil.getTodayFolder();
        String fileId = generateFileId();

        String folder = getFolder(folderName);
        File file = new File(folder + File.separator + fileId + "." + FilenameUtils.getExtension(uploadedFile.getSubmittedFileName()));
        try {
            if (file.exists()) {
                file.delete();
            }
            FileUtils.copyInputStreamToFile(uploadedFile.getInputStream(), file);
            return SEPARATOR + folderName + SEPARATOR + todayFolder + SEPARATOR + fileId + "." + FilenameUtils.getExtension(uploadedFile.getSubmittedFileName());
        } catch (IOException e) {
            log.error("Save file error", e);
            return null;
        }
    }

    private static String save(UploadedFile uploadedFile, String folderName) {
        String todayFolder = DateUtil.getTodayFolder();
        String fileId = generateFileId();

        String folder = getFolder(folderName);
        File file = new File(folder + File.separator + fileId + "." + FilenameUtils.getExtension(uploadedFile.getFileName()));
        try {
            if (file.exists()) {
                file.delete();
            }
            FileUtils.copyInputStreamToFile(uploadedFile.getInputStream(), file);
            return SEPARATOR + folderName + SEPARATOR + todayFolder + SEPARATOR + fileId + "." + FilenameUtils.getExtension(uploadedFile.getFileName());
        } catch (IOException e) {
            log.error("Save file error", e);
            return null;
        }
    }

    private static String saveResize(UploadedFile uploadedFile, String folderName) throws IOException {
        String todayFolder = DateUtil.getTodayFolder();
        String fileId = generateFileId();

        String folder = getFolder(folderName);
        File file = new File(folder + File.separator + fileId + "." + FilenameUtils.getExtension(uploadedFile.getFileName()));
        String path = folder + File.separator + fileId + "." + FilenameUtils.getExtension(uploadedFile.getFileName());
        double percent = 0.86;
        try {
            if (file.exists()) {
                file.delete();
            }
            resize(uploadedFile, path, percent);
            return SEPARATOR + folderName + SEPARATOR + todayFolder + SEPARATOR + fileId + "." + FilenameUtils.getExtension(uploadedFile.getFileName());
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
        // reads input image
        byte[] image = uploadedFile.getContent();
        BufferedImage inputImage = ImageIO.read(new ByteArrayInputStream(image));

        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // extracts extension of output file
        String formatName = path.substring(path
                .lastIndexOf(".") + 1);

        // writes to output file
        ImageIO.write(outputImage, formatName, new File(path));
    }

    public static String copyFileToStorage(String srcFile) {
        String todayFolder = DateUtil.getTodayFolder();
        String fileId = generateFileId();

        String folder = getFolder(FOLDER_STORAGE_FILE);
        File file = new File(folder + File.separator + fileId + "." + FilenameUtils.getExtension(srcFile));
        try {
            if (file.exists()) {
                file.delete();
            }
            String newFile = SEPARATOR + FOLDER_STORAGE_FILE + SEPARATOR + todayFolder + SEPARATOR + fileId + "." + FilenameUtils.getExtension(srcFile);
            FileUtils.copyFile(new File(srcFile), file);
            return newFile;
        } catch (IOException e) {
            log.error("Save file error", e);
            return null;
        }
    }

    public static void remove(String path) {
        try {
            Files.deleteIfExists(Paths.get(FacesUtil.getServletContext().getRealPath(File.separator + FOLDER_NAME_PARENT + path)));
        } catch (Exception e) {
            log.error("Remove file error", e);
            return;
        }
    }

    public static void remove(List<String> paths) {
        try {
            for (String path : paths) {
                FileUtil.remove(path);
            }
        } catch (Exception e) {
            log.error("Remove file error", e);
            return;
        }
    }

    public static String clone(String databaseFilePath, boolean isImage) {
        String oldPath = getFilePathFromDatabase(databaseFilePath);
        String todayFolder = DateUtil.getTodayFolder();
        String fileId = generateFileId();
        String folder = isImage ? FOLDER_NAME_IMAGE : FOLDER_NAME_FILE;
        String newRealPath = File.separator + folder + File.separator + todayFolder + File.separator + fileId + "." + FilenameUtils.getExtension(oldPath);
        String newPath = SEPARATOR + folder + SEPARATOR + todayFolder + SEPARATOR + fileId + "." + FilenameUtils.getExtension(oldPath);
        if (new File(FacesUtil.getServletContext().getRealPath(oldPath)).exists()) {
            try {
                Files.copy(Paths.get(FacesUtil.getServletContext().getRealPath(oldPath)), Paths.get(FacesUtil.getServletContext().getRealPath(getFilePathFromDatabase(newRealPath))));
                return newPath;
            } catch (IOException e) {
                log.error("Clone file error", e);
                return newPath;
            }
        } else {
            return newPath;
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
        }
        return null;
    }

    public static FileInputStream getInputStream(String filePath) {
        try {
            return new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {

        }
        return null;
    }

    public static String getFilePathFromDatabase(String databaseFilePath) {
        return SEPARATOR + FOLDER_NAME_PARENT + SEPARATOR + databaseFilePath;
    }

    // Get only file name
    public static String getFilenameFromFilePath(String databaseFilePath) {
        if (StringUtils.isBlank(databaseFilePath)) {
            return "";
        }
        return databaseFilePath.substring(databaseFilePath.lastIndexOf(SEPARATOR) + 1);
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

    public static String setWorkFolderExportDoc() {
        return PropertiesUtil.getProperty("work_folder");
    }

    public static boolean isAcceptFileType(String fileName) {
        String fileTypsString = getAcceptFileString();
        if (StringUtils.isBlank(fileTypsString) || StringUtils.isBlank(fileName)) {
            return false;
        }
        List<String> fileTypeList = Arrays.asList(fileTypsString.split(","));
        return fileTypeList.contains(getFileExtFromFileName(fileName));
    }

    public static boolean isAcceptFileType(UploadedFile uploadedFile) {
        return !isAcceptFileType(uploadedFile.getFileName().toLowerCase());
    }

    public static String getAcceptFileString() {
        return PropertiesUtil.getProperty("accept_file_types");
    }

    public static String getAcceptFileAudioString() {
        return PropertiesUtil.getProperty("accept_file_audio_types");
    }

    public static String getAcceptFileImageString() {
        return PropertiesUtil.getProperty("accept_image_types");
    }

    // PDF
    public static boolean isAcceptFilePDFType(String fileName) {
        String fileTypeString = getAccpetFilePDFString();
        if (StringUtils.isBlank(fileTypeString) || StringUtils.isBlank(fileName)) {
            return false;
        }
        List<String> fileTypeList = Arrays.asList(fileTypeString.split(","));
        return fileTypeList.contains(getFileExtFromFileName(fileName));
    }

    public static boolean isAcceptFilePDFAndDocxType(String fileName) {
        String fileTypeString = getAccpetFilePDFAndDOCXString();
        if (StringUtils.isBlank(fileTypeString) || StringUtils.isBlank(fileName)) {
            return false;
        }
        List<String> fileTypeList = Arrays.asList(fileTypeString.split(","));
        return fileTypeList.contains(getFileExtFromFileName(fileName));
    }

    public static boolean isAcceptFilePDFType(UploadedFile uploadedFile) {
        return isAcceptFilePDFType(uploadedFile.getFileName().toLowerCase());
    }

    public static boolean isAcceptFilePDFAndDocxType(UploadedFile uploadedFile) {
        return isAcceptFilePDFAndDocxType(uploadedFile.getFileName().toLowerCase());
    }

    public static String getAccpetFilePDFString() {
        return PropertiesUtil.getProperty("accept_file_types_pdf");
    }

    public static String getAccpetFilePDFAndDOCXString() {
        return PropertiesUtil.getProperty("accept_file_types_pdf_docx");
    }

    public static String getAccpetFileImageString() {
        return PropertiesUtil.getProperty("accept_image_types");
    }

    public static void deleteFileByListPath(List<String> fileList) {
        for (String filePath : fileList) {
            File file = new File(filePath);
            if (file.exists()) {
                boolean a = file.delete();
                System.out.println(a);
            }
        }
    }

    // Image
    public static boolean isAcceptImageType(String fileName) {
        String fileTypeString = getAcceptImageString();
        if (StringUtils.isBlank(fileTypeString) || StringUtils.isBlank(fileName)) {
            return false;
        }
        List<String> fileTypeList = Arrays.asList(fileTypeString.split(","));
        return fileTypeList.contains(getFileExtFromFileName(fileName));
    }

    public static boolean isAcceptImageType(UploadedFile uploadedFile) {
        return isAcceptImageType(uploadedFile.getFileName().toLowerCase());
    }

    public static String getAcceptImageString() {
        return PropertiesUtil.getProperty("accept_image_types");
    }

    public static boolean isZipFileExt(String fileName) {
        return StringUtils.equalsIgnoreCase(getFileExtFromFileName(fileName), EXT_ZIP);
    }

    public static boolean isAcceptFileZipType(UploadedFile uploadedFile) {
        return isZipFileExt(uploadedFile.getFileName().toLowerCase());
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

    public static String createPathFileError() {
        String fileId = FileUtil.generateFileIdTime() + RandomStringUtils.randomAlphanumeric(16);
        String folder = FileUtil.buildFolderUpload(FileUtil.FOLDER_NAME_FILE);
        File inFiles = new File(folder);
        if (!inFiles.exists() && !inFiles.mkdirs()) {
            log.error("Can't create folder");
        }
        pathReturn = pathReturn + fileId + "." + FILE_EXCEL[1];
        return folder + fileId + "." + FILE_EXCEL[1];
    }

    public static String generateFileIdTime() {
        return DateUtil.getCurrentDateStr();
    }

    public static String buildFolderUpload(String folderName) {
        String todayFolder = DateUtil.getTodayFolder();
        String folderSave = PropertiesUtil.getProperty("vn.cpa.static.location.upload");
        pathReturn = "";
        pathReturn = pathReturn + File.separator + folderName
                + File.separator + todayFolder
                + File.separator;
        // in project
        return folderSave
                + File.separator + folderName
                + File.separator + todayFolder
                + File.separator;
    }

}
