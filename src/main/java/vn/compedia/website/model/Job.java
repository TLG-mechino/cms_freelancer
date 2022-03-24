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
@Table(name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "JOB_ID")
    private Long id;
    @Column(name = "CODE")
    private String codeJob;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "USERNAME")
    private String userName;
    @Column(name = "START_TIME")
    private Timestamp startTime;
    @Column(name = "END_TIME")
    private Timestamp endTime;
    @Column(name = "MONEY_FROM")
    private Double moneyFrom;
    @Column(name = "MONEY_TO")
    private Double moneyTo;
    @Column(name = "CREATE_DATE")
    private Timestamp createTime;
    @Column(name = "UPDATE_DATE")
    private Timestamp modifiedTime;
    @Column(name="STATUS")
    private Integer status;
}
