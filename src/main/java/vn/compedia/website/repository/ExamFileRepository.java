package vn.compedia.website.repository;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.compedia.website.model.ExamFile;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ExamFileRepository extends CrudRepository<ExamFile, Long> {

    @Query("SELECT ef FROM ExamFile ef WHERE ef.objectId = ?1 AND ef.type = 1")
    List<ExamFile> findAllByExamId(Long examId);

    @Query("SELECT ef FROM ExamFile ef WHERE ef.objectId = ?1 AND ef.type = 2")
    List<ExamFile> findAllByUserExamId(Long examId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ExamFile u where u.examFileId in (:idLists)")
    void deleteAllByListId(@Param("idLists") List<Long> idLists);

}
