package vn.compedia.website.controller.common;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.util.DateUtil;
import vn.compedia.website.util.FacesUtil;
import vn.compedia.website.util.FileUtil;
import vn.compedia.website.util.PropertiesUtil;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Named
@Getter
@Setter
@Scope(value = "session")
public class CommonController implements Serializable {
    @Autowired
    private HttpServletRequest request;

    public static String abbreviate(String text, int size) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        if (text.length() > size) {
            return text.substring(0, size).trim() + "...";
        }
        return text;
    }

    public static String showAddress(String address, String commune, String district, String province) {
        List<String> list = new ArrayList<>(Arrays.asList(
                address,
                commune,
                district,
                province
        ));
        list.removeIf(StringUtils::isBlank);

        return String.join(" - ", list);
    }

    public boolean isPdfFile(String fileName) {
        return FileUtil.isPdfFileExt(fileName);
    }

    public boolean isOfficeFile(String fileName) {
        return FileUtil.isOfficeFileExt(fileName);
    }

    public StreamedContent getFileDownload(String filePath) {
        StreamedContent streamedContent = FileUtil.getDownloadFileFromDatabase(setUrlToShowImage(filePath));
        if (streamedContent == null) {
            FacesUtil.addErrorMessage("File không tồn tại");
        }
        return streamedContent;
    }

    public String getCurrentDomain() {
        return String.format("%s://%s:%s", request.getScheme(), request.getServerName(), request.getServerPort()
        );
    }

    public static void download(String file, String userFileName) {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            response.reset();
            response.setHeader("Content-Type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + userFileName);
            OutputStream responseOutputStream = response.getOutputStream();
            InputStream fileInputStream = FileUtil.getDownloadFileFromDatabase(file).getStream();
            byte[] bytesBuffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(bytesBuffer)) > 0) {
                responseOutputStream.write(bytesBuffer, 0, bytesRead);
            }
            responseOutputStream.flush();
            fileInputStream.close();
            responseOutputStream.close();
            facesContext.responseComplete();
        } catch (Exception e) {
            FacesUtil.redirect("/404");
        }
    }

    public String upperCaseFirstChar(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static Cell setCellType(Cell cell) {
        cell.setCellType(CellType.STRING);
        return cell;
    }

    public static Object convertValue(Cell cell, int type) {
        cell.setCellType(CellType.STRING);
        Object object;
        try {
            switch (type) {
                case 1:
                    object = cell.getStringCellValue().trim().replaceAll("'", "");
                    break;
                case 2:
                    object = Integer.valueOf(cell.getStringCellValue().trim().replaceAll("'", ""));
                    break;
                case 3:
                    object = Long.valueOf(cell.getStringCellValue().trim().replaceAll("'", ""));
                    break;
                case 4:
                    object = DateUtil.formatDatePattern(cell.getStringCellValue().trim().replaceAll("'", ""), DateUtil.DDMMYYYY);
                    break;
                default:
                    object = null;
                    break;
            }
            return object;
        } catch (Exception ex) {
            return null;
        }
    }

    public static String getMonth(Date date) {
        SimpleDateFormat simpleMonthFormat = new SimpleDateFormat("MM");
        return simpleMonthFormat.format(date).toUpperCase();
    }

    public static String generateDateExport() {
        return DateUtil.generateDateExport();
    }

    public static String setUrlToShowImage(String filePath) {
        return PropertiesUtil.getProperty("vn.compedia.static.context") + filePath;
    }
}
