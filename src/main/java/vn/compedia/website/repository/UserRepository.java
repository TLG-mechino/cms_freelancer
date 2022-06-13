package vn.compedia.website.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.compedia.website.model.User;

@Repository
public interface UserRepository extends CrudRepository<User,String> ,UserRepositoryCustom{

    @Query(value = "select * from user u , account ac  where u.USERNAME = ac.USERNAME and u.USERNAME =:username and ac.STATUS = 1", nativeQuery = true)
    User getUsersByUserName(@Param("username")String username);

    @Query(value = "select count(u.USERNAME) from user u inner join account acc on u.USERNAME = acc.USERNAME where acc.TYPE = 1 and acc.STATUS = 1", nativeQuery = true)
    Integer totalUser();

}
