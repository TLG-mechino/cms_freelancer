package vn.compedia.website.dto.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.website.model.PackageService;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class serviceConfigDto extends PackageService {
    private Long stt;
}
