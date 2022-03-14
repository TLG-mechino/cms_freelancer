package vn.compedia.website.controller;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.controller.common.BaseController;
import vn.compedia.website.model.SystemConfig;
import vn.compedia.website.repository.SystemConfigRepository;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.FacesUtil;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Named
@Scope(value = "session")
public class SystemConfigController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(SystemConfigController.class);

    @Autowired
    private SystemConfigRepository systemConfigRepository;

    private List<SystemConfig> systemConfigList;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            init();
            resetAll();
        }
    }

    public void resetAll() {
        systemConfigList = (List<SystemConfig>) systemConfigRepository.findAll();
        if (CollectionUtils.isEmpty(systemConfigList)) {
            systemConfigList = new ArrayList<>();
        }
    }

    public void onSave() {
        for (SystemConfig systemConfig : systemConfigList) {
            if (StringUtils.isBlank(systemConfig.getValue())) {
                FacesUtil.addErrorMessage("Bạn vui lòng nhập giá trị cho \"" + systemConfig.getTitle() + "\"");
                FacesUtil.updateView("growl");
                return;
            }
            if (StringUtils.isNumeric(systemConfig.getValue()) && Integer.parseInt(systemConfig.getValue()) == 0) {
                FacesUtil.addErrorMessage("Giá trị tham số \"" + systemConfig.getTitle() + "\" phải lớn hơn 0");
                FacesUtil.updateView("growl");
                return;
            }
        }
        systemConfigRepository.saveAll(systemConfigList);
        FacesUtil.addSuccessMessage("Lưu thành công");
        FacesUtil.updateView("dlForm");
    }

    @Override
    protected String getMenuId() {
        return Constant.SYS_CONFIG;
    }
}
