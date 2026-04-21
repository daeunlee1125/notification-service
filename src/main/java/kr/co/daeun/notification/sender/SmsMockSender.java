package kr.co.daeun.notification.sender;

import kr.co.daeun.notification.dto.NotificationDTO;
import kr.co.daeun.notification.type.ChannelType;
import kr.co.daeun.notification.type.NotificationSendResult;
import org.springframework.stereotype.Component;

@Component
public class SmsMockSender implements NotificationSender {

    @Override
    public ChannelType getChannelType() {
        return ChannelType.SMS;
    }

    @Override
    public NotificationSendResult send(NotificationDTO notification) {
        if (notification.getBody() != null && notification.getBody().contains("fail")) {
            return NotificationSendResult.fail("SMS_FAIL", "Sms mock send failed.");
        }
        return NotificationSendResult.success();
    }
}