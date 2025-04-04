package com.data.whisperer.userservice.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class UserDetailsDTO {
    private String fullName;
    private String email;
    private String password;
    private UUID picture;
    private UUID accessRoleId;
} 