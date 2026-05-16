package com.svalero.komorebiApi.service;

import com.svalero.komorebiApi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public long getUserCount() {
        return userRepository.count();
    }
}