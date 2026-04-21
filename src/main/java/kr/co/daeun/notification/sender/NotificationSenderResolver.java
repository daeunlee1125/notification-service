package kr.co.daeun.notification.sender;

import kr.co.daeun.notification.type.ChannelType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationSenderResolver {
    private final List<NotificationSender> senders;

    public NotificationSenderResolver(List<NotificationSender> senders) {
        this.senders = senders;
    }

    public NotificationSender getSender(ChannelType channelType) {
        return senders.stream()
                .filter(sender -> sender.getChannelType() == channelType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported channelType: " + channelType));
    }
}