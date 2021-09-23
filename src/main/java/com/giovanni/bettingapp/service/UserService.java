package com.giovanni.bettingapp.service;

import com.giovanni.bettingapp.dto.UserDto;
import com.giovanni.bettingapp.exception.ConflictException;
import com.giovanni.bettingapp.exception.ResourceNotFoundException;
import com.giovanni.bettingapp.mapper.UserMapper;
import com.giovanni.bettingapp.model.Role;
import com.giovanni.bettingapp.model.User;
import com.giovanni.bettingapp.repository.RoleRepository;
import com.giovanni.bettingapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.giovanni.bettingapp.util.ConstantUtil.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toUserDtoList(users);
    }

    public UserDto getUser(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_WITH_ID + id + NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    public UserDto saveUser(User user) {
        validateUser(user);
        user.giveDefaultRole();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toUserDto(userRepository.save(user));
    }

    public UserDto updateUser(int id, User newUser) {
        validateUser(newUser);
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setPassword(newUser.getPassword());
                    user.setEmail(newUser.getEmail());
                    return userMapper.toUserDto(userRepository.save(user));
                })
                .orElseThrow(() -> new ResourceNotFoundException(USER_WITH_ID + id + NOT_FOUND));
    }

    public void deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(USER_WITH_ID + id + NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    public void giveRole(String username, String roleName) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(USER_WITH_USERNAME + username + NOT_FOUND));
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException(ROLE_WITH_NAME + roleName + NOT_FOUND));

        user.getRoles().add(role);
        userRepository.save(user);
    }

    public void removeRole(String username, String roleName) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(USER_WITH_USERNAME + username + NOT_FOUND));
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException(ROLE_WITH_NAME + roleName + NOT_FOUND));

        if (!user.getRoles().contains(role)) {
            throw new ResourceNotFoundException(ROLE_WITH_NAME + roleName + NOT_FOUND);
        }

        user.getRoles().remove(role);
        userRepository.save(user);
    }

    private void validateUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ConflictException(USER_WITH_USERNAME + user.getUsername() + EXISTS);
        } else if (userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException(USER_WITH_EMAIL + user.getEmail() + EXISTS);
        }
    }
}
