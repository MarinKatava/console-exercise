package com.cgm.consoleexercise.domain.builder;

import com.cgm.consoleexercise.domain.model.Answer;
import com.cgm.consoleexercise.domain.model.Question;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.InputMismatchException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Accessors(chain = true)
@Getter
@Setter
public class AnswerBuilder {
    private Set<Answer> answers;
    private Answer answer;
    private Question question;

    public AnswerBuilder(Set<Answer> answers, Answer answer) {
        this.answers = answers;
        this.answer = answer;
    }

    private AnswerBuilder createNewAnswer() {
        this.answer = new Answer();
        return this;
    }

    public AnswerBuilder buildAnswers(String input) {
        Pattern pat = Pattern.compile("(\"([^\"]+)\")");
        Matcher mat = pat.matcher(input);
        while (mat.find()) {
            this.answers.add(this.createNewAnswer()
                    .buildAnswer(mat.group(1))
                    .getAnswer());
        }
        if (this.answers.size() == 0) {
            throw new InputMismatchException();
        }

        return this;
    }

    private AnswerBuilder buildAnswer(String answer) {
        String substring = answer.replaceAll("\"", "");
        if (substring.length() > 255) {
            throw new InputMismatchException();
        }
        this.answer.setAnswer(substring);
        this.answer.setQuestion(this.question);
        return this;
    }
}
