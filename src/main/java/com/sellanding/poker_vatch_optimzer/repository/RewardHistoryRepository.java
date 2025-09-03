package com.sellanding.poker_vatch_optimzer.repository;

import com.sellanding.poker_vatch_optimzer.domain.RewardHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardHistoryRepository extends JpaRepository<RewardHistory, Long>{
    // 필요시 특정 유저의 보상 내역 조회 등의 메소드 추가 가능 
}
