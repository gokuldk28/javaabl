package com.quiz.service;

import com.quiz.model.*;
import com.quiz.payload.request.QuizAttemptRequest;
import com.quiz.payload.response.*;
import com.quiz.repository.QuizAttemptRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizAttemptService {

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    public List<QuizForUserResponse> getAllQuizzesForUser() {
        return quizRepository.findAll().stream()
                .map(this::convertToQuizForUserResponse)
                .collect(Collectors.toList());
    }

    public QuizForUserResponse getQuizForUser(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + quizId));
        return convertToQuizForUserResponse(quiz);
    }

    @Transactional
    public QuizAttemptResponse submitQuizAttempt(Long userId, Long quizId, QuizAttemptRequest attemptRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        QuizAttempt attempt = new QuizAttempt();
        attempt.setUser(user);
        attempt.setQuiz(quiz);
        attempt.setAnswers(attemptRequest.getAnswers());
        attempt.setTimeTakenSeconds(attemptRequest.getTimeTakenSeconds());
        attempt.setStartedAt(LocalDateTime.now().minusSeconds(attemptRequest.getTimeTakenSeconds()));
        attempt.setCompletedAt(LocalDateTime.now());

        // Calculate scores
        int totalScore = 0;
        int maxScore = 0;

        for (Question question : quiz.getQuestions()) {
            maxScore += question.getPoints();
            Integer userAnswer = attemptRequest.getAnswers().get(question.getId());
            if (userAnswer != null && userAnswer.equals(question.getCorrectAnswerIndex())) {
                totalScore += question.getPoints();
            }
        }

        attempt.setTotalScore(totalScore);
        attempt.setMaxScore(maxScore);

        QuizAttempt savedAttempt = quizAttemptRepository.save(attempt);
        return convertToAttemptResponse(savedAttempt);
    }

    public List<QuizAttemptResponse> getUserAttempts(Long userId) {
        return quizAttemptRepository.findByUserId(userId).stream()
                .map(this::convertToAttemptResponse)
                .collect(Collectors.toList());
    }

    public List<LeaderboardEntry> getLeaderboard(Long quizId) {
        List<QuizAttempt> attempts = quizAttemptRepository.findLeaderboardByQuizId(quizId);
        return attempts.stream()
                .map(this::convertToLeaderboardEntry)
                .collect(Collectors.toList());
    }

    private QuizForUserResponse convertToQuizForUserResponse(Quiz quiz) {
        QuizForUserResponse response = new QuizForUserResponse();
        response.setId(quiz.getId());
        response.setTitle(quiz.getTitle());
        response.setDescription(quiz.getDescription());
        response.setCategory(quiz.getCategory());
        response.setDifficultyLevel(quiz.getDifficultyLevel());
        response.setTimeLimitMinutes(quiz.getTimeLimitMinutes());
        response.setCreatedAt(quiz.getCreatedAt());

        if (quiz.getQuestions() != null) {
            List<QuestionForUserResponse> questionResponses = quiz.getQuestions().stream()
                    .map(this::convertToQuestionForUserResponse)
                    .collect(Collectors.toList());
            response.setQuestions(questionResponses);
        }

        return response;
    }

    private QuestionForUserResponse convertToQuestionForUserResponse(Question question) {
        QuestionForUserResponse response = new QuestionForUserResponse();
        response.setId(question.getId());
        response.setQuestionText(question.getQuestionText());
        response.setOptions(question.getOptions());
        response.setPoints(question.getPoints());
        return response;
    }

    private QuizAttemptResponse convertToAttemptResponse(QuizAttempt attempt) {
        QuizAttemptResponse response = new QuizAttemptResponse();
        response.setId(attempt.getId());
        response.setQuizId(attempt.getQuiz().getId());
        response.setQuizTitle(attempt.getQuiz().getTitle());
        response.setTotalScore(attempt.getTotalScore());
        response.setMaxScore(attempt.getMaxScore());
        response.setCompletedAt(attempt.getCompletedAt());
        response.setTimeTakenSeconds(attempt.getTimeTakenSeconds());
        response.setAnswers(attempt.getAnswers());

        if (attempt.getMaxScore() > 0) {
            double percentage = (attempt.getTotalScore().doubleValue() / attempt.getMaxScore().doubleValue()) * 100;
            response.setPercentage(Math.round(percentage * 100.0) / 100.0);
        } else {
            response.setPercentage(0.0);
        }

        return response;
    }

    private LeaderboardEntry convertToLeaderboardEntry(QuizAttempt attempt) {
        LeaderboardEntry entry = new LeaderboardEntry();
        entry.setUsername(attempt.getUser().getUsername());
        entry.setTotalScore(attempt.getTotalScore());
        entry.setMaxScore(attempt.getMaxScore());
        entry.setTimeTakenSeconds(attempt.getTimeTakenSeconds());
        entry.setAttemptId(attempt.getId());

        if (attempt.getMaxScore() > 0) {
            double percentage = (attempt.getTotalScore().doubleValue() / attempt.getMaxScore().doubleValue()) * 100;
            entry.setPercentage(Math.round(percentage * 100.0) / 100.0);
        } else {
            entry.setPercentage(0.0);
        }

        return entry;
    }
}

