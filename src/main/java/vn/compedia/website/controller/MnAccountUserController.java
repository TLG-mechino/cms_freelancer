package vn.compedia.website.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class MnAccountUserController extends BaseController {

    @Inject
    private AuthorizationController authorizationController;

    @Autowired
    private AccountRepository accountRepository;

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
        accountDto = new AccountDto();
        searchDto = new AccountSearchDto();
        searchDto.setAccountId(authorizationController.getAccountDto().getAccountId());
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
                return accountRepository.search(searchDto, DbConstant.ACCOUNT_USER);
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
        int count = accountRepository.countSearch(searchDto, DbConstant.ACCOUNT_USER).intValue();
        lazyDataModel.setRowCount(count);
        FacesUtil.updateView("searchForm");
    }

    public void changeStatus(AccountDto resultDto) {
        Account account = accountRepository.findById(resultDto.getAccountId()).orElse(null);
        if (account == null) {
            return;
        }
        if (resultDto.getStatus() == DbConstant.ACTIVE_STATUS) {
            account.setStatus(DbConstant.INACTIVE_STATUS);
        } else {
            account.setStatus(DbConstant.ACTIVE_STATUS);
        }
        accountRepository.delete(account);
        FacesUtil.addSuccessMessage("Xóa thành công");
        FacesUtil.updateView("growl");
        onSearch();
    }

    @Override
    protected String getMenuId() {
        return Constant.MN_ACCOUNT;
    }
}
