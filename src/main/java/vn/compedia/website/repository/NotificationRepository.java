package vn.compedia.website.repository;

import org.springframework.data.repository.CrudRepository;
import vn.compedia.website.model.Notification;

public interface NotificationRepository extends CrudRepository<Notification, Long>, NotificationRepositoryCustom {
}
