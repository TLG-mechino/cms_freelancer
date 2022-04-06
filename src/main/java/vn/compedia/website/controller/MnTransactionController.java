package vn.compedia.website.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.controller.common.BaseController;
import vn.compedia.website.dto.ExportExcelDataDto;
import vn.compedia.website.dto.TransactionDto;
import vn.compedia.website.dto.TransactionSearchDto;
import vn.compedia.website.model.*;
import vn.compedia.website.repository.PaymentTypeRepository;
import vn.compedia.website.repository.TransactionRepository;
import vn.compedia.website.util.Constant;
import vn.compedia.website.util.ExportUtil;
import vn.compedia.website.util.FacesUtil;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Log4j
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Named
@Scope(value = "session")
public class MnTransactionController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(MnTransactionController.class);
    @Inject
    private AuthorizationController authorizationController;

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

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
                return transactionRepository.search(searchDto);
            }

            @Override
            public TransactionDto getRowData(String rowKey) {
                List<TransactionDto> requestRewardDtoList = getWrappedData();
                Long value = Long.valueOf(rowKey);
                for (TransactionDto obj : requestRewardDtoList) {
                    if (obj.getTransactionId().equals(value)) {
                        obj.setPaymentTypeName(paymentTypeRepository.findById(obj.getTransactionId()).get().getName());
                        return obj;
                    }
                }
                return null;
            }
        };
        int count = transactionRepository.countSearch(searchDto).intValue();
        lazyDataModel.setRowCount(count);
        FacesUtil.updateView("searchForm");
    }

    public StreamedContent exportDataExcel() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int count = 1;
        ExportExcelDataDto exportExcelDataDto = new ExportExcelDataDto();
        exportExcelDataDto.setTransactionExcel(new ArrayList<>());
        List<TransactionDto> transactionDtos = transactionRepository.exportExcel(searchDto);
        for (TransactionDto var : transactionDtos) {
            var.setStt(count);
            var.setTransactionTimeString(var.getTransactionTime() == null ? "" : simpleDateFormat.format(var.getTransactionTime()));
            count++;
        }
            exportExcelDataDto.getTransactionExcel().addAll(transactionDtos);

        String fileName = ExportUtil.getFileNameExport("Transaction_File");
        try {
            return ExportUtil.downloadExcelFile(exportExcelDataDto, fileName, Constant.TEMPLATE_EXPORT_TRANSACTION, Constant.REPORT_EXPORT_TRANSACTION);
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Có lỗi trong quá trình xuất dữ liệu");
//            log.error("Download excel error", e);
            return null;
        }
    }

    @Override
    protected String getMenuId() {
        return Constant.MN_TRANSACTION;
    }
}
