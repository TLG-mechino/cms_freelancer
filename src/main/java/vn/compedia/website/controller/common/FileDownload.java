package vn.compedia.website.controller.common;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import vn.compedia.website.model.ExamFile;

import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class FileDownload {
    private StreamedContent file;

    public void FileDownload(ExamFile examFile) {
        file = DefaultStreamedContent.builder()
                .name(examFile.getFileName())
                .contentType(examFile.getFileType())
                .stream(() -> FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(examFile.getFilePath()))
                .build();
    }

    public StreamedContent getFile() {
        return file;
    }
}
