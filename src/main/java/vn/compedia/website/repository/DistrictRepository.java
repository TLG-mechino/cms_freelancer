package vn.compedia.website.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import vn.compedia.website.model.District;

import java.util.List;

public interface DistrictRepository extends CrudRepository<District, Long> {

    @Query("select d from District d where d.provinceId = :provinceId")
    List<District> findAllByProvinceId(@Param("provinceId") Long provinceId);

    @Query("select d.name from District d where d.districtId = :districtId")
    String getNameByDistrictId(@Param("districtId") Long districtId);
}
