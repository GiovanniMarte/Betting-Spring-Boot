package com.giovanni.bettingapp.service;

import com.giovanni.bettingapp.exception.ConflictException;
import com.giovanni.bettingapp.exception.ResourceNotFoundException;
import com.giovanni.bettingapp.model.User;
import com.giovanni.bettingapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.giovanni.bettingapp.util.AppConstant.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_WITH + id + NOT_FOUND));
    }

    public User addUser(User user) {
        if (userRepository.existsById(user.getId())) {
            throw new ConflictException(USER_WITH + user.getId() + EXISTS);
        }
        return userRepository.save(user);
    }

    public User updateUser(int id, User newUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setPassword(newUser.getPassword());
                    user.setEmail(newUser.getEmail());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new ResourceNotFoundException(USER_WITH + id + NOT_FOUND));
    }

    public void deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(USER_WITH + id + NOT_FOUND);
        }
        userRepository.deleteById(id);
    }
}
