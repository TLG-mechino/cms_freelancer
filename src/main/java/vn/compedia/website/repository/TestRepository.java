package vn.compedia.website.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.compedia.website.model.Exam;

import java.util.List;

@Repository
public interface TestRepository extends CrudRepository<Exam, Long>, TestRepositoryCustom {

    @Query("select e from Exam e where e.code = :code and e.examId <> :examId")
    List<Exam> findByCodeExists(@Param("code") String code, @Param("examId") Long examId);

}
