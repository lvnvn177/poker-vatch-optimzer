package com.sellanding.poker_vatch_optimzer.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "reward_histoty")
public class RewardHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String rewardType; // "rank_up", "rank_keep"

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private LocalDateTime givenAt;

    @PrePersist
    protected void onGive() {
        givenAt = LocalDateTime.now();
    }
}
