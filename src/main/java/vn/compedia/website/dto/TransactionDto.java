package vn.compedia.website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.website.model.Transaction;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto extends Transaction {
    private int stt;
    private String transactionTimeString;
    private String paymentTypeName;
    private String statusString;
}
