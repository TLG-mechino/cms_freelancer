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
import vn.compedia.website.dto.entity.UserDto;
import vn.compedia.website.dto.search.UserSearchDto;
import vn.compedia.website.model.*;
import vn.compedia.website.repository.*;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.DbConstant;
import vn.compedia.website.util.FacesUtil;
import vn.compedia.website.util.StringUtil;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Named
@Scope(value = "session")
public class MnUserController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(MnUserController.class);

    @Inject
    private AuthorizationController authorizationController;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CommuneRepository communeRepository;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private DistrictRepository districtRepository;

    private User user;
    private Account account;
    private String emailTemp;
    private String titleDialog;
    private UserDto userDtoDetails;
    private User userChangeProvince;
    private User userChangeDistrict;
    private UserSearchDto searchUserDto;
    private UserSearchDto searchUserTemp;
    private List<SelectItem> listProvince;
    private List<SelectItem> listDistrict;
    private List<SelectItem> listCommune;
    private LazyDataModel<UserDto> lazyDataModel;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            init();
            resetAll();
        }
    }

    public void resetAll() {
        searchUserDto = new UserSearchDto();
        searchUserTemp = new UserSearchDto();
        account = new Account();
        user = new User();
        userChangeProvince = new User();
        userChangeDistrict = new User();
        emailTemp = "";
        onSearch();
    }

    public void onSearch() {
        lazyDataModel = new LazyDataModel<UserDto>() {
            @Override
            public List<UserDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
                searchUserDto.setPageIndex(first);
                searchUserDto.setPageSize(pageSize);
                searchUserDto.setSortField(sortField);
                String sort = "";
                if (sortOrder.equals(SortOrder.ASCENDING)) {
                    sort = "ASC";
                } else {
                    sort = "DESC";
                }
                searchUserDto.setSortOrder(sort);
                BeanUtils.copyProperties(searchUserDto, searchUserTemp);
                return userRepository.search(searchUserDto);
            }

            @Override
            public UserDto getRowData(String rowKey) {
                List<UserDto> dtos = getWrappedData();
                Long value = Long.valueOf(rowKey);
                for (UserDto userDto : dtos) {
                    if (userDto.getAccountId().equals(value)) {
                        return userDto;
                    }
                }
                return null;
            }
        };
        int count = userRepository.countSearch(searchUserDto, DbConstant.ACCOUNT_USER);
        lazyDataModel.setRowCount(count);
        FacesUtil.updateView("searchForm");
    }

    public void onSave() {
        Account checkEmail = accountRepository.findAccountByEmail(userDtoDetails.getEmail());
        emailTemp = accountRepository.findEmail(userDtoDetails.getAccountId());
        if (checkEmail != null && !Objects.equals(checkEmail.getEmail(), emailTemp)) {
            FacesUtil.addErrorMessage("Địa chỉ email đã tồn tại");
            FacesUtil.updateView("growl");
            return;
        }
        BeanUtils.copyProperties(userDtoDetails, user);
        userRepository.save(user);
        FacesUtil.addSuccessMessage("Lưu thành công");
        FacesUtil.closeDialog("inforDialog");
        FacesUtil.updateView("growl");
        onSearch();
    }

    public void onEdit(UserDto object) {
        if (object == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        user = userRepository.findById(object.getId()).get();
        account = accountRepository.findById(object.getAccountId()).get();
        user.setFacebookLink(object.getFacebookLink());
        user.setExperienceAmount(object.getExperienceAmount());
        user.setWorkingHours(object.getWorkingHours());
        user.setProvinceId(object.getProvinceId());
        user.setDistrictId(object.getDistrictId());
        user.setCommuneId(object.getCommuneId());
        user.setAddress(communeRepository.getNameByCommuneId(object.getCommuneId()) + ", " +
                districtRepository.getNameByDistrictId(object.getDistrictId()) + ", " +
                provinceRepository.getNameByProvinceId(object.getProvinceId()));
        account.setFullName(object.getFullName());
        account.setPhone(object.getPhone());
        account.setEmail(object.getEmail());

        if (object.getExperienceAmount() == null) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập số năm kinh nghiệm làm việc ");
            FacesUtil.updateView("growl");
            return;
        }
        if (object.getWorkingHours() == null) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập số giờ làm việc ");
            FacesUtil.updateView("growl");
            return;
        }
        if (StringUtils.isBlank(object.getFullName().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập họ tên");
            FacesUtil.updateView("growl");
            return;
        }
        if (!userDtoDetails.getFullName().matches(Constant.FULL_NAME_PATTERN)) {
            FacesUtil.addErrorMessage("Họ và tên không đúng định dạng");
            FacesUtil.updateView("growl");
            return;
        }

        if (StringUtils.isBlank(userDtoDetails.getPhone().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập số điện thoại ");
            FacesUtil.updateView("growl");
            return;
        }
        if (!userDtoDetails.getPhone().matches(Constant.PHONE_PATTERN)) {
            FacesUtil.addErrorMessage("Số điện thoại không đúng định dạng");
            FacesUtil.updateView("growl");
            return;
        }

        if (StringUtils.isBlank(userDtoDetails.getEmail().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập email ");
            FacesUtil.updateView("growl");
            return;
        }
        if (!userDtoDetails.getEmail().matches(Constant.EMAIL_PATTERN)) {
            FacesUtil.addErrorMessage("Địa chỉ email không đúng định dạng");
            FacesUtil.updateView("growl");
            return;
        }
        if (StringUtils.isBlank(userDtoDetails.getFacebookLink().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập link facebook ");
            FacesUtil.updateView("growl");
            return;
        }
        if (!userDtoDetails.getFacebookLink().matches(Constant.LINK_FACEBOOK_PATTERN)) {
            FacesUtil.addErrorMessage("Link Facebook không đúng định dạng");
            FacesUtil.updateView("growl");
            return;
        }
        if (object.getProvinceId() == null) {
            FacesUtil.addErrorMessage("Bạn vui lòng chọn Tỉnh/Thành phố");
            FacesUtil.updateView("growl");
            return;
        }
        if (object.getDistrictId() == null) {
            FacesUtil.addErrorMessage("Bạn vui lòng chọn Quận/Huyện");
            FacesUtil.updateView("growl");
            return;
        }
        if (object.getCommuneId() == null) {
            FacesUtil.addErrorMessage("Bạn vui lòng chọn Phường/Xã");
            FacesUtil.updateView("growl");
            return;
        }
        userRepository.save(user);
        accountRepository.save(account);
        titleDialog = "Sửa";
        FacesUtil.addSuccessMessage("Lưu thành công");
        FacesUtil.updateView("growl");
    }

    public void findUserDtoById(Long accountId) {
        userDtoDetails = userRepository.findUserDtoById(accountId);

        listProvince = new ArrayList<>();
        List<Province> dataProvince = (List<Province>) provinceRepository.findAll();
        if (CollectionUtils.isNotEmpty(dataProvince)) {
            for (Province province : dataProvince) {
                listProvince.add(new SelectItem(province.getProvinceId(), province.getName()));
            }
        }
        listDistrict = new ArrayList<>();
        List<District> dataDistrict = districtRepository.findAllByProvinceId(userDtoDetails.getProvinceId());
        if (CollectionUtils.isNotEmpty(dataDistrict)) {
            for (District district : dataDistrict) {
                listDistrict.add(new SelectItem(district.getDistrictId(), district.getName()));
            }
        }
        listCommune = new ArrayList<>();
        List<Commune> dataCommune = communeRepository.findAllByDistrictId(userDtoDetails.getDistrictId());
        if (CollectionUtils.isNotEmpty(dataCommune)) {
            for (Commune commune : dataCommune) {
                listCommune.add(new SelectItem(commune.getCommuneId(), commune.getName()));
            }
        }

        FacesUtil.redirect("/user/profile.xhtml");
    }

    public void changeProvince(Long provinceId) {
        userDtoDetails.setCommuneId(0L);
        listDistrict = new ArrayList<>();
        List<District> dataDistrict = districtRepository.findAllByProvinceId(provinceId);
        if (CollectionUtils.isNotEmpty(dataDistrict)) {
            for (District district : dataDistrict) {
                listDistrict.add(new SelectItem(district.getDistrictId(), district.getName()));
            }
        }

    }

    public void changeDistrict(Long districtId) {
        listCommune = new ArrayList<>();
        List<Commune> dataCommune = communeRepository.findAllByDistrictId(districtId);
        if (CollectionUtils.isNotEmpty(dataCommune)) {
            for (Commune commune : dataCommune) {
                listCommune.add(new SelectItem(commune.getCommuneId(), commune.getName()));
            }
        }
    }

    public void blockAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).get();
        account.setStatus(0);
        accountRepository.save(account);
    }

    public void reBlockAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).get();
        account.setStatus(1);
        accountRepository.save(account);
    }

    public void removeChar() {
        if (!StringUtils.isBlank(userDtoDetails.getFullName())) {
            userDtoDetails.setFullName(StringUtil.removeSigned(userDtoDetails.getFullName()).toUpperCase());
        }
    }

    @Override
    protected String getMenuId() {
        return Constant.MN_USER;
    }
}
