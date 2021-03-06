package com.cgm.consoleexercise.service;

import com.cgm.consoleexercise.domain.builder.AnswerBuilder;
import com.cgm.consoleexercise.domain.builder.QuestionBuilder;
import com.cgm.consoleexercise.domain.model.Answer;
import com.cgm.consoleexercise.domain.model.Question;
import com.cgm.consoleexercise.repository.QuestionRepository;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;

@Setter
@Accessors(chain = true)
@Service
public class QuestionService {

    private QuestionRepository questionRepository;
    private QuestionBuilder questionBuilder;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void addQuestion(String input) {
        try {
            this.questionRepository.save(this.questionBuilder
                    .setAnswerBuilder(new AnswerBuilder(new LinkedHashSet<>(), new Answer()))
                    .buildQuestion(input)
                    .getQuestion());
        } catch (DataIntegrityViolationException e) {
            System.out.println("This question is already stored in the database.");
        }
    }

    public Question getQuestion(String input) {
        return this.questionRepository.findByQuestion(input);
    }
}
