package vn.compedia.website.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.compedia.website.model.UserExam;

@Repository
public interface TestEvaluateRepository extends CrudRepository<UserExam, Long>, TestEvaluateRepositoryCustom {
}
