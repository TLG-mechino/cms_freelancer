package vn.compedia.website.controller.common;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.dto.common.UploadMultipleFileDto;
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
public class UploadMultipleImageController extends BaseController {
    @Inject
    private LanguageController languageController;

    private static Logger log = LoggerFactory.getLogger(UploadMultipleImageController.class);

    private UploadMultipleFileDto uploadMultipleFileDto;

    public void resetAll(List<String> listImage) {
        uploadMultipleFileDto = new UploadMultipleFileDto();
        if (CollectionUtils.isEmpty(listImage)) {
            listImage = new ArrayList<>();
        }
        uploadMultipleFileDto.setListToShow(listImage);
        uploadMultipleFileDto.setListToAdd(new ArrayList<>());
        uploadMultipleFileDto.setListToDelete(new ArrayList<>());
    }

    public void onUploadImage(FileUploadEvent e) {
        try {
            if (FileUtil.isAcceptImageType(e.getFile())) {
                setErrorForm("Bạn vui lòng chọn file ảnh có định dạng gif, jpg, jpeg, png");
                return;
            }
            if (e.getFile().getSize() > Constant.MAX_FILE_SIZE) {
                setErrorForm("Dung lượng file quá lớn. Dung lượng tối đa " + Constant.MAX_FILE_SIZE / 1000000 + "Mb");
                return;
            }

            if (uploadMultipleFileDto.getListToShow().size() >= 10 ) {
                setErrorForm("Số lượng hình ảnh tối đa được tải lên là 10");
                return;
            }

            String image = FileUtil.saveImageFile(e.getFile());
            uploadMultipleFileDto.getListToShow().add(image);
            uploadMultipleFileDto.getListToAdd().add(image);
        } catch (Exception ex) {
            log.error("Error", ex);
        }
    }

    public void onRemoveImage(String image) {
        uploadMultipleFileDto.getListToShow().remove(image);
        // Nếu chưa có trong database
        if (uploadMultipleFileDto.getListToAdd().contains(image)) {
            uploadMultipleFileDto.getListToAdd().remove(image);
        } else {
            uploadMultipleFileDto.getListToDelete().add(image);
        }
    }

    public UploadMultipleFileDto getUploadMultipleFileDto() {
        return uploadMultipleFileDto;
    }

    @Override
    protected String getMenuId() {
        return null;
    }
}
