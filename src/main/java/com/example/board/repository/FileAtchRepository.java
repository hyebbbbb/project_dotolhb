package com.example.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.board.model.FileAtch;

@Repository
public interface FileAtchRepository extends JpaRepository<FileAtch,Integer> {
    
}
