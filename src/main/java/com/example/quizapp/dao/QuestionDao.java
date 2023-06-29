package com.example.quizapp.dao;

import com.example.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Long> {

    List<Question> findByCategory(String category);

    @Query(value = "SELECT * FROM questions where category = :category order by rand() limit :numOfQuestions", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int numOfQuestions);
}
