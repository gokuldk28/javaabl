package com.quiz.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class QuestionRequest {
    @NotBlank
    private String questionText;

    @NotNull
    private List<String> options;

    @NotNull
    private Integer correctAnswerIndex;

    private Integer points;

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Integer getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setCorrectAnswerIndex(Integer correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public Integer getPoints() {
        return points != null ? points : 1;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}

