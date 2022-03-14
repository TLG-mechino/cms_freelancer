package vn.compedia.website.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.controller.common.BaseController;
import vn.compedia.website.util.Constant;

import javax.faces.context.FacesContext;
import javax.inject.Named;

@Setter
@Getter
@Named
@Scope(value = "session")
public class DashboardController extends BaseController {

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
        return Constant.DASHBOARD;
    }
}
