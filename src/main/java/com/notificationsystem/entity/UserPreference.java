package com.notificationsystem.entity;

import jakarta.persistence.*;

@Entity
public class UserPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private boolean emailEnabled;
    private boolean pushEnabled;
    private boolean inAppEnabled;
    private boolean smsEnabled;
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
}
