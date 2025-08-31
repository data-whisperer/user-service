package com.data.whisperer.users.service;

import com.data.whisperer.users.dto.UserDetailsDTO;
import com.data.whisperer.users.model.UserDetails;
import java.util.List;
import java.util.UUID;

public interface UserDetailsService {
    List<UserDetails> getAllUsers();
    UserDetails getUserById(UUID id);
    UserDetails createUser(UserDetailsDTO userDTO);
    UserDetails updateUser(UUID id, UserDetailsDTO userDTO);
    void deleteUser(UUID id);
} 