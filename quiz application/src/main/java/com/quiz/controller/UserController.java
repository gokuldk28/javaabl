package com.quiz.controller;

import com.quiz.payload.request.QuizAttemptRequest;
import com.quiz.payload.response.LeaderboardEntry;
import com.quiz.payload.response.QuizAttemptResponse;
import com.quiz.payload.response.QuizForUserResponse;
import com.quiz.security.services.UserPrincipal;
import com.quiz.service.QuizAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class UserController {

    @Autowired
    private QuizAttemptService quizAttemptService;

    @GetMapping("/quizzes")
    public ResponseEntity<List<QuizForUserResponse>> getAllQuizzes() {
        return ResponseEntity.ok(quizAttemptService.getAllQuizzesForUser());
    }

    @GetMapping("/quizzes/{quizId}")
    public ResponseEntity<QuizForUserResponse> getQuiz(@PathVariable Long quizId) {
        return ResponseEntity.ok(quizAttemptService.getQuizForUser(quizId));
    }

    @PostMapping("/quizzes/{quizId}/attempt")
    public ResponseEntity<QuizAttemptResponse> submitQuizAttempt(
            @PathVariable Long quizId,
            @RequestBody QuizAttemptRequest attemptRequest,
            Authentication authentication) {
        
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getId();

        return ResponseEntity.ok(quizAttemptService.submitQuizAttempt(userId, quizId, attemptRequest));
    }

    @GetMapping("/attempts")
    public ResponseEntity<List<QuizAttemptResponse>> getMyAttempts(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getId();
        return ResponseEntity.ok(quizAttemptService.getUserAttempts(userId));
    }

    @GetMapping("/quizzes/{quizId}/leaderboard")
    public ResponseEntity<List<LeaderboardEntry>> getLeaderboard(@PathVariable Long quizId) {
        return ResponseEntity.ok(quizAttemptService.getLeaderboard(quizId));
    }
}

