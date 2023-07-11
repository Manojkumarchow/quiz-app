package com.example.dormnestapp.dao;

import com.example.dormnestapp.model.ColumnHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnHeaderDao extends JpaRepository<ColumnHeader, Long> {
}
