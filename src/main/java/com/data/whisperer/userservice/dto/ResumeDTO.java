package com.data.whisperer.userservice.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ResumeDTO {
    private UUID userId;
    private String fileName;
    private String fileContent; // Base64 encoded PDF content
} 