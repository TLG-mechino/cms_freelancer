package vn.compedia.website.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JOB_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "MONEY_FROM")
    private Double moneyFrom;

    @Column(name = "MONEY_TO")
    private Double moneyTo;

    @Column(name = "CREATE_DATE")
    private Date createTime;

    @Column(name = "UPDATE_DATE")
    private Date modifiedTime;

    @Column(name = "STATUS")
    private Integer status;
}
