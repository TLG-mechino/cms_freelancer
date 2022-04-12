package vn.compedia.website.repository;

import org.springframework.data.repository.CrudRepository;
import vn.compedia.website.model.CustomerTalk;

public interface CustomerTalkRepository extends CrudRepository<CustomerTalk, Long>, CustomerTalkRepositoryCustom {
}
