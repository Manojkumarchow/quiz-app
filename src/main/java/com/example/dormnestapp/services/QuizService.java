package com.example.dormnestapp.services;

import com.example.dormnestapp.dao.QuestionDao;
import com.example.dormnestapp.dao.QuizDao;
import com.example.dormnestapp.model.Question;
import com.example.dormnestapp.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private final QuizDao quizDao;
    @Autowired
    private final QuestionDao questionDao;

    public QuizService(QuizDao quizDao, QuestionDao questionDao) {
        this.quizDao = quizDao;
        this.questionDao = questionDao;
    }


    public ResponseEntity<String> createQuiz(String category, int numOfQuestions, String title) {

        try {

            List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numOfQuestions);

            Quiz quiz = new Quiz();

            quiz.setTitle(title);
            quiz.setQuestions(questions);

            quizDao.save(quiz);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Failed to create the quiz", HttpStatus.BAD_REQUEST);

    }
}
