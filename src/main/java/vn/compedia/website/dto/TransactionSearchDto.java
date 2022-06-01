package vn.compedia.website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSearchDto extends BaseSearchDto{
    private String status;
    private Long paymentTypeSearch;
    private Double greatMoney;
    private Double lessMoney;
}
