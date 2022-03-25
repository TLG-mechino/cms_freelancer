package vn.compedia.website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.website.model.Notification;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto extends Notification {
    private Integer stt;
}
