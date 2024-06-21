package Model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a multiple-choice question that extends the base Question class and is Serializable.
 *
 * @author Binal Dhaliwal
 * @version 1.0
 */
public class MultipleChoiceQuestion extends Question implements Serializable {
    /**
     * Serial version UID.
     */
    @Serial
    private static final long serialVersionUID = 5L;
    /**
     * Represents choices for multiple choice.
     */
    private String[] myChoices;
    /**
     * Represents the correct option for the multiple choice question.
     */
    private String myCorrectOption;

    /**
     * MultipleChoiceQuestion constructor creates object for specified question text,
     * answer text, and question type.
     *
     * @param theQuestionText represents the text of the question.
     * @param theAnswerText represents the text of the answer.
     * @param theQuestionType represents the type of question.
     */
    public MultipleChoiceQuestion(final String theQuestionText, final String theAnswerText, final String theQuestionType) {
        super(theQuestionText, theAnswerText, theQuestionType);
    }

    /**
     * getChoices gets choices for multiple choice question.
     *
     * @return the choices.
     */
    public final String[] getChoices() {
        return myChoices;
    }

    /**
     * getCorrectOption gets correct option for multiple choice question.
     *
     * @return the correct option.
     */
    public final String getCorrectOption() {
        return myCorrectOption;
    }

    /**
     * setChoices sets choices for the multiple choice question
     *
     * @param theChoices represents the choices.
     * @throws IllegalArgumentException if theChoices is null
     */
    public final void setChoices (final String[] theChoices){
        if (theChoices == null) {
            throw new IllegalArgumentException("Choices array cannot be null");
        }
        myChoices = theChoices;
    }

    /**
     * setCorrectOption sets the correct option among the choices.
     *
     * @param theCorrectOption represents the correct option.
     */
    public final void setCorrectOption(final String theCorrectOption){
        if (theCorrectOption == null || theCorrectOption.trim().isEmpty()) {
            throw new IllegalArgumentException("Correct option cannot be null or empty");
        }
        myCorrectOption = theCorrectOption;
    }

    /**
     * getQuestionType gets the question type, in this case, multiple choice.
     *
     * @return multiple choice.
     */
    @Override
    public final String getQuestionType() {
        return "Multiple-Choice";
    }

}
