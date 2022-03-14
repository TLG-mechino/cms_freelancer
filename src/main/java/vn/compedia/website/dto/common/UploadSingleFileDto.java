package vn.compedia.website.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UploadSingleFileDto {
    private String imageToShow;
    private String imageToAdd;
    private List<String> listToDelete;
}
