package com.quiz.repository;

import com.quiz.model.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    List<QuizAttempt> findByUserId(Long userId);
    List<QuizAttempt> findByQuizId(Long quizId);
    Optional<QuizAttempt> findByUserIdAndQuizId(Long userId, Long quizId);
    
    @Query("SELECT qa FROM QuizAttempt qa WHERE qa.quiz.id = ?1 ORDER BY qa.totalScore DESC, qa.timeTakenSeconds ASC")
    List<QuizAttempt> findLeaderboardByQuizId(Long quizId);
}

