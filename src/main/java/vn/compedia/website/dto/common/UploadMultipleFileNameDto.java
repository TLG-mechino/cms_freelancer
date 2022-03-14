package vn.compedia.website.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UploadMultipleFileNameDto {
    private Map<String, String> listToShow;
    private Map<String, String> listToAdd;
    private Map<String, String> listToDelete;
}
