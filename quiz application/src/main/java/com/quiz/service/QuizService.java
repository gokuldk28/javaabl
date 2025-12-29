package com.quiz.service;

import com.quiz.model.*;
import com.quiz.payload.request.QuestionRequest;
import com.quiz.payload.request.QuizRequest;
import com.quiz.payload.response.QuestionResponse;
import com.quiz.payload.response.QuizResponse;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public List<QuizResponse> getAllQuizzes() {
        return quizRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public QuizResponse getQuizById(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));
        return convertToResponse(quiz);
    }

    public QuizResponse createQuiz(QuizRequest quizRequest) {
        Quiz quiz = new Quiz();
        quiz.setTitle(quizRequest.getTitle());
        quiz.setDescription(quizRequest.getDescription());
        quiz.setCategory(quizRequest.getCategory());
        quiz.setDifficultyLevel(quizRequest.getDifficultyLevel());
        quiz.setTimeLimitMinutes(quizRequest.getTimeLimitMinutes());

        Quiz savedQuiz = quizRepository.save(quiz);

        if (quizRequest.getQuestions() != null) {
            List<Question> questions = new ArrayList<>();
            for (QuestionRequest qReq : quizRequest.getQuestions()) {
                Question question = new Question();
                question.setQuestionText(qReq.getQuestionText());
                question.setOptions(qReq.getOptions());
                question.setCorrectAnswerIndex(qReq.getCorrectAnswerIndex());
                question.setPoints(qReq.getPoints() != null ? qReq.getPoints() : 1);
                question.setQuiz(savedQuiz);
                questions.add(question);
            }
            questionRepository.saveAll(questions);
            savedQuiz.setQuestions(questions);
        }

        return convertToResponse(savedQuiz);
    }

    @Transactional
    public QuizResponse updateQuiz(Long id, QuizRequest quizRequest) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));

        quiz.setTitle(quizRequest.getTitle());
        quiz.setDescription(quizRequest.getDescription());
        quiz.setCategory(quizRequest.getCategory());
        quiz.setDifficultyLevel(quizRequest.getDifficultyLevel());
        quiz.setTimeLimitMinutes(quizRequest.getTimeLimitMinutes());

        // Delete existing questions
        questionRepository.deleteByQuizId(id);
        quiz.getQuestions().clear();

        // Add new questions
        if (quizRequest.getQuestions() != null) {
            List<Question> questions = new ArrayList<>();
            for (QuestionRequest qReq : quizRequest.getQuestions()) {
                Question question = new Question();
                question.setQuestionText(qReq.getQuestionText());
                question.setOptions(qReq.getOptions());
                question.setCorrectAnswerIndex(qReq.getCorrectAnswerIndex());
                question.setPoints(qReq.getPoints() != null ? qReq.getPoints() : 1);
                question.setQuiz(quiz);
                questions.add(question);
            }
            questionRepository.saveAll(questions);
            quiz.setQuestions(questions);
        }

        Quiz updatedQuiz = quizRepository.save(quiz);
        return convertToResponse(updatedQuiz);
    }

    public void deleteQuiz(Long id) {
        if (!quizRepository.existsById(id)) {
            throw new RuntimeException("Quiz not found with id: " + id);
        }
        quizRepository.deleteById(id);
    }

    private QuizResponse convertToResponse(Quiz quiz) {
        QuizResponse response = new QuizResponse();
        response.setId(quiz.getId());
        response.setTitle(quiz.getTitle());
        response.setDescription(quiz.getDescription());
        response.setCategory(quiz.getCategory());
        response.setDifficultyLevel(quiz.getDifficultyLevel());
        response.setTimeLimitMinutes(quiz.getTimeLimitMinutes());
        response.setCreatedAt(quiz.getCreatedAt());
        response.setUpdatedAt(quiz.getUpdatedAt());

        if (quiz.getQuestions() != null) {
            List<QuestionResponse> questionResponses = quiz.getQuestions().stream()
                    .map(this::convertToQuestionResponse)
                    .collect(Collectors.toList());
            response.setQuestions(questionResponses);
        }

        return response;
    }

    private QuestionResponse convertToQuestionResponse(Question question) {
        QuestionResponse response = new QuestionResponse();
        response.setId(question.getId());
        response.setQuestionText(question.getQuestionText());
        response.setOptions(question.getOptions());
        response.setCorrectAnswerIndex(question.getCorrectAnswerIndex());
        response.setPoints(question.getPoints());
        return response;
    }
}

