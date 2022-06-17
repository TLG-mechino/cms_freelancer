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
import vn.compedia.website.dto.PackageServiceDto;
import vn.compedia.website.dto.config.ServiceConfigDto;
import vn.compedia.website.dto.config.ServiceConfigSearchDto;
import vn.compedia.website.model.Hashtag;
import vn.compedia.website.model.PackageService;
import vn.compedia.website.model.ServiceType;
import vn.compedia.website.repository.ServiceTypeRepository;
import vn.compedia.website.repository.config.ServiceConfigRepository;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.FacesUtil;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private ServiceConfigRepository serviceConfigRepository;
    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    private PackageService packageService;
    private String titleDialog;
    private ServiceConfigSearchDto searchDto;
    private ServiceConfigDto serviceConfigDto;
    private LazyDataModel<ServiceConfigDto> lazyDataModel;
    private ServiceConfigSearchDto searchDtoTemp;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            init();
            resetAll();
        }
    }

    public void resetAll() {
        packageService = new PackageService();
        serviceConfigDto = new ServiceConfigDto();
        searchDto = new ServiceConfigSearchDto();
        searchDtoTemp = new ServiceConfigSearchDto();
        onSearch();
    }

    public void resetDialog() {
        serviceConfigDto = new ServiceConfigDto();
        serviceConfigDto.setLimitPost(true);
        serviceConfigDto.setLimitComment(true);
        serviceConfigDto.setLimitTransaction(true);
        serviceConfigDto.setLimitShow(true);
        titleDialog = "Thêm mới";
        FacesUtil.updateView("inforDialogId");
    }

    public void onSearch() {
        lazyDataModel = new LazyDataModel<ServiceConfigDto>() {
            @Override
            public List<ServiceConfigDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filters) {
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
            public ServiceConfigDto getRowData(String rowKey) {
                List<ServiceConfigDto> requestRewardDtoList = getWrappedData();
                Long value = Long.valueOf(rowKey);
                for (ServiceConfigDto obj : requestRewardDtoList) {
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

    public boolean isValidatePackageService(String packageService) {
        String regex = "(.*)(?=.*[A-Z]+)(?=.*[0-9]+)(.*)";

        Pattern p = Pattern.compile(regex);

        if (packageService == null) {
            return false;
        }
        Matcher m = p.matcher(packageService);
        return m.matches();
    }

    public boolean validateDate() {

        if(StringUtils.isBlank(serviceConfigDto.getCode().trim())){
            FacesUtil.addErrorMessage("Bạn vui lòng nhập mã dịch vụ ");
            return false;
        }

        if (!isValidatePackageService(serviceConfigDto.getCode().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập mã dịch vụ phải bao gồm một chữ cái và một số");
            return false;
        }

        long idCheck;
        if (null == serviceConfigDto.getPackageServiceId()) {
            idCheck = 0L;
        } else {
            idCheck = serviceConfigDto.getPackageServiceId();
        }

        List<PackageService> checkCodeExists = serviceConfigRepository.findByCodeExists(serviceConfigDto.getCode(), idCheck);
        if (CollectionUtils.isNotEmpty(checkCodeExists)) {
            FacesUtil.addErrorMessage("Mã dịch vụ đã tồn tại");
            return false;
        }

        if (StringUtils.isBlank(serviceConfigDto.getNameVn().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập Tên tiếng việt ");
            return false;
        }
        if (StringUtils.isBlank(serviceConfigDto.getNameEn().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập Tên tiếng anh ");
            return false;
        }

        if (serviceConfigDto.getMoney() == null) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập phí dịch vụ ");
            return false;
        }

        if(serviceConfigDto.getLimitPost() == false && serviceConfigDto.getPost() == null){
            FacesUtil.addErrorMessage("Bạn vui lòng nhập số lượng đăng bài ");
            return false;
        }

        if(serviceConfigDto.getLimitComment() == false && serviceConfigDto.getComment() == null){
            FacesUtil.addErrorMessage("Bạn vui lòng nhập số lượng bình luận");
            return false;
        }

        if(serviceConfigDto.getLimitShow() == false && serviceConfigDto.getShow() == null){
            FacesUtil.addErrorMessage("Bạn vui lòng nhập số lượng hiển thị ưu tiên");
            return false;
        }

        if(serviceConfigDto.getLimitTransaction() == false && serviceConfigDto.getTransaction() == null){
            FacesUtil.addErrorMessage("Bạn vui lòng nhập phần trăm chiết khấu giao dịch");
            return false;
        }

        return true;
    }

    public void onSave() {
        if (!validateDate()) {
            return;
        }
        if (serviceConfigDto.getPackageServiceId() == null) {
            serviceConfigDto.setCreateDate(new Date());
            serviceConfigDto.setUsername(authorizationController.getAccountDto().getUsername());
        }
        serviceConfigDto.setUpdateDate(new Date());
        serviceConfigDto.setUpdateBy(authorizationController.getAccountDto().getUsername());

        BeanUtils.copyProperties(serviceConfigDto, packageService);
        if(packageService.getLimitComment() == true){
            packageService.setComment(0);
        }
        if(packageService.getLimitPost() == true){
            packageService.setPost(0);
        }
        if(packageService.getLimitShow() == true){
            packageService.setShow(0);
        }
        if(packageService.getLimitTransaction() == true){
            packageService.setTransaction(0);
        }

        serviceConfigRepository.save(packageService);
        FacesUtil.addSuccessMessage("Lưu thành công");
        FacesUtil.closeDialog("inforDialog");
        FacesUtil.updateView("growl");
        onSearch();
    }

    public void onEdit(ServiceConfigDto object) {
        if (object == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        serviceConfigDto = new ServiceConfigDto();
        BeanUtils.copyProperties(object, serviceConfigDto);
        titleDialog = "Sửa";
        FacesUtil.updateView("inforDialogId");
    }

    public void onDelete(Long packageServiceId) {
        if (packageServiceId == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        serviceConfigRepository.deleteById(packageServiceId);
        FacesUtil.addSuccessMessage("Xóa thành công");
        FacesUtil.updateView("growl");
        onSearch();
    }

    @Override
    protected String getMenuId() {
        return Constant.SERVICE_CONFIG;
    }
}
