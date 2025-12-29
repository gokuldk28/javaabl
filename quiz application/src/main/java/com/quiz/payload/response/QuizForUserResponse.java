package com.quiz.payload.response;

import com.quiz.model.DifficultyLevel;
import java.time.LocalDateTime;
import java.util.List;

public class QuizForUserResponse {
    private Long id;
    private String title;
    private String description;
    private String category;
    private DifficultyLevel difficultyLevel;
    private Integer timeLimitMinutes;
    private List<QuestionForUserResponse> questions;
    private LocalDateTime createdAt;

    public QuizForUserResponse() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Integer getTimeLimitMinutes() {
        return timeLimitMinutes;
    }

    public void setTimeLimitMinutes(Integer timeLimitMinutes) {
        this.timeLimitMinutes = timeLimitMinutes;
    }

    public List<QuestionForUserResponse> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionForUserResponse> questions) {
        this.questions = questions;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

