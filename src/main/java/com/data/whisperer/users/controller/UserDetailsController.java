package com.data.whisperer.users.controller;

import com.data.whisperer.users.dto.UserDetailsDTO;
import com.data.whisperer.users.model.UserDetails;
import com.data.whisperer.users.service.UserDetailsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserDetailsController {
    
    private final UserDetailsService userDetailsService;
    
    @GetMapping
    public ResponseEntity<List<UserDetails>> getAllUsers() {
        return ResponseEntity.ok(userDetailsService.getAllUsers());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDetails> getUserById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(userDetailsService.getUserById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDetailsDTO userDTO) {
        try {
            UserDetails createdUser = userDetailsService.createUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody UserDetailsDTO userDTO) {
        try {
            UserDetails updatedUser = userDetailsService.updateUser(id, userDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        try {
            userDetailsService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 