package com.sellanding.poker_vatch_optimzer.service;

import com.sellanding.poker_vatch_optimzer.domain.User;
import com.sellanding.poker_vatch_optimzer.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User registerUser(String nickname) {
        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new IllegalArgumentException("Nickname is already taken");
        }

        User newUser = new User();

        return userRepository.save(newUser);
    }

    public User getUserInfo(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    
}
