package vn.compedia.website.controller;


import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.controller.common.BaseController;
import vn.compedia.website.dto.MarketDto;
import vn.compedia.website.dto.MarketSearchDto;
import vn.compedia.website.model.Market;
import vn.compedia.website.repository.MarketRepository;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.FacesUtil;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
