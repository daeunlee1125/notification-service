package kr.co.daeun.notification.dto;

import kr.co.daeun.notification.type.ChannelType;
import lombok.Data;

@Data
public class ChannelStatsRespDTO {
    private ChannelType channelType;
    private int totalCnt;
    private int pendingCnt;
    private int inProgressCnt;
    private int sentCnt;
    private int retryScheduledCnt;
    private int deadLetterCnt;
}
