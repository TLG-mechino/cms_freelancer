package vn.compedia.website.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.compedia.website.model.NotificationRef;

import java.util.List;

@Repository
public interface NotificationRefRepository extends CrudRepository<NotificationRef, Long>, NotificationRefRepositoryCustom {

    @Query(value = "select * from NOTIFICATION_REF noti_ref where noti_ref.USERNAME=:userName", nativeQuery = true)
    List<NotificationRef> getAllByUserName(@Param("userName") String userName);

    @Query(value = "select * from NOTIFICATION_REF noti_ref where noti_ref.NOTIFICATION_REF_ID =:id", nativeQuery = true)
    NotificationRef getNotificationRefById(@Param("id") Long id);

}
