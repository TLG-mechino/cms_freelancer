package vn.compedia.website.controller.common;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.dto.UploadWithFilenameDto;
import vn.compedia.website.dto.common.UploadMultipleFileNameDto;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.FileUtil;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@Scope(value = "session")
@Getter
@Setter
public class UploadMultipleImageWithFileNameController extends BaseController {

    @Inject
    private LanguageController languageController;

    private static Logger log = LoggerFactory.getLogger(UploadMultipleImageController.class);

    private UploadMultipleFileNameDto uploadMultipleFileDto;

    public void resetAll(List<UploadWithFilenameDto> listImage) {
        uploadMultipleFileDto = new UploadMultipleFileNameDto();
        if (CollectionUtils.isEmpty(listImage)) {
            listImage = new ArrayList<>();
        }
        uploadMultipleFileDto.setListToShow(listImage);
        uploadMultipleFileDto.setListToAdd(new ArrayList<>());
        uploadMultipleFileDto.setListToDelete(new ArrayList<>());
    }

    public void onUploadImage(FileUploadEvent e) {
        try {
            if (!FileUtil.isAcceptImageType(e.getFile())) {
                setErrorForm("Loại file không được phép. Những file được phép " + FileUtil.getAcceptImageString().replaceAll(",", ", ").toUpperCase());
                return;
            }
            if (e.getFile().getSize() > Constant.MAX_FILE_SIZE) {
                setErrorForm("Dung lượng file quá lớn. Dung lượng tối đa " + Constant.MAX_FILE_SIZE / 1000000 + "Mb");
                return;
            }

            if (uploadMultipleFileDto.getListToShow().contains(e.getFile().getFileName())) {
                setErrorForm("Tên file đã tồn tại, vui lòng chọn file khác");
                return;
            }

            if (uploadMultipleFileDto.getListToShow().size() > 9) {
                setErrorForm("Số lượng file quá lớn. Số lượng cho phép là <= 10");
                return;
            }
            else {
                String image = FileUtil.saveImageFile(e.getFile());
                String fileName = e.getFile().getFileName();
                UploadWithFilenameDto uploadWithFilenameDto = new UploadWithFilenameDto();
                uploadWithFilenameDto.setFileName(fileName);
                uploadWithFilenameDto.setFilePath(image);

                uploadMultipleFileDto.getListToShow().add(uploadWithFilenameDto);
                uploadMultipleFileDto.getListToAdd().add(uploadWithFilenameDto);
            }
        } catch (Exception ex) {
            log.error("Error", ex);
        }
    }

    public void onRemoveImage(UploadWithFilenameDto uploadWithFilenameDto) {
        uploadMultipleFileDto.getListToShow().remove(uploadWithFilenameDto);
        // Nếu chưa có trong database
        if (uploadMultipleFileDto.getListToAdd().contains(uploadWithFilenameDto)) {
            uploadMultipleFileDto.getListToAdd().remove(uploadWithFilenameDto);
        } else {
            uploadMultipleFileDto.getListToDelete().add(uploadWithFilenameDto);
        }
    }

    public void onRemoveImageList(List<UploadWithFilenameDto> uploadMutipleImage) {
        for (UploadWithFilenameDto uploadWithFilenameDto : uploadMutipleImage) {
            uploadMultipleFileDto.getListToShow().remove(uploadWithFilenameDto);
            // Nếu chưa có trong database
            if (uploadMultipleFileDto.getListToAdd().contains(uploadWithFilenameDto)) {
                uploadMultipleFileDto.getListToAdd().remove(uploadWithFilenameDto);
            } else {
                uploadMultipleFileDto.getListToDelete().add(uploadWithFilenameDto);
            }
        }

    }

    @Override
    protected String getMenuId() {
        return null;
    }
}
