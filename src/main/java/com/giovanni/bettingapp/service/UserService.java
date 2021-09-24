package com.giovanni.bettingapp.service;

import com.giovanni.bettingapp.dto.BetDto;
import com.giovanni.bettingapp.dto.UserDto;
import com.giovanni.bettingapp.exception.ConflictException;
import com.giovanni.bettingapp.exception.ResourceNotFoundException;
import com.giovanni.bettingapp.mapper.BetMapper;
import com.giovanni.bettingapp.mapper.UserMapper;
import com.giovanni.bettingapp.model.Role;
import com.giovanni.bettingapp.model.User;
import com.giovanni.bettingapp.repository.RoleRepository;
import com.giovanni.bettingapp.repository.UserRepository;
import com.giovanni.bettingapp.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

import static com.giovanni.bettingapp.util.ConstantUtil.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final BetMapper betMapper;
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

    public UserDto getCurrentUser(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(USER_WITH_USERNAME + username + NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    public UserDto saveUser(User user) {
        validateUsername(user.getUsername());
        validatePassword(user.getPassword());
        user.giveDefaultRole();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toUserDto(userRepository.save(user));
    }

    public List<BetDto> getBetsByUser(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_WITH_ID + id + NOT_FOUND));
        return betMapper.toBetDtoList(user.getBets());
    }

    public UserDto updateUser(int id, User newUser) {
        User oldUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_WITH_ID + id + NOT_FOUND));

        if (!oldUser.getUsername().equals(newUser.getUsername())) {
            validateUsername(newUser.getUsername());
        } else if (!oldUser.getEmail().equals(newUser.getEmail())) {
            validatePassword(newUser.getPassword());
        }

        oldUser.setUsername(newUser.getUsername());
        oldUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        oldUser.setEmail(newUser.getEmail());
        return userMapper.toUserDto(userRepository.save(oldUser));
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

    private void validateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new ConflictException(USER_WITH_USERNAME + username + EXISTS);
        }
    }

    private void validatePassword(String password) {
        if (userRepository.existsByEmail(password)) {
            throw new ConflictException(USER_WITH_EMAIL + password + EXISTS);
        }
    }
}
