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
import vn.compedia.website.dto.StickerSearchDto;
import vn.compedia.website.dto.StickersDto;
import vn.compedia.website.model.Hashtag;
import vn.compedia.website.model.Sticker;
import vn.compedia.website.repository.StickersRepository;
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
public class StickersConfigController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ServiceConfigController.class);
    @Inject
    private AuthorizationController authorizationController;

    @Autowired
    private StickersRepository stickersRepository;

    private Sticker sticker;
    private String titleDialog;
    private StickerSearchDto searchDto;
    private StickersDto stickersDto;
    private LazyDataModel<StickersDto> lazyDataModel;
    private StickerSearchDto searchDtoTemp;
    private List<SelectItem> listPosition;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            init();
            resetAll();
        }
    }

    public void resetAll() {
        sticker = new Sticker();
        stickersDto = new StickersDto();
        searchDto = new StickerSearchDto();
        searchDtoTemp = new StickerSearchDto();

        listPosition = new ArrayList<>();
        List<Integer> data = new ArrayList<>();
        for (int i = 1; i <= stickersRepository.totalStickers() + 1; i++) {
            data.add(i);
        }
//        data.removeAll(stickersRepository.listPositionRemove());
        if (CollectionUtils.isNotEmpty(data)) {
            for (Integer i : data) {
                listPosition.add(new SelectItem(i, i.toString()));
            }
        }
        onSearch();
    }

    public void resetDialog() {
        stickersDto = new StickersDto();
        titleDialog = "Thêm mới";
        FacesUtil.updateView("inforDialogId");
    }

    public void onSearch() {
        lazyDataModel = new LazyDataModel<StickersDto>() {
            @Override
            public List<StickersDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filters) {
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
                return stickersRepository.search(searchDto);
            }

            @Override
            public StickersDto getRowData(String rowKey) {
                List<StickersDto> requestRewardDtoList = getWrappedData();
                Long value = Long.valueOf(rowKey);
                for (StickersDto obj : requestRewardDtoList) {
                    if (obj.getStickersId().equals(value)) {
                        return obj;
                    }
                }
                return null;
            }
        };
        int count = stickersRepository.countSearch(searchDto).intValue();
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

        if(StringUtils.isBlank(stickersDto.getCode().trim())){
            FacesUtil.addErrorMessage("Bạn vui lòng nhập mã dịch vụ ");
            return false;
        }

        if (!isValidatePackageService(stickersDto.getCode().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập mã dịch vụ phải bao gồm một chữ cái và một số");
            return false;
        }

        long idCheck;
        if (null == stickersDto.getStickersId()) {
            idCheck = 0L;
        } else {
            idCheck = stickersDto.getStickersId();
        }

        List<Sticker> checkCodeExists = stickersRepository.findByCodeExists(stickersDto.getCode(), idCheck);
        if (CollectionUtils.isNotEmpty(checkCodeExists)) {
            FacesUtil.addErrorMessage("Mã nhãn đã tồn tại");
            return false;
        }

        if (StringUtils.isBlank(stickersDto.getNameVn().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập tên Tiếng Việt ");
            return false;
        }
        if (StringUtils.isBlank(stickersDto.getNameEn().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập tên Tiếng Anh ");
            return false;
        }

        if (StringUtils.isBlank(stickersDto.getDescriptionVn().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập mô tả tiếng việt ");
            return false;
        }

        if (StringUtils.isBlank(stickersDto.getDescriptionEn().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập mô tả tiếng việt ");
            return false;
        }

        if (stickersDto.getMoney() == null) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập phí dịch vụ ");
            return false;
        }
        return true;
    }

    public void onSave() {
        if (!validateDate()) {
            return;
        }
        if (stickersDto.getStickersId() == null) {
            stickersDto.setCreateDate(new Date());
            stickersDto.setCreateBy(authorizationController.getAccountDto().getUsername());
        }
        stickersDto.setUpdateDate(new Date());
        stickersDto.setUpdateBy(authorizationController.getAccountDto().getUsername());

        BeanUtils.copyProperties(stickersDto, sticker);
        stickersRepository.save(sticker);
        FacesUtil.addSuccessMessage("Lưu thành công");
        FacesUtil.closeDialog("inforDialog");
        FacesUtil.updateView("growl");
        onSearch();
    }

    public void onEdit(StickersDto object) {
        if (object == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        stickersDto = new StickersDto();
        BeanUtils.copyProperties(object, stickersDto);
        titleDialog = "Sửa";
        FacesUtil.updateView("inforDialogId");
    }

    public void onDelete(Long stickersId) {
        if (stickersId == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        stickersRepository.deleteById(stickersId);
        FacesUtil.addSuccessMessage("Xóa thành công");
        FacesUtil.updateView("growl");
        onSearch();
    }

    @Override
    protected String getMenuId() {
        return Constant.STICKERS_CONFIG;
    }
}
