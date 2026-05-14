package com.svalero.komorebiApi.service;

import com.svalero.komorebiApi.domain.User;
import com.svalero.komorebiApi.domain.dto.AuthResponseDto;
import com.svalero.komorebiApi.domain.dto.LoginDto;
import com.svalero.komorebiApi.domain.dto.RegisterDto;
import com.svalero.komorebiApi.repository.UserRepository;
import com.svalero.komorebiApi.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthResponseDto register(RegisterDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + dto.getUsername());
        }

        User.Role role = User.Role.ROLE_USER;
        if (dto.getRole() != null && dto.getRole().equalsIgnoreCase("ADMIN")) {
            role = User.Role.ROLE_ADMIN;
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(role);
        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtService.generateToken(userDetails);

        return new AuthResponseDto(token, user.getUsername(), user.getRole().name());
    }

    public AuthResponseDto login(LoginDto dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
        String token = jwtService.generateToken(userDetails);
        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow();

        return new AuthResponseDto(token, user.getUsername(), user.getRole().name());
    }
}