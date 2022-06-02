package vn.compedia.website.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vn.compedia.website.model.ConfigSystem;

public interface SystemConfigRepository extends CrudRepository<ConfigSystem, Long> {
    @Query("select u.value from ConfigSystem u where u.code = ?1")
    String getValue(String code);

    @Query("select cs from ConfigSystem cs where cs.code = ?1")
    ConfigSystem findByCode(String code);
}
