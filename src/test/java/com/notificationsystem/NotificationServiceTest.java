package com.notificationsystem;

import com.notificationsystem.dto.NotificationRequest;
import com.notificationsystem.entity.NotificationHistory;
import com.notificationsystem.entity.User;
import com.notificationsystem.entity.UserPreference;
import com.notificationsystem.repository.NotificationHistoryRepository;
import com.notificationsystem.repository.UserPreferenceRepository;
import com.notificationsystem.repository.UserRepository;
import com.notificationsystem.service.NotificationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserPreferenceRepository userPreferenceRepository;

    @Mock
    private NotificationHistoryRepository notificationHistoryRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    void testEmailSentWhenEnabled() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setPhone("9999999999");

        UserPreference pref = new UserPreference();
        pref.setEmailEnabled(true);
        pref.setSmsEnabled(false);
        pref.setPushEnabled(false);
        pref.setInAppEnabled(false);

        NotificationRequest request = new NotificationRequest();
        request.setUserId(1L);
        request.setTitle("Test");
        request.setBody("Hello");
        request.setChannels(Arrays.asList("EMAIL"));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userPreferenceRepository.findByUser(user)).thenReturn(pref);
        when(notificationHistoryRepository.save(any(NotificationHistory.class)))
                .thenReturn(new NotificationHistory());

        notificationService.sendNotification(request);

        verify(notificationHistoryRepository, times(1))
                .save(any(NotificationHistory.class));
    }

    @Test
    void testSmsSkippedWhenDisabled() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setPhone("9999999999");

        UserPreference pref = new UserPreference();
        pref.setSmsEnabled(false);
        pref.setEmailEnabled(false);
        pref.setPushEnabled(false);
        pref.setInAppEnabled(false);

        NotificationRequest request = new NotificationRequest();
        request.setUserId(1L);
        request.setTitle("Test");
        request.setBody("Hello");
        request.setChannels(Arrays.asList("SMS"));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userPreferenceRepository.findByUser(user)).thenReturn(pref);
        when(notificationHistoryRepository.save(any(NotificationHistory.class)))
                .thenReturn(new NotificationHistory());

        notificationService.sendNotification(request);

        verify(notificationHistoryRepository, times(1))
                .save(any(NotificationHistory.class));
    }

    @Test
    void testUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        NotificationRequest request = new NotificationRequest();
        request.setUserId(99L);
        request.setTitle("Test");
        request.setBody("Hello");
        request.setChannels(Arrays.asList("EMAIL"));

        try {
            notificationService.sendNotification(request);
        } catch (RuntimeException e) {
            assert e.getMessage().equals("User not found");
        }
    }
}