package vn.compedia.website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadWithFilenameDto {
    private Long id;
    private String fileName;
    private String filePath;
    private String fileType;
    private Integer type;
}
