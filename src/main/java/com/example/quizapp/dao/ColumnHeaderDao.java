package com.example.quizapp.dao;

import com.example.quizapp.model.ColumnHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnHeaderDao extends JpaRepository<ColumnHeader, Long> {
}
