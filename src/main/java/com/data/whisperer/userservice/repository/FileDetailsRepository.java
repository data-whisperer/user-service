package com.data.whisperer.userservice.repository;

import com.data.whisperer.userservice.model.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface FileDetailsRepository extends JpaRepository<FileDetails, UUID> {
} 