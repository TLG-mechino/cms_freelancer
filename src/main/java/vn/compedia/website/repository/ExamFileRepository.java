package vn.compedia.website.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vn.compedia.website.model.ExamFile;

import java.util.List;

public interface ExamFileRepository extends CrudRepository<ExamFile, Long> {
    @Query("SELECT ef FROM ExamFile ef WHERE ef.objectId = ?1 AND ef.type = 1")
    List<ExamFile> findAllByExamId(Long examId);

    @Query("SELECT ef FROM ExamFile ef WHERE ef.objectId = ?1 AND ef.type = 2")
    List<ExamFile> findAllByUserExamId(Long examId);
}
