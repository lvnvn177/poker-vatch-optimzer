package com.sellanding.poker_vatch_optimzer.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "chip_transaction") 
public class ChipTransaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Long changeAmount;

    @Column(nullable = false)
    private String type; // "game_win", "reward", "purchase"

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
