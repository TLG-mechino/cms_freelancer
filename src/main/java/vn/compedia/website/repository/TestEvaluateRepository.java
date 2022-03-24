package vn.compedia.website.repository;

import org.springframework.data.repository.CrudRepository;
import vn.compedia.website.model.UserExam;

public interface TestEvaluateRepository extends CrudRepository<UserExam, Long>, TestEvaluateRepositoryCustom {
}
