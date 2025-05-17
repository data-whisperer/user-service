package com.data.whisperer.userservice.service;

import com.data.whisperer.userservice.dto.ResumeDTO;
import com.data.whisperer.userservice.model.FileDetails;
import com.data.whisperer.userservice.model.Resume;
import com.data.whisperer.userservice.repository.FileDetailsRepository;
import com.data.whisperer.userservice.repository.ResumeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResumeService {
    
    private final ResumeRepository resumeRepository;
    private final FileDetailsRepository fileDetailsRepository;
    private final String UPLOAD_DIR = "uploads/resumes/";
    
    @Transactional
    public Resume uploadResume(UUID userId, MultipartFile file) throws IOException {
        if (!file.getContentType().equals("application/pdf")) {
            throw new IllegalArgumentException("Only PDF files are allowed");
        }
        
        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        // Save file details
        FileDetails fileDetails = new FileDetails();
        fileDetails.setName(file.getOriginalFilename());
        fileDetails.setMimeType(file.getContentType());
        fileDetails.setLocation(UPLOAD_DIR + UUID.randomUUID() + ".pdf");
        fileDetails = fileDetailsRepository.save(fileDetails);
        
        // Save file to disk
        Files.copy(file.getInputStream(), Paths.get(fileDetails.getLocation()));
        
        // Create resume record
        Resume resume = new Resume();
        resume.setUserId(userId);
        resume.setResumeFile(fileDetails.getId());
        resume.setUploadDate(LocalDateTime.now());
        
        return resumeRepository.save(resume);
    }
    
    @Transactional(readOnly = true)
    public List<Resume> getUserResumes(UUID userId) {
        return resumeRepository.findByUserId(userId);
    }
    
    @Transactional(readOnly = true)
    public Resume getResume(UUID resumeId) {
        return resumeRepository.findById(resumeId)
                .orElseThrow(() -> new EntityNotFoundException("Resume not found"));
    }
    
    @Transactional
    public void deleteResume(UUID resumeId) throws IOException {
        Resume resume = getResume(resumeId);
        FileDetails fileDetails = fileDetailsRepository.findById(resume.getResumeFile())
                .orElseThrow(() -> new EntityNotFoundException("File details not found"));
        
        // Delete file from disk
        Files.deleteIfExists(Paths.get(fileDetails.getLocation()));
        
        // Delete database records
        resumeRepository.delete(resume);
        fileDetailsRepository.delete(fileDetails);
    }
} 