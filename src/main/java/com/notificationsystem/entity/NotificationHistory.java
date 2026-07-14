package com.notificationsystem.entity;

import jakarta.persistence.*;
import org.apache.catalina.User;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class NotificationHistory {

    @ManyToOne
    @JoinColumn
    private User user;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String body;
    private String channel;
    private String status;
    private LocalDateTime createdAt;
}
