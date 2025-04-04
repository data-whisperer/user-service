package com.data.whisperer.userservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "user_details")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "full_name", nullable = false)
    private String fullName;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;
    
    @Column(name = "picture")
    private UUID picture;
    
    @Column(name = "access_role_id", nullable = false)
    private UUID accessRoleId;
} 