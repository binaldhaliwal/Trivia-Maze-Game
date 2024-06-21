package Model;

import java.util.*;

/**
 * Factory class for creating and filtering questions from a database.
 *
 * @author Binal Dhaliwal
 * @version 1.0
 */
public class QuestionFactory {

    /**
     * Retrieves and filters questions from the database based on specified question types.
     *
     * @param theQuestionTypes the types of questions to retrieve.
     * @return a list of filtered questions.
     */
    public static List<Question> getQuestionsFromDatabase(final List<String> theQuestionTypes) {
        final List<Question> allQuestions = SQLQuestionDataBase.readQuestionsFromDatabase();
        final List<Question> filteredQuestionsMultipleChoice = new ArrayList<>();
        final List<Question> filteredQuestionsTrueFalse = new ArrayList<>();
        final List<Question> filteredQuestionsShortAnswer = new ArrayList<>();

        final Set<String> seenQuestionTextsMultipleChoice = new HashSet<>();
        final Set<String> seenQuestionTextsTrueFalse = new HashSet<>();
        final Set<String> seenQuestionTextsShortAnswer = new HashSet<>();

        for (Question question : allQuestions) {
            final String questionType = question.getQuestionType();
            final String questionText = question.getQuestionText();

            if (theQuestionTypes.contains(questionType)) {
                if ("Multiple-Choice".equals(questionType) && !seenQuestionTextsMultipleChoice.contains(questionText)) {
                    filteredQuestionsMultipleChoice.add(question);
                    seenQuestionTextsMultipleChoice.add(questionText);
                } else if ("True/False".equals(questionType) && !seenQuestionTextsTrueFalse.contains(questionText)) {
                    filteredQuestionsTrueFalse.add(question);
                    seenQuestionTextsTrueFalse.add(questionText);
                } else if ("Short Answer".equals(questionType) && !seenQuestionTextsShortAnswer.contains(questionText)) {
                    filteredQuestionsShortAnswer.add(question);
                    seenQuestionTextsShortAnswer.add(questionText);
                }
            }
        }
        Collections.shuffle(filteredQuestionsMultipleChoice);
        Collections.shuffle(filteredQuestionsTrueFalse);
        Collections.shuffle(filteredQuestionsShortAnswer);

        final List<Question> allFilteredQuestions = new ArrayList<>();
        allFilteredQuestions.addAll(filteredQuestionsMultipleChoice);
        allFilteredQuestions.addAll(filteredQuestionsTrueFalse);
        allFilteredQuestions.addAll(filteredQuestionsShortAnswer);

        return allFilteredQuestions;
    }
}

