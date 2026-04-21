package kr.co.daeun.notification.sender;

import kr.co.daeun.notification.dto.NotificationDTO;
import kr.co.daeun.notification.type.ChannelType;
import kr.co.daeun.notification.type.NotificationSendResult;
import org.springframework.stereotype.Component;

@Component
public class EmailMockSender implements NotificationSender {

    @Override
    public ChannelType getChannelType() {
        return ChannelType.EMAIL;
    }

    @Override
    public NotificationSendResult send(NotificationDTO notification) {
        if (notification.getBody() != null && notification.getBody().contains("fail")) {
            return NotificationSendResult.fail("EMAIL_FAIL", "Email mock send failed.");
        }
        return NotificationSendResult.success();
    }
}