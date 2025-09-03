package com.sellanding.poker_vatch_optimzer.domain;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "game_session")

public class GameSession {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playe1_id")
    private User player1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player2_id")
    private User player2;

    @Column(nullable = false)
    private String status; // "waiting", "playing", "finished"

    private LocalDateTime startedAt;


}
