package com.notificationsystem.dto;

import java.util.List;

public class NotificationRequest {
    private Long userId;
    private String title;
    private String body;
    private List<String> channels;
}
