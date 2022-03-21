package vn.compedia.website.repository.config;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import vn.compedia.website.model.PackageService;

import java.util.List;

public interface ServiceConfigRepository extends CrudRepository<PackageService, Long>, ServiceConfigRepositoryCustom{
    @Query("select p from PackageService p where p.code = :code and p.packageServiceId <> :serviceId")
    List<PackageService> findByCodeExists(@Param("code") String code, @Param("serviceId") Long serviceId);
}
