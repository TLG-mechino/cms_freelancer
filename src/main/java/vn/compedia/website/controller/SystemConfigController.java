package vn.compedia.website.controller;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.controller.common.BaseController;
import vn.compedia.website.util.Constant;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Named
@Scope(value = "session")
public class SystemConfigController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(SystemConfigController.class);
    @Inject
    private AuthorizationController authorizationController;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            init();
            resetAll();
        }
    }

    public void resetAll() {
    }

    @Override
    protected String getMenuId() {
        return Constant.SYS_CONFIG;
    }
}
