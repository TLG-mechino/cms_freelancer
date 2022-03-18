package vn.compedia.website.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.controller.common.BaseController;
import vn.compedia.website.dto.AccountDto;
import vn.compedia.website.dto.AccountSearchDto;
import vn.compedia.website.model.Account;
import vn.compedia.website.repository.AccountRepository;
import vn.compedia.website.util.*;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Named
@Scope(value = "session")
public class MnAccountController extends BaseController {

    @Inject
    private AuthorizationController authorizationController;

    @Autowired
    private AccountRepository accountRepository;

    private Account account;
    private String titleDialog;
    private AccountDto accountDto;
    private AccountSearchDto searchDto;
    private LazyDataModel<AccountDto> lazyDataModel;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            init();
            resetAll();
        }
    }

    public void resetAll() {
        account = new Account();
        accountDto = new AccountDto();
        searchDto = new AccountSearchDto();
        searchDto.setAccountId(authorizationController.getAccountDto().getAccountId());
        onSearch();
    }

    public void resetDialog() {
        accountDto = new AccountDto();
        titleDialog = "Thêm mới";
        FacesUtil.updateView("inforDialogId");
    }

    public boolean validate() {

        if (StringUtils.isBlank(accountDto.getEmail())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập địa chỉ email");
            return false;
        }

        if (StringUtils.isBlank(accountDto.getUsername())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập tên đăng nhập");
            return false;
        }
        if (accountDto.getAccountId() == null) {
            List<Account> checkUserName = accountRepository.findAccountByUsername(accountDto.getUsername());
            if (CollectionUtils.isNotEmpty(checkUserName )) {
                FacesUtil.addErrorMessage("Tên đăng nhập đã tồn tại");
                return false;
            }
            List<Account> checkEmail = accountRepository.findAccountByEmail(accountDto.getEmail());
            if (CollectionUtils.isNotEmpty(checkEmail)) {
                FacesUtil.addErrorMessage("Địa chỉ email đã tồn tại");
                return false;
            }
        } else {
            List<Account> checkUserName = accountRepository.findAccountByUsernameExists(accountDto.getAccountId(), accountDto.getUsername());
            if (CollectionUtils.isNotEmpty(checkUserName )) {
                FacesUtil.addErrorMessage("Tên đăng nhập đã tồn tại");
                return false;
            }
            List<Account> checkEmail = accountRepository.findAccountByEmailExists(accountDto.getAccountId(), accountDto.getEmail());
            if (CollectionUtils.isNotEmpty(checkEmail)) {
                FacesUtil.addErrorMessage("Địa chỉ email đã tồn tại");
                return false;
            }
        }

        return true;
    }

    public void onSave() {
        if (!validate()) {
            return;
        }
        String password = RandomStringUtils.randomAlphanumeric(8);
        if (accountDto.getAccountId() == null) {
            accountDto.setSalt(StringUtil.generateSalt());
            accountDto.setPassword(StringUtil.encryptPassword(password + accountDto.getSalt()));
            accountDto.setCreateDate(new Date());
            accountDto.setCreateBy(authorizationController.getAccountDto().getAccountId());
        }
        accountDto.setUpdateDate(new Date());
        accountDto.setUpdateBy(authorizationController.getAccountDto().getAccountId());
        BeanUtils.copyProperties(accountDto, account);
        accountRepository.save(account);

        if (accountDto.getAccountId() == null) {
            EmailUtil.getInstance().sendCreateUser(account.getEmail(), account.getUsername(), password);
            FacesUtil.addSuccessMessage("Lưu thành công. Bạn vui lòng kiểm tra email để lấy mật khẩu đăng nhập hệ thống");
        } else {
            FacesUtil.addSuccessMessage("Lưu thành công");
        }

        FacesUtil.closeDialog("inforDialog");
        onSearch();
    }

    public void onSearch() {
        lazyDataModel = new LazyDataModel<AccountDto>() {
            @Override
            public List<AccountDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
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
                return accountRepository.search(searchDto);
            }

            @Override
            public AccountDto getRowData(String rowKey) {
                List<AccountDto> list = getWrappedData();
                Long value = Long.valueOf(rowKey);
                for (AccountDto obj : list) {
                    if (obj.getAccountId().equals(value)) {
                        return obj;
                    }
                }
                return null;
            }
        };
        int count = accountRepository.countSearch(searchDto).intValue();
        lazyDataModel.setRowCount(count);
        FacesUtil.updateView("searchForm");
    }

    public void onDelete(AccountDto resultDto) {
        Account account = accountRepository.findById(resultDto.getAccountId()).orElse(null);
        if (account == null) {
            return;
        }
        accountRepository.delete(account);
        FacesUtil.addSuccessMessage("Xóa thành công");
        FacesUtil.updateView("growl");
        onSearch();
    }

    public void onEdit(AccountDto resultDto) {
        BeanUtils.copyProperties(resultDto, accountDto);
        titleDialog = "Sửa";
        FacesUtil.updateView("inforDialogId");
    }

    public void resetPassword(AccountDto result) {
        Account account = new Account();
        String password = StringUtil.generateNewPassword();
        result.setPassword(StringUtil.encryptPassword(password + result.getSalt()));
        BeanUtils.copyProperties(result, account);
        accountRepository.save(account);
        EmailUtil.getInstance().sendResetPassword(account.getEmail(), password);
        FacesUtil.addSuccessMessage("Cấp lại mật khẩu thành công. Vui lòng kiểm tra email để lấy mật khẩu đăng nhập hệ thống");
    }

    @Override
    protected String getMenuId() {
        return Constant.MN_ACCOUNT;
    }
}
