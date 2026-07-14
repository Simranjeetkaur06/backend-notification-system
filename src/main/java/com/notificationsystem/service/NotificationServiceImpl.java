package com.notificationsystem.service;

import com.notificationsystem.dto.NotificationRequest;
import com.notificationsystem.entity.NotificationHistory;
import com.notificationsystem.entity.User;
import com.notificationsystem.entity.UserPreference;
import com.notificationsystem.repository.NotificationHistoryRepository;
import com.notificationsystem.repository.UserPreferenceRepository;
import com.notificationsystem.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final UserRepository userRepository;
    private final UserPreferenceRepository userPreferenceRepository;
    private final NotificationHistoryRepository notificationHistoryRepository;

    public NotificationServiceImpl(UserRepository userRepository,
                                   UserPreferenceRepository userPreferenceRepository,
                                   NotificationHistoryRepository notificationHistoryRepository) {
        this.userRepository = userRepository;
        this.userPreferenceRepository = userPreferenceRepository;
        this.notificationHistoryRepository = notificationHistoryRepository;
    }

    @Override
    public void sendNotification(NotificationRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserPreference preference = userPreferenceRepository.findByUser(user);

        for (String channel : request.getChannels()) {
            boolean sent = false;
            String status = "FAILED";

            try {
                switch (channel.toUpperCase()) {
                    case "EMAIL":
                        if (preference.isEmailEnabled()) {
                            System.out.println("EMAIL sent to: " + user.getEmail());
                            sent = true;
                        }
                        break;
                    case "SMS":
                        if (preference.isSmsEnabled()) {
                            System.out.println("SMS sent to: " + user.getPhone());
                            sent = true;
                        }
                        break;
                    case "PUSH":
                        if (preference.isPushEnabled()) {
                            System.out.println("PUSH sent to user: " + user.getId());
                            sent = true;
                        }
                        break;
                    case "IN_APP":
                        if (preference.isInAppEnabled()) {
                            System.out.println("IN_APP sent to user: " + user.getId());
                            sent = true;
                        }
                        break;
                }
                status = sent ? "SUCCESS" : "SKIPPED";
            } catch (Exception e) {
                status = "FAILED";
            }

            NotificationHistory history = new NotificationHistory();
            history.setUser(user);
            history.setChannel(channel);
            history.setStatus(status);
            history.setTitle(request.getTitle());
            history.setBody(request.getBody());
            history.setCreatedAt(LocalDateTime.now());
            notificationHistoryRepository.save(history);
        }
    }
}