package vn.compedia.website.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UploadMultipleFileDto {
    private Set<String> listToShow;
    private Set<String> listToAdd;
    private Set<String> listToDelete;
}
