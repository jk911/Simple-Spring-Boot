package com.example.demo.repositories;

import com.example.demo.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface BranchRepository extends JpaRepository<Branch, String> {
    @Query(value = "SELECT TOP 1 * FROM branches b ORDER BY b.id ASC", nativeQuery = true)
    Branch findFirstBranchId();
}

