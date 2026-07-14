package com.notificationsystem.repository;

import com.notificationsystem.entity.User;
import com.notificationsystem.entity.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {
    UserPreference findByUser(User user);
}
