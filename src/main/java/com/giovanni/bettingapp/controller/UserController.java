package com.giovanni.bettingapp.controller;

import com.giovanni.bettingapp.dto.UserDto;
import com.giovanni.bettingapp.model.User;
import com.giovanni.bettingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = userService.getUsers();
        return new ResponseEntity<>(users, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable int id) {
        UserDto user = userService.getUser(id);
        return new ResponseEntity<>(user, OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody User user) {
        UserDto newUser = userService.saveUser(user);
        return new ResponseEntity<>(newUser, OK);
    }

    @PostMapping("/{username}/role/{roleName}")
    public ResponseEntity<Void> giveRole( @PathVariable String username, @PathVariable String roleName) {
        userService.giveRole(username, roleName);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable int id, @Valid @RequestBody User user) {
        UserDto updatedUser = userService.updateUser(id, user);
        return new ResponseEntity<>(updatedUser, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @DeleteMapping("/{username}/role/{roleName}")
    public ResponseEntity<Void> removeRole( @PathVariable String username, @PathVariable String roleName) {
        userService.removeRole(username, roleName);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
