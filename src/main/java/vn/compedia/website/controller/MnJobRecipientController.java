package vn.compedia.website.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.controller.common.BaseController;
import vn.compedia.website.dto.JobDto;
import vn.compedia.website.dto.JobSearchDto;
import vn.compedia.website.model.Job;
import vn.compedia.website.repository.JobRepository;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.FacesUtil;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Named
@Scope(value = "session")
public class MnJobRecipientController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(MnJobRecipientController.class);

    @Inject
    private MnUserController userController;

    @Autowired
    private JobRepository jobRepository;

    private Job job;
    private String titleDialog;
    private JobSearchDto searchDto;
    private JobDto jobDto;
    private LazyDataModel<JobDto> lazyDataModel;
    private JobSearchDto searchDtoTemp;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            init();
            resetAll();
        }
    }

    public void resetAll() {
        job = new Job();
        jobDto = new JobDto();
        searchDto = new JobSearchDto();
        searchDtoTemp = new JobSearchDto();
        onSearch();
    }

    public void onSearch() {
        lazyDataModel = new LazyDataModel<JobDto>() {
            @Override
            public List<JobDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filters) {
                searchDto.setPageIndex(first);
                searchDto.setPageSize(pageSize);
                searchDto.setSortField(sortField);
                String sort = "";
                if (sortOrder.equals(SortOrder.ASCENDING)) {
                    sort = "ASC";
                } else {
                    sort = "DESC";
                }
                searchDto.setSortOrder(sort);
                BeanUtils.copyProperties(searchDto, searchDtoTemp);
                return jobRepository.getAllJobRecipient(userController.getUserDtoDetails().getId(), searchDto);
            }

            @Override
            public JobDto getRowData(String rowKey) {
                List<JobDto> requestRewardDtoList = getWrappedData();
                Long value = Long.valueOf(rowKey);
                for (JobDto obj : requestRewardDtoList) {
                    if (obj.getId().equals(value)) {
                        return obj;
                    }
                }
                return null;
            }
        };
        int count = jobRepository.countSearchRecipient(userController.getUserDtoDetails().getId(), searchDto).intValue();
        lazyDataModel.setRowCount(count);
        FacesUtil.updateView("searchForm");
    }

    public void onDelete(Long jobId) {
        if (jobId == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        jobRepository.deleteById(jobId);
        FacesUtil.addSuccessMessage("Xóa thành công");
        FacesUtil.updateView("growl");
        onSearch();
    }

    @Override
    protected String getMenuId() {
        return Constant.MN_JOB_RECIPIENT;
    }
}
