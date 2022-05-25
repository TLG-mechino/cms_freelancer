package vn.compedia.website.repository;

import org.springframework.data.repository.query.Param;
import vn.compedia.website.model.Exam;

import java.util.List;

public interface ExamRepositoryCustom {
    List<Exam> findAllByHashtagIdAndFinishExam(@Param("username") String username, @Param("hashtagId") Long hashtagId);
}
