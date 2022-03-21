package vn.compedia.website.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.compedia.website.model.RegisterPackage;

@Repository
public interface RegisterPackageRepository extends CrudRepository<RegisterPackage,Long>,RegisterPackageRepositoryCustom {

}
