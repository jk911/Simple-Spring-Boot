package com.example.demo.service;

import com.example.demo.model.Branch;
import com.example.demo.repositories.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BranchService {
    @Autowired
    private BranchRepository branchRepository;

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }
    public Optional<Branch> getBranchById(String branchId){
        return branchRepository.findById(branchId);
    }
    public Branch addBranch(Branch branch) {
        return branchRepository.save(branch);
    }
    public void deleteBranchById(String branchId){
        branchRepository.deleteById(branchId);
    }
    public Branch getFirstBranchId() {
        return branchRepository.findFirstBranchId();
    }
}
