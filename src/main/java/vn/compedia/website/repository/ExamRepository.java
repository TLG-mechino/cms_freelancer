package vn.compedia.website.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.compedia.website.model.Exam;
import vn.compedia.website.model.HashtagUser;

import java.util.List;

@Repository
public interface ExamRepository extends CrudRepository<Exam, Long>, ExamRepositoryCustom {

    @Query(value = "select e.* from Exam e left join user_exam u on (u.USERNAME = :username and u.EXAM_ID = e.EXAM_ID) " +
            " where e.HASHTAG_ID = :hashtagId and u.score >= e.score", nativeQuery = true)
    List<Exam> findAllByHashtagIdAndFinishExam(@Param("username") String username, @Param("hashtagId") Long hashtagId);
}
