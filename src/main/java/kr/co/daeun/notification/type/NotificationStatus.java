package kr.co.daeun.notification.type;

public enum NotificationStatus {
    PENDING,
    IN_PROGRESS,
    SENT,
    RETRY_SCHEDULED,
    DEAD_LETTER;

    public boolean isFinalStatus() {
        return this == SENT || this == DEAD_LETTER;
    }
}
