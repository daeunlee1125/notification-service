package kr.co.daeun.notification;

public class ChannelStatsRespDTO {
    private String channelType;
    private int totalCnt;
    private int pendingCnt;
    private int inProgressCnt;
    private int sentCnt;
    private int retryScheduledCnt;
    private int deadLetterCnt;
}
