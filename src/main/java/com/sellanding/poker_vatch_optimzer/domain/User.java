package com.sellanding.poker_vatch_optimzer.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "users") 
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private Long chips;

    @Column(nullable = false)
    private String rank; // Bronze, Silver

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Version
    private Long version; // For Optimistic Lock

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
