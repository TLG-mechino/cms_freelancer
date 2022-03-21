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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.controller.common.BaseController;
import vn.compedia.website.dto.AccountDto;
import vn.compedia.website.dto.config.serviceConfigDto;
import vn.compedia.website.dto.config.serviceConfigSearchDto;
import vn.compedia.website.model.Hashtag;
import vn.compedia.website.model.PackageService;
import vn.compedia.website.model.ServiceType;
import vn.compedia.website.repository.ServiceTypeRepository;
import vn.compedia.website.repository.config.serviceConfigRepository;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.FacesUtil;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Named
@Scope(value = "session")
public class ServiceConfigController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ServiceConfigController.class);
    @Inject
    private AuthorizationController authorizationController;

    @Autowired
    private serviceConfigRepository serviceConfigRepository;
    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    private PackageService packageService;
    private String titleDialog;
    private serviceConfigSearchDto searchDto;
    private serviceConfigDto serviceConfigDto;
    private LazyDataModel<PackageService> lazyDataModel;
    private serviceConfigSearchDto searchDtoTemp;
    private List<ServiceType> serviceTypeList;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            init();
            resetAll();
        }
    }

    public void resetAll() {
        serviceTypeList = new ArrayList<>();
        packageService = new PackageService();
        serviceConfigDto = new serviceConfigDto();
        searchDto = new serviceConfigSearchDto();
        searchDtoTemp = new serviceConfigSearchDto();
        serviceTypeList = (List<ServiceType>) serviceTypeRepository.findAll();
        onSearch();
    }

    public void resetDialog() {
        packageService = new PackageService();
        titleDialog = "Thêm mới";
        FacesUtil.updateView("inforDialogId");
    }

    public void onSearch() {
        lazyDataModel = new LazyDataModel<PackageService>() {
            @Override
            public List<PackageService> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filters) {
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
                return serviceConfigRepository.search(searchDto);
            }

            @Override
            public PackageService getRowData(String rowKey) {
                List<PackageService> requestRewardDtoList = getWrappedData();
                Long value = Long.valueOf(rowKey);
                for (PackageService obj : requestRewardDtoList) {
                    if (obj.getPackageServiceId().equals(value)) {
                        return obj;
                    }
                }
                return null;
            }
        };
        int count = serviceConfigRepository.countSearch(searchDto).intValue();
        lazyDataModel.setRowCount(count);
        FacesUtil.updateView("searchForm");
    }

    public boolean validateDate() {
        if (StringUtils.isBlank(packageService.getCode().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập mã dịch vụ");
            return false;
        }

        long idCheck;
        if (null == packageService.getPackageServiceId()) {
            idCheck = 0L;
        } else {
            idCheck = packageService.getPackageServiceId();
        }

//        List<PackageService> checkCodeExists = serviceConfigRepository.findByCodeExists(packageService.getCode(), idCheck);
//        if (CollectionUtils.isNotEmpty(checkCodeExists)) {
//            FacesUtil.addErrorMessage("Mã dịch vụ đã tồn tại");
//            return false;
//        }

        if (StringUtils.isBlank(packageService.getName().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập tên dịch vụ ");
            return false;
        }

        if (StringUtils.isBlank(packageService.getDescription().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập tiêu đề ");
            return false;
        }
        if (packageService.getMoney() == null) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập số tiền ");
            return false;
        }

        return true;
    }

    public void onSave() {
        if (!validateDate()) {
            return;
        }
        if (packageService.getPackageServiceId() == null) {
            packageService.setCreateDate(new Date());
            packageService.setUserName(authorizationController.getAccountDto().getUsername());
        }
        packageService.setUpdateDate(new Date());
        packageService.setUpdateBy(authorizationController.getAccountDto().getUsername());

        serviceConfigRepository.save(packageService);
        FacesUtil.addSuccessMessage("Lưu thành công");
        FacesUtil.closeDialog("inforDialog");
        FacesUtil.updateView("growl");
        onSearch();
    }

    public void onEdit(Hashtag object) {
        if (object == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        packageService = new PackageService();
        BeanUtils.copyProperties(object, packageService);
        titleDialog = "Sửa";
        FacesUtil.updateView("inforDialogId");
    }

    public void onDelete(Long hashtagId) {
        if (hashtagId == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        serviceConfigRepository.deleteById(hashtagId);
        FacesUtil.addSuccessMessage("Xóa thành công");
        FacesUtil.updateView("growl");
        onSearch();
    }

    @Override
    protected String getMenuId() {
        return Constant.SERVICE_CONFIG;
    }
}
