package vn.compedia.website.controller;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.controller.common.BaseController;
import vn.compedia.website.model.ConfigSystem;
import vn.compedia.website.model.Hashtag;
import vn.compedia.website.repository.SystemConfigRepository;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.DbConstant;
import vn.compedia.website.util.FacesUtil;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
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
    @Autowired
    private SystemConfigRepository systemConfigRepository;

    private Integer totalJob;
    private Integer totalFreelancer;
    private Integer bidFee;
    private Integer transactionFee;
    private Integer blockTimeExam;
    private String keyword;
    private ConfigSystem configSystem;
    private List<ConfigSystem> configSystemList;
    private List<ConfigSystem> listFee;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            init();
            resetAll();
        }
    }

    public void resetAll() {
        configSystem = new ConfigSystem();
        configSystemList = new ArrayList<>();
        totalJob = Integer.parseInt(systemConfigRepository.getValue(DbConstant.TOTAL_JOB));
        totalFreelancer = Integer.parseInt(systemConfigRepository.getValue(DbConstant.TOTAL_FREELANCER));
        bidFee = Integer.parseInt(systemConfigRepository.getValue(DbConstant.BID_FEE));
        transactionFee = Integer.parseInt(systemConfigRepository.getValue(DbConstant.TRANSACTION_FEE));
        blockTimeExam = Integer.parseInt(systemConfigRepository.getValue(DbConstant.BLOCK_TIME_EXAM));
        keyword = systemConfigRepository.getValue(DbConstant.BAN_KEYWORD);
    }

    public boolean validateDate() {
        if (bidFee == null) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập phí chiết khấu đấu thầu ");
            return false;
        }

        if (transactionFee == null) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập % chiết khấu giao dịch ");
            return false;
        }

        if (totalJob == null) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập tổng số dự án ");
            return false;
        }

        if (totalFreelancer == null) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập tổng số người dùng ");
            return false;
        }

        if (blockTimeExam == null) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập thời gian cấm thi ");
            return false;
        }
        if (StringUtils.isBlank(keyword)) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập từ khóa bị cấm ");
            return false;
        }

        return true;
    }


    public void editBidFee(Integer bidFee){
        if (bidFee == null) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập phí chiết khấu đấu thầu ");
            FacesUtil.updateView("growl");
            return;
        }
        if (transactionFee == null) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập phần trăm chiết khấu giao dịch ");
            FacesUtil.updateView("growl");
            return;
        }
        ConfigSystem csBidFee = systemConfigRepository.findByCode(DbConstant.BID_FEE);
        configSystem.setValue(bidFee.toString());
        listFee.add(csBidFee);
        ConfigSystem csTransactionFee = systemConfigRepository.findByCode(DbConstant.TRANSACTION_FEE);
        configSystem.setValue(transactionFee.toString());
        listFee.add(csTransactionFee);
        systemConfigRepository.save(configSystem);
        FacesUtil.addSuccessMessage("Lưu thành công");
        FacesUtil.closeDialog("inforDialog");
        FacesUtil.updateView("growl");
    }

    public void editBanKeyword(String keyword){
        if (StringUtils.isBlank(keyword)) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập từ khóa bị cấm ");
            FacesUtil.updateView("growl");
            return;
        }
        configSystem = systemConfigRepository.findByCode(DbConstant.BAN_KEYWORD);
        configSystem.setValue(keyword);
        systemConfigRepository.save(configSystem);
        FacesUtil.addSuccessMessage("Lưu thành công");
        FacesUtil.closeDialog("inforDialog");
        FacesUtil.updateView("growl");
    }

    public void edit(Integer totalJob, Integer totalFreelancer, Integer blockTimeExam){
        if (totalJob == null) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập s ");
            FacesUtil.updateView("growl");
            return;
        }

        if (totalFreelancer == null) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập tổng số người dùng ");
            FacesUtil.updateView("growl");
            return;
        }

        if (blockTimeExam == null) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập thời gian cấm thi ");
            FacesUtil.updateView("growl");
            return;
        }
        ConfigSystem csTotalJob = systemConfigRepository.findByCode(DbConstant.TOTAL_JOB);
        csTotalJob.setValue(totalJob.toString());
        configSystemList.add(csTotalJob);
        ConfigSystem csTotalFreelancer = systemConfigRepository.findByCode(DbConstant.TOTAL_FREELANCER);
        csTotalFreelancer.setValue(totalFreelancer.toString());
        configSystemList.add(csTotalFreelancer);
        ConfigSystem csBlockTimeExam = systemConfigRepository.findByCode(DbConstant.BLOCK_TIME_EXAM);
        csBlockTimeExam.setValue(blockTimeExam.toString());
        configSystemList.add(csBlockTimeExam);

        systemConfigRepository.saveAll(configSystemList);
        FacesUtil.addSuccessMessage("Lưu thành công");
        FacesUtil.closeDialog("inforDialog");
        FacesUtil.updateView("growl");
    }

    @Override
    protected String getMenuId() {
        return Constant.SYS_CONFIG;
    }
}
