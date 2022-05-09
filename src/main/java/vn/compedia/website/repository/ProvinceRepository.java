package vn.compedia.website.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import vn.compedia.website.model.Province;

public interface ProvinceRepository extends CrudRepository<Province, Long> {

    @Query("select p.name from Province p where p.provinceId = :provinceId")
    String getNameByProvinceId(@Param("provinceId") Long provinceId);
}
