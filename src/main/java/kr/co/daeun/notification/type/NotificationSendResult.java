package kr.co.daeun.notification.type;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationSendResult {
    private boolean success;
    private String errorCode;
    private String errorMessage;

    public static NotificationSendResult success() {
        return NotificationSendResult.builder()
                .success(true)
                .build();
    }

    public static NotificationSendResult fail(String errorCode, String errorMessage) {
        return NotificationSendResult.builder()
                .success(false)
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .build();
    }
}