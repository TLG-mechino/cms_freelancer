package vn.compedia.website.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import vn.compedia.website.model.Commune;

import java.util.List;

public interface CommuneRepository extends CrudRepository<Commune, Long> {

    @Query("select c from Commune c where c.districtId = :districtId")
    List<Commune> findAllByDistrictId(@Param("districtId") Long districtId);

    @Query("select c.name from Commune c where c.communeId = :communeId")
    String getNameByCommuneId(@Param("communeId") Long communeId);
}
