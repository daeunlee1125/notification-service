package kr.co.daeun.notification.dto;

import lombok.Data;

import java.util.List;

@Data
public class NotificationListRespDTO {
    private List<NotificationListItemDTO> items;
    private int page;
    private int size;
    private int totalCnt;
}
