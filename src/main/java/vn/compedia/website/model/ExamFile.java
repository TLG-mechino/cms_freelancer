package vn.compedia.website.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "exam_file")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXAM_FILE_ID", nullable = false)
    private Long examFileId;

    @Column(name = "FILE_NAME", length = 200)
    private String fileName;

    @Column(name = "FILE_PATH", length = 200)
    private String filePath;

    @Column(name = "FILE_TYPE", length = 10)
    private String fileType;

    @Column(name = "OBJECT_ID")
    private Long objectId;

    @Column(name = "TYPE")
    private Integer type;


}
