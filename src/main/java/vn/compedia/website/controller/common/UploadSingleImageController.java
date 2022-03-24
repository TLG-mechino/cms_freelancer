package vn.compedia.website.controller.common;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.FileUtil;

import javax.inject.Inject;
import javax.inject.Named;

@Getter
@Setter
@Named
@Scope(value = "session")
public class UploadSingleImageController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(UploadSingleImageController.class);
    @Inject
    private LanguageController languageController;

    private String imagePath;
    private boolean showDeleteButton;

    public void resetAll(String image) {
        if (StringUtils.isBlank(image)) {
            image = Constant.NO_IMAGE_URL;
            showDeleteButton = false;
        } else {
            showDeleteButton = true;
        }
        imagePath = image;
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
            imagePath = FileUtil.saveImageFile(e.getFile());
            showDeleteButton = true;
        } catch (Exception ex) {
            log.error("Error", ex);
        }
    }

    public void onRemoveImage(String image) {
        imagePath = Constant.NO_IMAGE_URL;
        showDeleteButton = false;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isShowDeleteButton() {
        return showDeleteButton;
    }

    public boolean isShowDeleteButtonDefault(String imagePath) {
        if (StringUtils.isNotBlank(imagePath) && imagePath.contains("no-avatar.png")) {
            return false;
        }
        return true;
    }

    public void setShowDeleteButton(boolean showDeleteButton) {
        this.showDeleteButton = showDeleteButton;
    }

    @Override
    protected String getMenuId() {
        return null;
    }
}
