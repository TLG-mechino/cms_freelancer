package vn.compedia.website.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "SERVICE_TYPE")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ServiceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SERVICE_TYPE_ID", nullable = false)
    private Long serviceTypeId;

    @Column(name = "CODE", length = 20)
    private String code;

    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "STATUS")
    private Integer status;
}
