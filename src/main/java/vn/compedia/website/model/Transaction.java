package vn.compedia.website.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "transaction")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION", nullable = false)
    private Long transactionId;

    @Column(name = "SENDER", length = 20)
    private String sender;

    @Column(name = "RECIPIENT", length = 20)
    private String recipient;

    @Column(name = "TRANSACTION_TIME")
    private Date transactionTime;

    @Column(name = "AMOUNT_OF_MONEY")
    private Double amountOfMoney;

    @Column(name = "DISCOUNT_MONEY")
    private Double discountMoney;

    @Column(name = "FINAL_MONEY")
    private Double finalMoney;

    @Column(name = "CODE", length = 20)
    private String code;

    @Column(name = "PAYMENT_TYPE_ID")
    private Integer paymentTypeId;

    @Column(name = "STATUS")
    private Integer status;
}