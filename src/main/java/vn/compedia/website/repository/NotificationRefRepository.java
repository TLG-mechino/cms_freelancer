package vn.compedia.website.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import vn.compedia.website.model.NotificationRef;
import java.util.*;
public interface NotificationRefRepository extends CrudRepository<NotificationRef,Long> ,NotificationRefRepositoryCustom{

    @Query(value = "select * from notification_ref noti_ref where noti_ref.USERNAME=:userName",nativeQuery = true)
    List<NotificationRef>getAllByUserName(@Param("userName") String userName);

}
