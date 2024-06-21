package Model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a true/false question that extends the base Question class and is Serializable.
 *
 * @author Binal Dhaliwal
 * @version 1.0
 */
public class TrueFalseQuestion extends Question implements Serializable {
    /**
     * Serial version UID.
     */
    @Serial
    private static final long serialVersionUID = 8L;

    /**
     * TrueFalseQuestion constructor creates object for specified question text,
     * answer text, and question type.
     *
     * @param theQuestionText represents the text of the question.
     * @param theAnswerText represents the text of the answer.
     * @param theQuestionType represents the type of question.
     */
    public TrueFalseQuestion(final String theQuestionText, final String theAnswerText, final String theQuestionType) {
        super(theQuestionText, theAnswerText, theQuestionType);
    }

    /**
     * getCorrectAnswer gets the correct answer to true/false.
     *
     * @return correct answer, either true or false.
     */
    public final boolean getCorrectAnswer() {
        return Boolean.parseBoolean(getAnswerText());

    }

    /**
     * getQuestionType gets the question type, in this case, true/false.
     *
     * @return true/false.
     */
    @Override
    public final String getQuestionType() {
        return "True/False";
    }

}

