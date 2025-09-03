package com.sellanding.poker_vatch_optimzer.repository;

import com.sellanding.poker_vatch_optimzer.domain.GameHistory;
import com.sellanding.poker_vatch_optimzer.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface GameHistoryRepository extends JpaRepository<GameHistory, Long> {
    
    // 특정 기간 동안의 유저 게임 기록 조회
    List<GameHistory> findByUserAndPlayedAtBetween(User user, LocalDateTime start, LocalDateTime end);

    // 등급 산정을 위해 최근 7일간의 모든 유저별 점수 합계를 계산(JPQL)
   @Query("SELECT h.user, SUM(h.score) as totalScore FROM GameHistory h WHERE h.playedAt >= :sevenDaysAgo GROUP BY h.user")
   List<Object[]> findUserScoreForLastSevenDays(@Param("sevenDaysAgo") LocalDateTime sevenDaysAgo);
}
