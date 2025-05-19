package com.data.whisperer.userservice.service.impl;

import com.data.whisperer.userservice.dto.UserDetailsDTO;
import com.data.whisperer.userservice.model.UserDetails;
import com.data.whisperer.userservice.repository.UserDetailsRepository;
import com.data.whisperer.userservice.service.UserDetailsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final UserDetailsRepository userDetailsRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<UserDetails> getAllUsers() {
        return userDetailsRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails getUserById(UUID id) {
        return userDetailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }
    
    @Override
    @Transactional
    public UserDetails createUser(UserDetailsDTO userDTO) {
        if (userDetailsRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        UserDetails user = new UserDetails();
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword()); // In real application, password should be encrypted
        user.setCreatedOn(LocalDateTime.now());
        user.setPicture(userDTO.getPicture());
        user.setAccessRoleId(userDTO.getAccessRoleId());
        
        return userDetailsRepository.save(user);
    }
    
    @Override
    @Transactional
    public UserDetails updateUser(UUID id, UserDetailsDTO userDTO) {
        UserDetails existingUser = getUserById(id);
        
        existingUser.setFullName(userDTO.getFullName());
        existingUser.setEmail(userDTO.getEmail());
        if (userDTO.getPassword() != null) {
            existingUser.setPassword(userDTO.getPassword()); // In real application, password should be encrypted
        }
        existingUser.setPicture(userDTO.getPicture());
        existingUser.setAccessRoleId(userDTO.getAccessRoleId());
        
        return userDetailsRepository.save(existingUser);
    }
    
    @Override
    @Transactional
    public void deleteUser(UUID id) {
        if (!userDetailsRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userDetailsRepository.deleteById(id);
    }
} 