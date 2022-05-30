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
import vn.compedia.website.dto.PostDto;
import vn.compedia.website.dto.PostSearchDto;
import vn.compedia.website.dto.TransactionDto;
import vn.compedia.website.dto.TransactionSearchDto;
import vn.compedia.website.model.PaymentType;
import vn.compedia.website.model.Post;
import vn.compedia.website.model.Transaction;
import vn.compedia.website.repository.PaymentTypeRepository;
import vn.compedia.website.repository.PostRepository;
import vn.compedia.website.repository.TransactionRepository;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.FacesUtil;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Named
@Scope(value = "session")
public class MnTransactionUserController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(MnTransactionUserController.class);
    @Inject
    private AuthorizationController authorizationController;

    @Inject
    private MnUserController userController;
    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private Transaction transaction;
    private String titleDialog;
    private TransactionSearchDto searchDto;
    private TransactionDto transactionDto;
    private LazyDataModel<TransactionDto> lazyDataModel;
    private TransactionSearchDto searchDtoTemp;
    private List<PaymentType> paymentTypeList;

    public void initData() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            init();
            resetAll();
        }
    }

    public void resetAll() {
        transaction = new Transaction();
        paymentTypeList = new ArrayList<>();
        transactionDto = new TransactionDto();
        searchDto = new TransactionSearchDto();
        searchDtoTemp = new TransactionSearchDto();
        paymentTypeList = (List<PaymentType>) paymentTypeRepository.findAll();
        onSearch();
    }

    public void resetDialog() {
        transactionDto = new TransactionDto();
        titleDialog = "Thêm mới";
        FacesUtil.updateView("inforDialogId");
    }

    public void onSearch() {
        lazyDataModel = new LazyDataModel<TransactionDto>() {
            @Override
            public List<TransactionDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filters) {
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
                return transactionRepository.getAllByUserName(userController.getUserDtoDetails().getFullName(), searchDto);
            }

            @Override
            public TransactionDto getRowData(String rowKey) {
                List<TransactionDto> requestRewardDtoList = getWrappedData();
                Long value = Long.valueOf(rowKey);
                for (TransactionDto obj : requestRewardDtoList) {
                    if (obj.getTransactionId().equals(value)) {
                        return obj;
                    }
                }
                return null;
            }
        };
        int count = transactionRepository.countSearchByUserName(userController.getUserDtoDetails().getId(), searchDto).intValue();
        lazyDataModel.setRowCount(count);
        FacesUtil.updateView("searchForm");
    }

    public void onDelete(Long transactionId) {
        if (transactionId == null) {
            FacesUtil.addErrorMessage("Không tồn tại thông tin");
            FacesUtil.updateView("growl");
            return;
        }
        transactionRepository.deleteById(transactionId);
        FacesUtil.addSuccessMessage("Xóa thành công");
        FacesUtil.updateView("growl");
        onSearch();
    }


    @Override
    protected String getMenuId() {
        return Constant.MN_TRANSACTION_USER;
    }
}
