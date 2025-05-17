package com.data.whisperer.userservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name = "file_details")
public class FileDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "mime_type")
    private String mimeType;
    
    @Column(nullable = false)
    private String location;
} 