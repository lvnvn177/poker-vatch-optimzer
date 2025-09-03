package com.sellanding.poker_vatch_optimzer.service;

import com.sellanding.poker_vatch_optimzer.domain.ChipTransaction;
import com.sellanding.poker_vatch_optimzer.domain.User;
import com.sellanding.poker_vatch_optimzer.repository.ChipTransactionRepository;
import com.sellanding.poker_vatch_optimzer.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChipService {
    
    private final UserRepository userRepository;
    private final ChipTransactionRepository chipTransactionRepository;

    @Transactional
    public void processChipTransaction(Long userId, long amount, String type) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found."));

        userRepository.save(user);

        ChipTransaction transaction = new ChipTransaction();

        chipTransactionRepository.save(transaction);
    }
}
