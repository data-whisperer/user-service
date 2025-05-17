package com.data.whisperer.userservice.controller;

import com.data.whisperer.userservice.model.Resume;
import com.data.whisperer.userservice.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {
    
    private final ResumeService resumeService;
    
    @PostMapping("/upload/{userId}")
    public ResponseEntity<Resume> uploadResume(
            @PathVariable UUID userId,
            @RequestParam("file") MultipartFile file) {
        try {
            Resume resume = resumeService.uploadResume(userId, file);
            return ResponseEntity.ok(resume);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Resume>> getUserResumes(@PathVariable UUID userId) {
        return ResponseEntity.ok(resumeService.getUserResumes(userId));
    }
    
    @GetMapping("/{resumeId}")
    public ResponseEntity<Resource> downloadResume(@PathVariable UUID resumeId) {
        try {
            Resume resume = resumeService.getResume(resumeId);
            Path filePath = Paths.get(resume.getFileDetails().getLocation());
            Resource resource = new UrlResource(filePath.toUri());
            
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, 
                            "attachment; filename=\"" + resume.getFileDetails().getName() + "\"")
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @DeleteMapping("/{resumeId}")
    public ResponseEntity<Void> deleteResume(@PathVariable UUID resumeId) {
        try {
            resumeService.deleteResume(resumeId);
            return ResponseEntity.noContent().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
} 