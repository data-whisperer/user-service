package com.data.whisperer.users.repository;

import com.data.whisperer.users.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, UUID> {
    boolean existsByEmail(String email);
} 