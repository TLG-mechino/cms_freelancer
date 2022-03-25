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
import vn.compedia.website.dto.PackageServiceDto;
import vn.compedia.website.dto.PackageServiceSearchDto;
import vn.compedia.website.dto.TransactionDto;
import vn.compedia.website.dto.TransactionSearchDto;
import vn.compedia.website.dto.config.ServiceConfigDto;
import vn.compedia.website.dto.config.ServiceConfigSearchDto;
import vn.compedia.website.model.PackageService;
import vn.compedia.website.model.Transaction;
import vn.compedia.website.repository.PackageServiceRepository;
import vn.compedia.website.repository.RegisterPackageRepository;
import vn.compedia.website.repository.TransactionRepository;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.FacesUtil;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Named
@Scope(value = "session")
public class MnServicePackageController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(MnServicePackageController.class);
    @Inject
    private AuthorizationController authorizationController;

    @Inject
    private MnUserController userController;


    @Autowired
    private RegisterPackageRepository registerPackageRepository;


    private PackageService packageService;
    private String titleDialog;
    private PackageServiceSearchDto searchDto;
    private PackageServiceDto packageServiceDto;
    private LazyDataModel<PackageServiceDto> lazyDataModel;
    private PackageServiceSearchDto searchDtoTemp;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            init();
            resetAll();
        }
    }

    public void resetAll() {
        packageService = new PackageService();
        packageServiceDto = new PackageServiceDto();
        searchDto = new PackageServiceSearchDto();
        searchDtoTemp = new PackageServiceSearchDto();
        onSearch();
    }

    public void resetDialog() {
        packageService = new PackageService();
        titleDialog = "Thêm mới";
        FacesUtil.updateView("inforDialogId");
    }

    public void onSearch() {
        lazyDataModel = new LazyDataModel<PackageServiceDto>() {
            @Override
            public List<PackageServiceDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filters) {
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
                return registerPackageRepository.getAllRegisterPackageByUserName(userController.getUserDtoDetails().getId(), searchDto);
            }

            @Override
            public PackageServiceDto getRowData(String rowKey) {
                List<PackageServiceDto> requestRewardDtoList = getWrappedData();
                Long value = Long.valueOf(rowKey);
                for (PackageServiceDto obj : requestRewardDtoList) {
                    if (obj.getPackageServiceId().equals(value)) {
                        return obj;
                    }
                }
                return null;
            }
        };
        int count = registerPackageRepository.countSearchByUserName(userController.getUserDtoDetails().getId(), searchDto).intValue();
        lazyDataModel.setRowCount(count);
        FacesUtil.updateView("searchForm");
    }

    public void onDelete(Long transactionId) {
        if (transactionId == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        registerPackageRepository.deleteById(transactionId);
        FacesUtil.addSuccessMessage("Xóa thành công");
        FacesUtil.updateView("growl");
        onSearch();
    }

    @Override
    protected String getMenuId() {
        return Constant.MN_PACKAGE_SERVICE;
    }
}
