package vn.compedia.website.controller;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.dto.AccountDto;
import vn.compedia.website.model.Account;
import vn.compedia.website.repository.AccountRepository;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.DbConstant;
import vn.compedia.website.util.FacesUtil;
import vn.compedia.website.util.StringUtil;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Setter
@Getter
@Scope(value = "session")
@Named
public class AuthorizationController implements Serializable {

    @Autowired
    private AccountRepository accountRepository;

    private String password;
    private String newPassword;
    private List<String> myMenus;
    private String reNewPassword;
    private String roleName = "";
    private AccountDto accountDto;
    private boolean checkNotify = false;
    private int role = Constant.NOT_LOGIN_ID;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            resetAll();
            updateMenuByRole();
        }
    }

    public void resetAll() {
        if (checkNotify) {
            FacesUtil.addSuccessMessage("Đổi mật khẩu thành công. Bạn vui lòng đăng nhập lại hệ thống");
        }

        accountDto = new AccountDto();
        myMenus = new ArrayList<>();
        role = Constant.NOT_LOGIN_ID;
        checkNotify = false;
    }

    public void updateMenuByRole() {
        myMenus = new ArrayList<>();
        if (role == Constant.NOT_LOGIN_ID) {
            return;
        }
        myMenus.add(Constant.DASHBOARD);
        myMenus.add(Constant.MN_MARKET);
        myMenus.add(Constant.MN_ACCOUNT);
        myMenus.add(Constant.SYS_CONFIG);
    }

    public void login() {
        if (StringUtils.isBlank(accountDto.getUsername())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập tên đăng nhập");
            return;
        }
        if (StringUtils.isEmpty(accountDto.getPassword())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập mật khẩu");
            return;
        }
        Account account = accountRepository.getByUsername(accountDto.getUsername());
        if (account == null) {
            FacesUtil.addErrorMessage("Tên đăng nhập hoặc mật khẩu không chính xác bạn vui lòng kiểm tra lại");
            return;
        }
        if (!account.getStatus().equals(DbConstant.ACTIVE_STATUS)) {
            FacesUtil.addErrorMessage("Tài khoản đã bị khóa. Bạn vui lòng liên hệ quản trị viên hệ thống để được giải quyết");
            return;
        }
        if (!account.getPassword().equals(StringUtil.encryptPassword(accountDto.getPassword() + account.getSalt()))) {
            FacesUtil.addErrorMessage("Tên đăng nhập hoặc mật khẩu không chính xác bạn vui lòng kiểm tra lại");
            return;
        }
        processLogin(account);
    }

    public void processLogin(Account account) {
        role = Constant.LOGIN_ID;
        roleName = DbConstant.DEFAULT_ROLE;
        updateMenuByRole();
        accountDto = new AccountDto();
        BeanUtils.copyProperties(account, accountDto);
        FacesUtil.redirect("/dashboard.xhtml");
    }

    public Boolean hasLogged() {
        return role != Constant.NOT_LOGIN_ID;
    }

    public boolean hasRole(String menuId) {
        return myMenus.indexOf(menuId) > -1;
    }

    public void logout() {
        resetAll();
        FacesUtil.redirect("/login.xhtml");
    }

    public void initPass() {
        if (!hasLogged()) {
            FacesUtil.redirect("/login.xhtml");
            return;
        }
        password = "";
        newPassword = "";
        reNewPassword = "";
    }

    public boolean isValidatePassword(String password) {
        String regex = "^.{6,50}$";

        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public Boolean isValidate() {
        if (StringUtils.isEmpty(password)) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập mật khẩu cũ");
            return false;
        }
        if (StringUtils.isEmpty(newPassword)) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập mật khẩu mới");
            return false;
        }
        if (StringUtils.isEmpty(reNewPassword)) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập lại mật khẩu mới");
            return false;
        }
        if (!isValidatePassword(newPassword)) {
            FacesUtil.addErrorMessage("Mật khẩu phải từ 6 đến 50 ký  bao gồm chữ cái in thường, chữ cái in hoa, số, ký tự đặc biệt");
            return false;
        }
        if (password.equals(newPassword)) {
            FacesUtil.addErrorMessage("Mật khẩu mới không được phép trùng với mật khẩu cũ");
            return false;
        }
        return true;
    }

    public void changePassword() {
        if (!isValidate()) {
            return;
        }

        String pass = StringUtil.encryptPassword(password + accountDto.getSalt());
        if (!pass.equals(accountDto.getPassword())) {
            FacesUtil.addErrorMessage("Mật khẩu cũ không chính xác");
            return;
        }

        if (newPassword.equals(reNewPassword)) {
            String newPass = StringUtil.encryptPassword(reNewPassword + accountDto.getSalt());
            accountDto.setPassword(newPass);

            Account account = new Account();
            BeanUtils.copyProperties(accountDto, account);

            accountRepository.save(account);
            initPass();
            checkNotify = true;
            FacesUtil.redirect("/login.xhtml");
        } else {
            FacesUtil.addErrorMessage("Mật khẩu mới và nhập lại mật khẩu không trùng nhau");
        }
    }

    public void initInfo() {
        if (!hasLogged()) {
            FacesUtil.redirect("/dashboard.xhtml");
            return;
        }
        if (!FacesContext.getCurrentInstance().isPostback()) {
            accountDto = accountRepository.getAccountInfo(accountDto.getAccountId());
        }
    }

    public void redirectPage(String url) {
        FacesUtil.redirect(url);
    }
}

