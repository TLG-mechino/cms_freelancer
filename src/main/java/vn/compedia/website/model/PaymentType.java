package vn.compedia.website.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PAYMENT_TYPE")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_TYPE_ID", nullable = false)
    private Long paymentTypeId;

    @Column(name = "CODE", length = 20)
    private String code;

    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "STATUS")
    private Integer status;
}
