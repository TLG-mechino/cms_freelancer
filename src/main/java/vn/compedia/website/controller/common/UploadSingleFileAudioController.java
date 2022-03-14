package vn.compedia.website.controller.common;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.FacesUtil;
import vn.compedia.website.util.FileUtil;

import javax.inject.Named;

@Getter
@Setter
@Named
@Scope(value = "session")
public class UploadSingleFileAudioController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(UploadSingleFileAudioController.class);

    private String filePath;

    public void resetAll(String filePath) {
        this.filePath = filePath;
    }

    public void onUploadFile(FileUploadEvent e) {
        try {
            if (!FileUtil.isAcceptFileAudioType(e.getFile())) {
                FacesUtil.addErrorMessage("File upload không đúng định dạng. Bạn vui lòng tải có định dạng là 1 trong những định dạng sau: " + FileUtil.getAcceptFileAudioString().replaceAll(",", ", "));
                return;
            }
            if (e.getFile().getSize() > Constant.MAX_FILE_SIZE) {
                FacesUtil.addErrorMessage("Dung lượng file quá lớn. Dung lượng tối đa " + Constant.MAX_FILE_SIZE / 1000000 + "Mb");
                return;
            }
            filePath = FileUtil.saveFile(e.getFile());
        } catch (Exception ex) {
            log.error("Error", ex);
        }
    }

    public void onRemoveFile() {
        this.filePath = "";
    }

    @Override
    protected String getMenuId() {
        return null;
    }
}
