package kr.co.daeun.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationStatsRespDTO {
    private LocalDateTime from;
    private LocalDateTime to;
    private int totalCnt;
    private int pendingCnt;
    private int inProgressCnt;
    private int sentCnt;
    private int retryScheduledCnt;
    private int deadLetterCnt;
    private List<ChannelStatsRespDTO> channelStats;
}
