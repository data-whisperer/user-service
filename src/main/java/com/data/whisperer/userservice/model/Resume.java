package com.data.whisperer.userservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "resume")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    
    @Column(name = "resume_file", nullable = false)
    private UUID resumeFile;
    
    @Column(name = "upload_date")
    private LocalDateTime uploadDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserDetails user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_file", insertable = false, updatable = false)
    private FileDetails fileDetails;
} 