package vn.compedia.website.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID")
    private Long id;
    @Column(name = "SENDER")
    private String sender;
    @Column(name = "RECIPIENT")
    private String recipient;
    @Column(name = "TRANSACTION_TIME")
    private Timestamp transactionTime;
    @Column(name = "AMOUNT_OF_MONEY")
    private Double amountOfMoney;
    @Column(name = "DISCOUNT_MONEY")
    private Double discountMoney;
    @Column(name = "FINAL_MONEY")
    private Double finalMoney;
    @Column(name = "CODE")
    private String codeTransaction;
    @Column(name = "PAYMENT_TYPE_ID")
    private Integer paymentTypeId;
    @Column(name = "STATUS")
    private Integer status;

}
