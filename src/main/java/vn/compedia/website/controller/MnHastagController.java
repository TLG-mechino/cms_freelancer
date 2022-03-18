package vn.compedia.website.controller;


import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.controller.common.BaseController;
import vn.compedia.website.util.Constant;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Setter
@Getter
@Named
@Scope(value = "session")
public class MnHastagController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(MnHastagController.class);
    @Inject
    private AuthorizationController authorizationController;

    private String titleDialog;

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
        return Constant.MN_HASTAG;
    }
}
