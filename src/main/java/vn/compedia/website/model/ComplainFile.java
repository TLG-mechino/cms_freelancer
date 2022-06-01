package vn.compedia.website.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "complain_file")
@Getter
@Setter
public class ComplainFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPLAIN_FILE_ID", nullable = false)
    private Long complainFileId;

    @Column(name = "FILE_NAME", length = 200)
    private String fileName;

    @Column(name = "FILE_PATH", length = 200)
    private String filePath;

    @Column(name = "FILE_TYPE")
    private Integer fileType;

    @Column(name = "COMPLAIN_ID")
    private Long complainId;

}