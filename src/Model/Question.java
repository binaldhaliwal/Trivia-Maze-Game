package Model;

/**
 * The Question class represents a question in a maze game.
 *
 * @author Binal Dhaliwal
 * @version 1.0
 */
public class Question {
    /**
     * Represents the text of the question.
     */
    private String myQuestionText;
    /**
     * Represents the text of the correct answer to the question.
     */
    private String myAnswerText;
    /**
     * Represents the type of question.
     */
    private String myQuestionType;

    /**
     * The Question constructor initializes all fields.
     *
     * @param theQuestionText represents the text of the question.
     * @param theAnswerText represents the text of the correct answer to the question.
     * @param theQuestionType represents the type of question.
     */
    public Question(final String theQuestionText, final String theAnswerText, final String theQuestionType){
        myQuestionText = theQuestionText;
        myAnswerText = theAnswerText;
        myQuestionType = theQuestionType;
    }

    /**
     * getQuestionText gets the text of the question.
     *
     * @return the text of the question.
     */
    public final String getQuestionText() {
        return myQuestionText;
    }

    /**
     * getAnswerText gets the text of the correct answer to the question.
     *
     * @return the text of the correct answer to question.
     */
    public final String getAnswerText(){
        return myAnswerText;
    }

    /**
     * getQuestionType gets the type of question.
     *
     * @return type of question.
     */
    public String getQuestionType() {
        return myQuestionType;
    }

    /**
     * setQuestionText sets text of the question.
     *
     * @param theQuestionText represents text of the question.
     * @throws IllegalArgumentException if theQuestionText is null or empty
     */
    public final void setQuestionText(final String theQuestionText) {
        if (theQuestionText == null || theQuestionText.trim().isEmpty()) {
            throw new IllegalArgumentException("Question text cannot be null or empty");
        }
        myQuestionText = theQuestionText;
    }

    /**
     * setAnswerTest sets text of the correct answer to question.
     *
     * @param theAnswerText represents the  correct answer.
     * @throws IllegalArgumentException if theAnswerText is null or empty
     */
    public final void setAnswerText(final String theAnswerText){
        if (theAnswerText == null || theAnswerText.trim().isEmpty()) {
            throw new IllegalArgumentException("Answer text cannot be null or empty");
        }
        myAnswerText = theAnswerText;
    }

    /**
     * setQuestionType sets the type of question.
     *
     * @param theQuestionType represents the type of questions.
     * @throws IllegalArgumentException if theQuestionType is null or empty
     */
    public final void setQuestionType(final String theQuestionType){
        if (theQuestionType == null || theQuestionType.trim().isEmpty()) {
            throw new IllegalArgumentException("Question type cannot be null or empty");
        }
        myQuestionType = theQuestionType;
    }

}

