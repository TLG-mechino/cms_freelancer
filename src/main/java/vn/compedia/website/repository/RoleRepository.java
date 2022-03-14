package vn.compedia.website.repository;

import org.springframework.data.repository.CrudRepository;
import vn.compedia.website.model.Role;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Long> {
    List<Role> findAll();

    Role findRoleByRoleId(Long id);
}
