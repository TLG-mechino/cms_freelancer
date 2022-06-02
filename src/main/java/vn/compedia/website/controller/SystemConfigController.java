package vn.compedia.website.controller;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.controller.common.BaseController;
import vn.compedia.website.model.ConfigSystem;
import vn.compedia.website.repository.SystemConfigRepository;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.DbConstant;

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
    private Integer blockTimeExam;
    private String keyword;
    private ConfigSystem configSystem;
    private List<ConfigSystem> configSystemList;

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
        blockTimeExam = Integer.parseInt(systemConfigRepository.getValue(DbConstant.BLOCK_TIME_EXAM));
        keyword = systemConfigRepository.getValue(DbConstant.BAN_KEYWORD);
    }

    public void editBidFee(Integer bidFee){
        configSystem = systemConfigRepository.findByCode(DbConstant.BID_FEE);
        configSystem.setValue(bidFee.toString());
        systemConfigRepository.save(configSystem);
    }

    public void editBanKeyword(String keyword){
        configSystem = systemConfigRepository.findByCode(DbConstant.BAN_KEYWORD);
        configSystem.setValue(keyword);
        systemConfigRepository.save(configSystem);
    }

    public void edit(Integer totalJob, Integer totalFreelancer, Integer blockTimeExam){
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
    }

    @Override
    protected String getMenuId() {
        return Constant.SYS_CONFIG;
    }
}
