package com.sellanding.poker_vatch_optimzer.domain;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "game_history", indexes = {
    @Index(name = "idx_user_id_played_at", columnList = "user_id, playedAt"),
})
public class GameHistory {
    
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
