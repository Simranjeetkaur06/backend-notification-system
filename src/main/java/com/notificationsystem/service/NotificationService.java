package com.notificationsystem.service;

import com.notificationsystem.dto.NotificationRequest;

public interface NotificationService {
    void sendNotification(NotificationRequest request);
}
