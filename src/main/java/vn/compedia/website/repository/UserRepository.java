package vn.compedia.website.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.compedia.website.model.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long> ,UserRepositoryCustom{

    @Query(value = "select * from USER u , ACCOUNT ac  where u.USERNAME = ac.USERNAME and u.USERNAME =:userName and ac.STATUS = 1", nativeQuery = true)
    User getUsersByUserName(@Param("userName")String userName);

}
