package com.sellanding.poker_vatch_optimzer.repository;


import com.sellanding.poker_vatch_optimzer.domain.ChipTransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChipTransactionRepository extends JpaRepository<ChipTransaction, Long> {
    // 필요시 특정 유저의 칩 거래 내역 조회 등의 메소드 추가 가능 
}
