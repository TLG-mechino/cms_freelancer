package vn.compedia.website.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
import vn.compedia.website.dto.entity.UserDto;
import vn.compedia.website.dto.search.UserSearchDto;
import vn.compedia.website.model.Account;
import vn.compedia.website.model.User;
import vn.compedia.website.repository.AccountRepository;
import vn.compedia.website.repository.UserRepository;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.DbConstant;
import vn.compedia.website.util.FacesUtil;
import vn.compedia.website.util.StringUtil;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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


    private User user;
    private Account account;
    private String titleDialog;
    private LazyDataModel<UserDto>lazyDataModel;
    private UserSearchDto searchUserDto;
    private UserSearchDto searchUserTemp;
    private UserDto userDtoDetails;

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
        onSearch();

    }
    @Override
    protected String getMenuId() {
        return Constant.MN_USER;
    }

    public void onSearch(){
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
                List<UserDto>dtos = getWrappedData();
                Long value = Long.valueOf(rowKey);
                for(UserDto userDto : dtos){
                    if(userDto.getAccountId().equals(value)){
                        return userDto;
                    }
                }
                return null;
            }
        };
        int count = userRepository.countSearch(searchUserDto,DbConstant.ACCOUNT_USER);
        lazyDataModel.setRowCount(count);
        FacesUtil.updateView("searchForm");
    }

    public boolean validateDate() {
        if (StringUtils.isBlank(userDtoDetails.getFullName().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập họ tên");
            return false;
        }

        if (StringUtils.isBlank(userDtoDetails.getPhone().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập số điện thoại ");
            return false;
        }

        if (StringUtils.isBlank(userDtoDetails.getEmail().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập email ");
            return false;
        }
        if (StringUtils.isBlank(userDtoDetails.getFacebookLink().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập link facebook ");
            return false;
        }
        if (StringUtils.isBlank(userDtoDetails.getAddress().trim())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập địa chỉ ");
            return false;
        }

        return true;
    }

    public void onSave() {
        if (!validateDate()) {
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
        BeanUtils.copyProperties(userDtoDetails, user);
        userRepository.save(user);
        titleDialog = "Sửa";
        FacesUtil.addSuccessMessage("Update thành công");
        FacesUtil.updateView("growl");
    }

    public void findUserDtoById(Long accountId){
        userDtoDetails = userRepository.findUserDtoById(accountId);
        FacesUtil.redirect("/user/profile.xhtml");
    }

    public void blockAccount(Long accountId){
        Account account = accountRepository.findById(accountId).get();
        account.setStatus(0);
        accountRepository.save(account);
    }

    public void reBlockAccount(Long accountId){
        Account account = accountRepository.findById(accountId).get();
        account.setStatus(1);
        accountRepository.save(account);
    }

    public void removeChar() {
        if (!StringUtils.isBlank(userDtoDetails.getFullName())) {
            userDtoDetails.setFullName(StringUtil.removeSigned(userDtoDetails.getFullName()).toUpperCase());
        }
    }

}
