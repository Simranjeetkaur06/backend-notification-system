package com.notificationsystem.repository;

import com.notificationsystem.entity.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationHistoryRepository extends JpaRepository<UserPreference, Long> {
}
