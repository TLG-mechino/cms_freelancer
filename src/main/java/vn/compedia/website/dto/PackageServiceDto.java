package vn.compedia.website.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.website.model.PackageService;
import vn.compedia.website.model.RegisterPackage;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PackageServiceDto extends RegisterPackage {
    private Long stt;
    private String namePackage;
}
