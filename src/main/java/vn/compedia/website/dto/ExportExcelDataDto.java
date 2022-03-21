package vn.compedia.website.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExportExcelDataDto {
    private List<TransactionDto> transactionExcel;
}
