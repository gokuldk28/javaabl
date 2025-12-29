package com.quiz.payload.request;

import java.util.Map;

public class QuizAttemptRequest {
    private Map<Long, Integer> answers; // questionId -> selectedAnswerIndex
    private Integer timeTakenSeconds;

    public Map<Long, Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, Integer> answers) {
        this.answers = answers;
    }

    public Integer getTimeTakenSeconds() {
        return timeTakenSeconds;
    }

    public void setTimeTakenSeconds(Integer timeTakenSeconds) {
        this.timeTakenSeconds = timeTakenSeconds;
    }
}

