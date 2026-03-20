package kr.co.daeun.notification;

import lombok.Data;

import java.util.List;

@Data
public class NotificationStatsRespDTO {
    private int totalCnt;
    private int pendingCnt;
    private int inProgressCnt;
    private int sentCnt;
    private int retryScheduledCnt;
    private int deadLetterCnt;
    private List<ChannelStatsRespDTO> channelStats;
}
