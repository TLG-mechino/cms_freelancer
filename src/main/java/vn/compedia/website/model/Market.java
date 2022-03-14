package vn.compedia.website.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "market")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Market extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "market_id")
    private Long marketId;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "prefix")
    private Integer prefix;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Integer status;
}
