package com.giovanni.bettingapp.controller;

import com.giovanni.bettingapp.dto.BetDto;
import com.giovanni.bettingapp.dto.UserDto;
import com.giovanni.bettingapp.model.User;
import com.giovanni.bettingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = userService.getUsers();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable int id) {
        UserDto user = userService.getUser(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/me")
    ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        UserDto user = userService.getCurrentUser(principal);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{id}/bets")
    public ResponseEntity<List<BetDto>> getBetsByUser(@PathVariable int id) {
        List<BetDto> bets = userService.getBetsByUser(id);
        return ResponseEntity.ok().body(bets);
    }

    @PostMapping("/{username}/role/{roleName}")
    public ResponseEntity<Void> giveRole( @PathVariable String username, @PathVariable String roleName) {
        userService.giveRole(username, roleName);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable int id, @Valid @RequestBody User user) {
        UserDto updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok().body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{username}/role/{roleName}")
    public ResponseEntity<Void> removeRole( @PathVariable String username, @PathVariable String roleName) {
        userService.removeRole(username, roleName);
        return ResponseEntity.noContent().build();
    }
}
