package vn.compedia.website.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.website.dto.UploadWithFilenameDto;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UploadMultipleFileNameDto {
    private List<UploadWithFilenameDto> listToShow;
    private List<UploadWithFilenameDto> listToAdd;
    private List<UploadWithFilenameDto> listToDelete;
}
