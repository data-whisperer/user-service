package com.data.whisperer.users.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserDetailsDTO {
    private UUID id;
    private String fullName;
    private String email;
    private LocalDateTime createdOn;
} 