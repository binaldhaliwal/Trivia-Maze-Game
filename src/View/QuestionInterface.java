package View;

import Model.Question;

/**
 * QuestionInterface represents an interface for displaying a question based on its type.
 *
 * @author Binal Dhaliwal
 * @version 1.0
 */
public interface QuestionInterface {
    /**
     * displayQuestion displays the current question.
     *
     * @param theQuestion the question object that is displayed.
     */
    void displayQuestion(final Question theQuestion);
}

