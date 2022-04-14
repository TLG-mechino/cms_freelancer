package vn.compedia.website.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
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
import vn.compedia.website.controller.common.UploadSingleImageController;
import vn.compedia.website.dto.CustomerTalkDto;
import vn.compedia.website.dto.CustomerTalkSearchDto;
import vn.compedia.website.dto.HashtagDto;
import vn.compedia.website.dto.TransactionDto;
import vn.compedia.website.model.CustomerTalk;
import vn.compedia.website.model.Hashtag;
import vn.compedia.website.repository.CustomerTalkRepository;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.FacesUtil;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Log4j
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Named
@Scope(value = "session")
public class CustomerTalkController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(MnTransactionController.class);
    @Inject
    private AuthorizationController authorizationController;

    @Autowired
    private CustomerTalkRepository customerTalkRepository;
    @Inject
    private UploadSingleImageController uploadSingleImageController;

    private String titleDialog;
    private CustomerTalk customerTalk;
    private CustomerTalkDto customerTalkDto;
    private CustomerTalkSearchDto searchDto;
    private LazyDataModel<CustomerTalkDto> lazyDataModel;
    private CustomerTalkSearchDto searchDtoTemp;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            init();
            resetAll();
        }
    }

    public void resetAll(){
        customerTalk = new CustomerTalk();
        customerTalkDto = new CustomerTalkDto();
        searchDto = new CustomerTalkSearchDto();
        searchDtoTemp = new CustomerTalkSearchDto();
        uploadSingleImageController.resetAll(null);
        onSearch();
    }

    public void resetDialog(){
        customerTalkDto = new CustomerTalkDto();
        uploadSingleImageController.resetAll(null);
        titleDialog = "Thêm mới";
        FacesUtil.updateView("inforDialogId");
    }

    public void onSearch() {
        lazyDataModel = new LazyDataModel<CustomerTalkDto>() {
            @Override
            public List<CustomerTalkDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filters) {
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
                return customerTalkRepository.search(searchDto);
            }

            @Override
            public CustomerTalkDto getRowData(String rowKey) {
                List<CustomerTalkDto> requestRewardDtoList = getWrappedData();
                Long value = Long.valueOf(rowKey);
                for (CustomerTalkDto obj : requestRewardDtoList) {
                    if (obj.getCustomerTalkId().equals(value)) {
                        return obj;
                    }
                }
                return null;
            }
        };
        int count = customerTalkRepository.countSearch(searchDto).intValue();
        lazyDataModel.setRowCount(count);
        FacesUtil.updateView("searchForm");
    }

    public boolean validateDate() {
        if (StringUtils.isBlank(customerTalkDto.getPosition().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập vị trí công việc của khách hàng");
            return false;
        }

        long idCheck;
        if (null == customerTalkDto.getCustomerTalkId()) {
            idCheck = 0L;
        } else {
            idCheck = customerTalkDto.getCustomerTalkId();
        }

        if (StringUtils.isBlank(customerTalkDto.getFullName().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập Họ Tên khách hàng ");
            return false;
        }

        if (StringUtils.isBlank(customerTalkDto.getContent().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập nội dung");
            return false;
        }

        if(!uploadSingleImageController.isShowDeleteButton()) {
            FacesUtil.addErrorMessage("Bạn vui lòng chọn hình ảnh");
            return false;
        }

        return true;
    }

    public void onSave() {
        if (!validateDate()) {
            return;
        }
        BeanUtils.copyProperties(customerTalkDto, customerTalk);
        customerTalk.setImagePath(uploadSingleImageController.getImagePath());
        customerTalkRepository.save(customerTalk);
        FacesUtil.addSuccessMessage("Lưu thành công");
        FacesUtil.closeDialog("inforDialog");
        FacesUtil.updateView("growl");
        onSearch();
    }

    public void onEdit(CustomerTalkDto object) {
        if (object == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        customerTalkDto = new CustomerTalkDto();
        BeanUtils.copyProperties(object, customerTalkDto);
        titleDialog = "Sửa";
        uploadSingleImageController.resetAll(object.getImagePath());
        FacesUtil.updateView("inforDialogId");
    }

    public void onDelete(Long customerTalkId) {
        if (customerTalkId == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        customerTalkRepository.deleteById(customerTalkId);
        FacesUtil.addSuccessMessage("Xóa thành công");
        FacesUtil.updateView("growl");
        onSearch();
    }

    @Override
    protected String getMenuId() {
        return Constant.MN_CUSTOMER_TALK;
    }
}
