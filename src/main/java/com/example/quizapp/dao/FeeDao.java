package com.example.quizapp.dao;

import com.example.quizapp.model.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FeeDao extends JpaRepository<Fee, Long> {

    @Query(value = "SELECT * FROM FEE where person_id = :personId", nativeQuery = true)
    List<Fee> findByPersonId(long personId);

    @Query(value = "SELECT * FROM FEE where refId = :refId", nativeQuery = true)
    List<Fee> findByRefId(String refId);

    @Query(value = "SELECT * FROM FEE where paidDate = :localDate", nativeQuery = true)
    List<Fee> findByDate(LocalDate localDate);
}
