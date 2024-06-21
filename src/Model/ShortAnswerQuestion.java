package Model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a short answer question that extends the base Question class and is Serializable.
 *
 * @author Binal Dhaliwal
 * @version 1.0
 */
public class ShortAnswerQuestion extends Question implements Serializable {

    /**
     * Serial version UID.
     */
    @Serial
    private static final long serialVersionUID = 7L;
    /**
     * Represents the max length allowed for a short answer question.
     */
    private final int myMaxAnswerLength;

    /**
     * ShortAnswerQuestion constructor creates object for specified question text,
     * answer text, and question type. The max answer length is set to two words.
     *
     * @param theQuestionText represents the text of the question.
     * @param theAnswerText represents the text of the answer.
     * @param theQuestionType represents the type of question.
     */
    public ShortAnswerQuestion(final String theQuestionText, final String theAnswerText, final String theQuestionType) {
        super(theQuestionText, theAnswerText, theQuestionType);
        this.myMaxAnswerLength = 2;
    }

    /**
     * getMaxAnswerLength gets the max length for answer.
     *
     * @return max length for answer.
     */
    public final int getMaxAnswerLength() {
        return myMaxAnswerLength;
    }

    /**
     * getQuestionType gets the question type, in this case, short answer.
     *
     * @return short answer.
     */
    @Override
    public final String getQuestionType() {
        return "Short Answer";
    }


}
