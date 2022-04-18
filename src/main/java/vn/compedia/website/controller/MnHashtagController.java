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
import vn.compedia.website.dto.HashtagDto;
import vn.compedia.website.dto.HashtagSearchDto;
import vn.compedia.website.model.Hashtag;
import vn.compedia.website.repository.HashtagRepository;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.FacesUtil;
import vn.compedia.website.util.StringUtil;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Named
@Scope(value = "session")
public class MnHashtagController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(MnHashtagController.class);
    @Inject
    private AuthorizationController authorizationController;

    @Autowired
    private HashtagRepository hashtagRepository;

    private Hashtag hashtag;
    private String titleDialog;
    private HashtagSearchDto searchDto;
    private HashtagDto hashtagDto;
    private LazyDataModel<HashtagDto> lazyDataModel;
    private HashtagSearchDto searchDtoTemp;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            init();
            resetAll();
        }
    }

    public void resetAll() {
        hashtag = new Hashtag();
        hashtagDto = new HashtagDto();
        searchDto = new HashtagSearchDto();
        onSearch();
    }

    public void resetDialog() {
        hashtagDto = new HashtagDto();
        titleDialog = "Thêm mới";
        FacesUtil.updateView("inforDialogId");
    }

    public void onSearch() {
        lazyDataModel = new LazyDataModel<HashtagDto>() {
            @Override
            public List<HashtagDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filters) {
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
                return hashtagRepository.search(searchDto);
            }

            @Override
            public HashtagDto getRowData(String rowKey) {
                List<HashtagDto> requestRewardDtoList = getWrappedData();
                Long value = Long.valueOf(rowKey);
                for (HashtagDto obj : requestRewardDtoList) {
                    if (obj.getHashtagId().equals(value)) {
                        return obj;
                    }
                }
                return null;
            }
        };
        int count = hashtagRepository.countSearch(searchDto).intValue();
        lazyDataModel.setRowCount(count);
        FacesUtil.updateView("searchForm");
    }

    public boolean isValidateHashTag(String hashtag) {
        String regex = "(.*)([A-Z]+)(\\d+)(.*)";

        Pattern p = Pattern.compile(regex);

        if (hashtag == null) {
            return false;
        }
        Matcher m = p.matcher(hashtag);
        return m.matches();
    }

    public boolean validateDate() {

        if (StringUtils.isBlank(hashtagDto.getCode().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập mã hashtag ");
            return false;
        }

        if (!isValidateHashTag(hashtagDto.getCode())) {
            FacesUtil.addErrorMessage("Mã hashtag phải bao gồm một chữ cái và một số");
            return false;
        }

        long idCheck;
        if (null == hashtagDto.getHashtagId()) {
            idCheck = 0L;
        } else {
            idCheck = hashtagDto.getHashtagId();
        }

        List<Hashtag> checkCodeExists = hashtagRepository.findByCodeExists(hashtagDto.getCode(), idCheck);
        if (CollectionUtils.isNotEmpty(checkCodeExists)) {
            FacesUtil.addErrorMessage("Mã hashtag đã tồn tại");
            return false;
        }

        if (StringUtils.isBlank(hashtagDto.getTitleVn().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập tiêu đề tiếng việt ");
            return false;
        }

        if (StringUtils.isBlank(hashtagDto.getTitleEn().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập tiêu đề tiếng anh ");
            return false;
        }


        if (StringUtils.isBlank(hashtagDto.getDescriptionVn().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập mô tả tiếng việt ");
            return false;
        }
        if (StringUtils.isBlank(hashtagDto.getDescriptionEn().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập mô tả tiếng anh ");
            return false;
        }

        return true;
    }

    public void onSave() {
        if (!validateDate()) {
            return;
        }
        if (hashtagDto.getHashtagId() == null) {
            hashtagDto.setCreateDate(new Date());
            hashtagDto.setCreateBy(authorizationController.getAccountDto().getUsername());
        }
        hashtagDto.setUpdateDate(new Date());
        hashtagDto.setUpdateBy(authorizationController.getAccountDto().getUsername());

        BeanUtils.copyProperties(hashtagDto, hashtag);
        hashtagRepository.save(hashtag);
        FacesUtil.addSuccessMessage("Lưu thành công");
        FacesUtil.closeDialog("inforDialog");
        FacesUtil.updateView("growl");
        onSearch();
    }

    public void onEdit(HashtagDto object) {
        if (object == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        hashtagDto = new HashtagDto();
        BeanUtils.copyProperties(object, hashtagDto);
        titleDialog = "Sửa";
        FacesUtil.updateView("inforDialogId");
    }

    public void onDelete(Long hashtagId) {
        if (hashtagId == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        hashtagRepository.deleteById(hashtagId);
        FacesUtil.addSuccessMessage("Xóa thành công");
        FacesUtil.updateView("growl");
        onSearch();
    }

    @Override
    protected String getMenuId() {
        return Constant.MN_HASHTAG;
    }

    public void removeChar() {
        if (!StringUtils.isBlank(hashtagDto.getCode())) {
            hashtagDto.setCode(StringUtil.removeSigned(hashtagDto.getCode()).toUpperCase());
        }
    }
}
