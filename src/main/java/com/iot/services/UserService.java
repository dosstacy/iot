package com.iot.services;

import com.iot.domain.entity.User;
import com.iot.domain.exceptions.InvalidUserException;
import com.iot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new InvalidUserException("Cannot get user with id " + id));
    }

    public User save(User entity) {
        isUserExist(entity.getUsername());
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return userRepository.save(entity);
    }

    private void isUserExist(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new InvalidUserException("User with name " + username + " already exists");
        }
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new InvalidUserException("Cannot delete user with id " + id);
        }
        userRepository.deleteById(id);
    }

    public boolean authenticate(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidUserException("User not found"));

        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}
