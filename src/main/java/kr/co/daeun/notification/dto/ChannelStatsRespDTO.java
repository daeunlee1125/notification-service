package kr.co.daeun.notification.dto;

import kr.co.daeun.notification.type.ChannelType;

public class ChannelStatsRespDTO {
    private ChannelType channelType;
    private int totalCnt;
    private int pendingCnt;
    private int inProgressCnt;
    private int sentCnt;
    private int retryScheduledCnt;
    private int deadLetterCnt;
}
