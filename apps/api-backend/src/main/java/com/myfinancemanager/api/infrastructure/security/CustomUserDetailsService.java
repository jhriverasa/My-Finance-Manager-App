package com.myfinancemanager.api.infrastructure.security;

import com.myfinancemanager.api.domain.model.User;
import com.myfinancemanager.api.domain.port.out.UserRepositoryPort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

/**
 * Custom UserDetailsService implementation that loads user by ID (from JWT).
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepositoryPort userRepository;

    public CustomUserDetailsService(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        try {
            Long id = Long.parseLong(userId);
            Optional<User> user = userRepository.findById(id);
            
            if (user.isEmpty()) {
                throw new UsernameNotFoundException("User not found with id: " + id);
            }

            return new org.springframework.security.core.userdetails.User(
                user.get().getEmail(),
                user.get().getPasswordHash(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid user ID: " + userId);
        }
    }
}