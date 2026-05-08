package kr.co.daeun.notification.sender;

import com.solapi.sdk.SolapiClient;
import com.solapi.sdk.message.exception.SolapiMessageNotReceivedException;
import com.solapi.sdk.message.model.Message;
import com.solapi.sdk.message.service.DefaultMessageService;
import kr.co.daeun.notification.dto.NotificationDTO;
import kr.co.daeun.notification.type.ChannelType;
import kr.co.daeun.notification.type.NotificationSendResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SmsSender implements NotificationSender {

    private final DefaultMessageService messageService;

    @Value("${spring.solapi.from}")
    private String from;

    public SmsSender(
            @Value("${spring.solapi.api-key}") String apiKey,
            @Value("${spring.solapi.api-secret}") String apiSecret
    ) {
        this.messageService = SolapiClient.INSTANCE.createInstance(apiKey, apiSecret);
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.SMS;
    }

    @Override
    public NotificationSendResult send(NotificationDTO notification) {
        try {
            Message message = new Message();

            message.setFrom(from);
            message.setTo(notification.getRecipientValue());
            message.setText(notification.getBody());

            messageService.send(message);

            return NotificationSendResult.success();

        } catch (SolapiMessageNotReceivedException e) {
            return NotificationSendResult.fail(
                    "SOLAPI_NOT_RECEIVED",
                    e.getMessage()
            );
        } catch (Exception e) {
            return NotificationSendResult.fail(
                    "SOLAPI_SEND_ERROR",
                    e.getMessage()
            );
        }
    }
}