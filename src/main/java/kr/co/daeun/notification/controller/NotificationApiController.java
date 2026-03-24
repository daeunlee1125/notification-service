package kr.co.daeun.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
@RequiredArgsConstructor
public class NotificationApiController {

    @PostMapping("/api/notifications")
    public ResponseEntity<CreateNotificationRespDTO> sendNotification(@RequestBody CreateNotificationReqDTO reqDTO) {
        return ResponseEntity.ok().body(new CreateNotificationRespDTO());
    }

}
