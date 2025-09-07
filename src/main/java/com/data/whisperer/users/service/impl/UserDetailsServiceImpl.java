package com.data.whisperer.users.service.impl;

import com.data.whisperer.users.dto.UserDetailsDTO;
import com.data.whisperer.users.model.UserDetails;
import com.data.whisperer.users.repository.UserDetailsRepository;
import com.data.whisperer.users.service.UserDetailsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserDetailsDTO> getAllUsers() {
        return userDetailsRepository.findAll()
                .stream()
                .map(userDetails ->
                        modelMapper.map(userDetails, UserDetailsDTO.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailsDTO getUserById(UUID id) {
        var user = userDetailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return modelMapper.map(user,UserDetailsDTO.class);
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
        user.setCreatedOn(LocalDateTime.now());

        return userDetailsRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails updateUser(UUID id, UserDetailsDTO userDTO) {
        var existingUserOptional = userDetailsRepository.findById(id);
        if(existingUserOptional.isEmpty()){
            throw new EntityNotFoundException("User not found");
        }
        var existingUser = existingUserOptional.get();
        existingUser.setFullName(userDTO.getFullName());
        existingUser.setEmail(userDTO.getEmail());

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