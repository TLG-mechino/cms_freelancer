package vn.compedia.website.controller;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.dto.AccountDto;
import vn.compedia.website.dto.LoginDTO;
import vn.compedia.website.jsf.CookieHelper;
import vn.compedia.website.model.Account;
import vn.compedia.website.repository.AccountRepository;
import vn.compedia.website.service.NotificationSystemService;
import vn.compedia.website.service.TokenService;
import vn.compedia.website.util.*;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    private TokenService tokenService;
    @Autowired
    private NotificationSystemService notificationSystemService;

    private List<String> myMenus;
    private boolean saveCookie;
    private AccountDto accountDto;
    private boolean checkNotify = false;
    private String tokenResponse;
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
        saveCookie = false;
        role = Constant.NOT_LOGIN_ID;
        checkNotify = false;
    }

    public void updateMenuByRole() {
        myMenus = new ArrayList<>();
        if (role == Constant.NOT_LOGIN_ID) {
            return;
        }
        myMenus.add(Constant.DASHBOARD);
        myMenus.add(Constant.MN_COMPLAIN);
        myMenus.add(Constant.MN_ACCOUNT);
        myMenus.add(Constant.MN_HASHTAG);
        myMenus.add(Constant.SYS_CONFIG);
        myMenus.add(Constant.MN_USER);
        myMenus.add(Constant.MN_TEST);
        myMenus.add(Constant.MN_JOB);
        myMenus.add(Constant.MN_JOB_RECIPIENT);
        myMenus.add(Constant.MN_POST);
        myMenus.add(Constant.MN_REVIEW);
        myMenus.add(Constant.MN_PACKAGE_SERVICE);
        myMenus.add(Constant.MN_TRANSACTION_USER);
        myMenus.add(Constant.MN_NOTIFICATION);
        myMenus.add(Constant.MN_TEST_EVALUATE);
        myMenus.add(Constant.MN_TRANSACTION);
        myMenus.add(Constant.MN_CUSTOMER_TALK);
        myMenus.add(Constant.SERVICE_CONFIG);
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
            FacesUtil.addErrorMessage("Tài khoản không tồn tại");
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
        if (account.getType() != 2) {
            FacesUtil.addErrorMessage("Tài khoản không có quyền truy cập");
            return;
        }
        LoginDTO loginDTO = new LoginDTO(account.getUsername(), accountDto.getPassword());
        notificationSystemService.setLoginDTO(loginDTO);
//        tokenResponse = notificationSystemService.loginApi(loginDTO);
        notificationSystemService.setAccountId(account.getAccountId());
//        notificationSystemService.setToken(tokenResponse);
        processLogin(account, saveCookie);
    }

    public void processLogin(Account account, Boolean saveCookie) {
        role = Constant.LOGIN_ID;
        updateMenuByRole();
        if (saveCookie) {
            CookieHelper.setCookie((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse(),
                    Constant.COOKIE_ACCOUNT,
                    account.getUsername(),
                    Integer.parseInt(PropertiesUtil.getProperty("cookie.auth.expire")));
            CookieHelper.setCookie((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse(),
                    Constant.COOKIE_PASSWORD,
                    StringUtil.encryptPassword(account.getPassword() + account.getSalt() + account.getCreateDate()),
                    Integer.parseInt(PropertiesUtil.getProperty("cookie.auth.expire")));
        }
        accountDto = new AccountDto();
        BeanUtils.copyProperties(account, accountDto);
        if (accountDto.getFirstLogin() == DbConstant.FIRST_LOGIN) {
            FacesUtil.redirect("/cms/change-password.xhtml");
            account.setFirstLogin(DbConstant.NOT_FIRST_LOGIN);
        } else {
            FacesUtil.redirect("/dashboard.xhtml");
        }
        accountRepository.save(account);
    }

    public Boolean hasLogged() {
        return role != Constant.NOT_LOGIN_ID;
    }

    public boolean hasRole(String menuId) {
        return myMenus.indexOf(menuId) > -1;
    }

    public void logout() {
        CookieHelper.removeCookie(Constant.COOKIE_ACCOUNT);
        CookieHelper.removeCookie(Constant.COOKIE_PASSWORD);
        resetAll();
        FacesUtil.redirect("/login.xhtml");
    }

    public void initPass() {
        accountDto.setOldPassword("");
        accountDto.setNewPassword("");
        accountDto.setRePassword("");
    }

    public boolean isValidatePassword(String password) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public Boolean isValidate() {
        if (StringUtils.isEmpty(accountDto.getOldPassword())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập mật khẩu cũ");
            return false;
        }
        if (StringUtils.isEmpty(accountDto.getNewPassword())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập mật khẩu mới");
            return false;
        }
        if (StringUtils.isEmpty(accountDto.getRePassword())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập lại mật khẩu mới");
            return false;
        }
        if (!isValidatePassword(accountDto.getNewPassword())) {
            FacesUtil.addErrorMessage("Mật khẩu phải từ 6 đến 50 ký  bao gồm chữ cái in thường, chữ cái in hoa, số, ký tự đặc biệt");
            return false;
        }
        if (accountDto.getOldPassword().equals(accountDto.getNewPassword())) {
            FacesUtil.addErrorMessage("Mật khẩu mới không được phép trùng với mật khẩu cũ");
            return false;
        }
        return true;
    }

    public void changePassword() {
        if (StringUtils.isEmpty(accountDto.getOldPassword())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập mật khẩu cũ");
            return;
        }
        if (StringUtils.isEmpty(accountDto.getNewPassword())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập mật khẩu mới");
            return;
        }
        if (StringUtils.isEmpty(accountDto.getRePassword())) {
            FacesUtil.addErrorMessage("Bạn vui lòng nhập lại mật khẩu mới");
            return;
        }
        if (!isValidatePassword(accountDto.getNewPassword())) {
            FacesUtil.addErrorMessage("Mật khẩu phải ít nhất 8 ký tự bao gồm chữ cái in thường, một chữ cái in hoa,một số");
            return;
        }
        if (accountDto.getOldPassword().equals(accountDto.getNewPassword())) {
            FacesUtil.addErrorMessage("Mật khẩu mới không được phép trùng với mật khẩu cũ");
            return;
        }
        accountDto.setFirstLogin(DbConstant.NOT_FIRST_LOGIN);
        String pass = StringUtil.encryptPassword(accountDto.getOldPassword() + accountDto.getSalt());
        if (!pass.equals(accountDto.getPassword())) {
            FacesUtil.addErrorMessage("Mật khẩu cũ không chính xác");
            return;
        }

        if (accountDto.getNewPassword().equals(accountDto.getRePassword())) {
            String newPass = StringUtil.encryptPassword(accountDto.getRePassword() + accountDto.getSalt());
            accountDto.setPassword(newPass);

            Account account = new Account();
            BeanUtils.copyProperties(accountDto, account);
            account.setSalt(StringUtil.generateSalt());
            account.setPassword(StringUtil.encryptPassword(accountDto.getNewPassword(), account.getSalt()));
            accountRepository.save(account);
            initPass();
            checkNotify = true;
            FacesUtil.redirect("/dashboard.xhtml");
            FacesUtil.addSuccessMessage("Đổi mật khẩu thành công");
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

