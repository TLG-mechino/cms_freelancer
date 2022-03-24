package vn.compedia.website.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UploadMultipleFileDto {
    private List<String> listToShow;
    private List<String> listToAdd;
    private List<String> listToDelete;
}
