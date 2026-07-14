package com.notificationsystem.controller;

import com.notificationsystem.dto.NotificationRequest;
import com.notificationsystem.service.NotificationService;
import com.notificationsystem.service.NotificationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    public NotificationController( NotificationService notificationService){
        this.notificationService = notificationService;
    }
    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request){
        notificationService.sendNotification(request);
        return ResponseEntity.ok("Notification sent successfully");
    }
}
