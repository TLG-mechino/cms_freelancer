package vn.compedia.website.controller.common;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import vn.compedia.website.controller.AuthorizationController;
import vn.compedia.website.model.Account;
import vn.compedia.website.repository.AccountRepository;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.FacesUtil;
import vn.compedia.website.util.FileUtil;

import javax.inject.Inject;
import java.io.Serializable;
import java.text.ParseException;

public abstract class BaseController implements Serializable {

    @Inject
    AuthorizationController authorizationController;
    @Autowired
    private AccountRepository accountRepository;

    protected abstract String getMenuId();

    public void init() {
        if (!authorizationController.hasLogged()) {
            FacesUtil.redirect("/login.xhtml");
            return;
        }

        Account account = accountRepository.findById(authorizationController.getAccountDto().getAccountId()).orElse(null);

        if (account == null) {
            authorizationController.resetAll();
            FacesUtil.redirect("/login.xhtml");
            return;
        }

        if (!authorizationController.hasRole(getMenuId())) {
            FacesUtil.redirect("/errors/access.xhtml");
        }
//
//        if (authorizationController.getAccountDto().getFirstLogin() != DbConstant.NOT_FIRST_LOGIN) {
//            FacesUtil.redirect("/login.xhtml");
//        }
    }

    public void setErrorForm(String error) {
        FacesUtil.addErrorMessage(Constant.ERROR_MESSAGE_ID, error);
    }


    public StreamedContent getFileDownload(String filePath) {
        StreamedContent streamedContent = FileUtil.getDownloadFileFromDatabase(filePath);
        if (streamedContent == null) {
            FacesUtil.addErrorMessage("File không tồn tại");
        }
        return streamedContent;
    }

    public StreamedContent getFileDownload(String filePath, String fileName) {
        StreamedContent streamedContent = FileUtil.getDownloadFileFromDatabase(filePath, fileName);
        if (streamedContent == null) {
            FacesUtil.addErrorMessage("File không tồn tại");
        }
        return streamedContent;
    }
}
