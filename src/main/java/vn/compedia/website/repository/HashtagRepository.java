package vn.compedia.website.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.compedia.website.model.Hashtag;

import java.util.List;

@Repository
public interface HashtagRepository extends CrudRepository<Hashtag, Long>, HashtagRepositoryCustom {

    @Query("select h from Hashtag h where h.code = :code and h.hashtagId <> :hashtagId")
    List<Hashtag> findByCodeExists(@Param("code") String code, @Param("hashtagId") Long hashtagId);

}
