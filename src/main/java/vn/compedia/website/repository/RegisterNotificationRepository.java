package vn.compedia.website.repository;

import org.springframework.data.repository.CrudRepository;
import vn.compedia.website.model.Account;
import vn.compedia.website.model.RegisterNotification;

public interface RegisterNotificationRepository  extends CrudRepository<RegisterNotification, Long> {
}
