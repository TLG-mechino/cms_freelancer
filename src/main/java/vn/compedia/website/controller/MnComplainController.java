package vn.compedia.website.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.controller.common.BaseController;
import vn.compedia.website.dto.AccountDto;
import vn.compedia.website.dto.ComplainDto;
import vn.compedia.website.dto.ComplainSearchDto;
import vn.compedia.website.model.Complain;
import vn.compedia.website.model.ComplainFile;
import vn.compedia.website.model.ComplainType;
import vn.compedia.website.model.Hashtag;
import vn.compedia.website.repository.ComplainFileRepository;
import vn.compedia.website.repository.ComplainRepository;
import vn.compedia.website.repository.ComplainTypeRepository;
import vn.compedia.website.service.NotificationSystemService;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.DbConstant;
import vn.compedia.website.util.FacesUtil;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Named
@Scope(value = "session")
public class MnComplainController extends BaseController {

    @Autowired
    private ComplainRepository complainRepository;
    @Autowired
    private ComplainTypeRepository complainTypeRepository;
    @Autowired
    private ComplainFileRepository complainFileRepository;
    @Autowired
    private AuthorizationController authorizationController;
    @Autowired
    private NotificationSystemService notificationSystemService;



    private Complain complain;
    private ComplainSearchDto searchDto;
    private ComplainDto dto;
    private LazyDataModel<ComplainDto> lazyDataModel;
    private List<ComplainType> complainTypes;
    private List<ComplainFile> complainFiles;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            init();
            resetAll();
        }
    }

    public void resetAll() {
        searchDto = new ComplainSearchDto();
        dto = new ComplainDto();
        complain = new Complain();
        complainFiles = new ArrayList<>();
        complainTypes = complainTypeRepository.findAllByStatus(DbConstant.ACTIVE_STATUS);
        onSearch();
    }

    public void onSearch() {
        lazyDataModel = new LazyDataModel<ComplainDto>() {
            @Override
            public List<ComplainDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
                searchDto.setPageIndex(first);
                searchDto.setPageSize(pageSize);
                searchDto.setSortField(sortField);

                String sortOption = "";
                if (sortOrder.equals(SortOrder.ASCENDING)) {
                    sortOption = "ASC";
                } else {
                    sortOption = "DESC";
                }

                searchDto.setSortOrder(sortOption);
                return complainRepository.search(searchDto);
            }

            @Override
            public ComplainDto getRowData(String rowKey) {
                List<ComplainDto> list = getWrappedData();
                Long value = Long.valueOf(rowKey);
                for (ComplainDto obj : list) {
                    if (obj.getComplainId().equals(value)) {
                        return obj;
                    }
                }
                return null;
            }
        };
        int count = complainRepository.countSearch(searchDto).intValue();
        lazyDataModel.setRowCount(count);
        FacesUtil.updateView("searchForm");
    }

    public void getComplainId(Long id){
        complain = complainRepository.findById(id).get();
        BeanUtils.copyProperties(complain, dto);

    }

   public List<ComplainFile> ListFileAttack(Long complainId){
       complainFiles = complainFileRepository.findAllByComplainId(complainId);
       return complainFiles;
   }


    public void ComplainResolve(ComplainDto dto){
        complain.setStatus(DbConstant.ACTIVE_STATUS       );
        complain.setNote(dto.getNote());
        if (dto.getStatus() == null) {
            FacesUtil.addErrorMessage("Bạn vui lòng chọn trạng thái ");
            FacesUtil.updateView("growl");
            return;
        }

        if (StringUtils.isBlank(dto.getNote().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập ghi chú  ");
            FacesUtil.updateView("growl");
            return;
        }
        complainRepository.save(complain);
        FacesUtil.addSuccessMessage("Lưu thành công");
        FacesUtil.closeDialog("inforDialog");
        FacesUtil.updateView("growl");

        //add notification
        List<String> usernameList = new ArrayList<>();
        usernameList.add(complain.getUsername());
        notificationSystemService.saveNotification(authorizationController.getAccountDto().getUsername(),"Đã tiếp nhận đơn khiếu nại",
                "Đơn khiếu nại đã được tiếp nhận và xử lý","Complaint received", "Complaint has been received and handled",
                12, complain.getComplainId(), usernameList);

    }

    public void onDelete(Long complainId) {
        if (complainId == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        complainRepository.deleteById(complainId);
        FacesUtil.addSuccessMessage("Xóa thành công");
        FacesUtil.updateView("growl");
        onSearch();
    }

    @Override
    protected String getMenuId() {
        return Constant.MN_COMPLAIN;
    }
}
