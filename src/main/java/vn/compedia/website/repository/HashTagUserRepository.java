package vn.compedia.website.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import vn.compedia.website.model.HashtagUser;

import java.util.Optional;

public interface HashTagUserRepository extends CrudRepository<HashtagUser, Long> {

    @Query("select hu from HashtagUser hu where hu.username = :username and hu.hashtagId = :hashtagId")
    Optional<HashtagUser> findByUsernameAndHashtagId(@Param("username") String username, @Param("hashtagId") Long hashtagId);
}
