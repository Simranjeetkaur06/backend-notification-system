package com.notificationsystem.dto;

import lombok.Data;

import java.util.List;
@Data
public class NotificationRequest {
    private Long userId;
    private String title;
    private String body;
    private List<String> channels;
}
