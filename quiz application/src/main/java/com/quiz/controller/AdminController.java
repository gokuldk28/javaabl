package com.quiz.controller;

import com.quiz.payload.request.QuizRequest;
import com.quiz.payload.response.MessageResponse;
import com.quiz.payload.response.QuizResponse;
import com.quiz.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/quizzes")
    public ResponseEntity<List<QuizResponse>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    @GetMapping("/quizzes/{id}")
    public ResponseEntity<QuizResponse> getQuizById(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.getQuizById(id));
    }

    @PostMapping("/quizzes")
    public ResponseEntity<QuizResponse> createQuiz(@Valid @RequestBody QuizRequest quizRequest) {
        return ResponseEntity.ok(quizService.createQuiz(quizRequest));
    }

    @PutMapping("/quizzes/{id}")
    public ResponseEntity<QuizResponse> updateQuiz(@PathVariable Long id, @Valid @RequestBody QuizRequest quizRequest) {
        return ResponseEntity.ok(quizService.updateQuiz(id, quizRequest));
    }

    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity<MessageResponse> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.ok(new MessageResponse("Quiz deleted successfully!"));
    }
}

