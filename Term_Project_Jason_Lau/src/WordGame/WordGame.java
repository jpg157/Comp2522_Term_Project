package WordGame;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

public class WordGame {

    private final World worldObjWordGame;
    private int numGamesPlayed;
    private int numCorrectQuestionsFirstAttempt;
    private int numCorrectQuestionsSecondAttempt;
    private int numIncorrectQuestions;
    private final List<Score> scores;

    final String SCORE_FILE_PATH_NAME = "src/WordGame/score.txt";

    private static final int NUM_QUESTIONS                  = 10;
    private static final int NUM_RANDOM_COUNTRY_NAMES       = NUM_QUESTIONS;

    private static final int RANDOM_COUNTRY_NAME_LIST_FIRST_INDEX   = 0;

    private static final int QUESTION_INDEX                 = 0;
    private static final int ANSWER_INDEX                   = 1;
    private static final int QUESTION_ANSWER_ARR_SIZE       = 2;

    private static final int FIRST_QUESTION_NUM             = 1;
    private static final int SECOND_QUESTION_NUM            = 2;
    private static final int THIRD_QUESTION_NUM             = 3;
    private static final int QUESTION_NUM_UPPER_BOUND_EXCL  = 4;

    private static final int COUNTRY_FACT_ONE_INDEX         = 0;

    private static final int INITIAL_NUM_GAMES_PLAYED       = 0;
    private static final int ONE_WORD_GAME                  = 1;
    private static final int INITIAL_ATTEMPT_NUM            = 1;
    private static final int ALLOWED_NUM_ATTEMPTS           = 2;
    private static final int INITIAL_QUESTION_SCORE_COUNT       = 0;

    private static final String RANDOM_QUESTION_1 = "What is the name of the country with the capital city of";
    private static final String RANDOM_QUESTION_2 = "What is the capital city of";
    private static final String RANDOM_QUESTION_3 = "Which country is being described from this fact";

    public WordGame()
    {
        try
        {
            this.worldObjWordGame               = new World();
            this.scores                         = new ArrayList<>();
            numGamesPlayed                      = INITIAL_NUM_GAMES_PLAYED;
            numCorrectQuestionsFirstAttempt     = INITIAL_QUESTION_SCORE_COUNT;
            numCorrectQuestionsSecondAttempt    = INITIAL_QUESTION_SCORE_COUNT;
            numIncorrectQuestions               = INITIAL_QUESTION_SCORE_COUNT;
        }
        catch (final IOException exception)
        {
            throw new WordGameCreationException(
                    "Unable to create WordGame object:"
                    + exception.getMessage()
            );
        }
    }

    public void playGame()
    {
        final List<String> randomCountryNamesList;
        final Map<String, String> questionsAndAnswersMap;

        randomCountryNamesList = getRandomSublistOfCountryNames();

        // Populate questionsAndAnswersMap with question and answer
        questionsAndAnswersMap = createRandomizedQuestionsAndAnswersMap(randomCountryNamesList);

        System.out.println(
                "============================================================"
                + System.lineSeparator()
                + "                         Word Game                          "
                + System.lineSeparator()
                + "============================================================"
                + System.lineSeparator()
                + System.lineSeparator()
                + "*** There are " + NUM_QUESTIONS + " questions to answer ***"
                + System.lineSeparator()
                + "*** Please answer each question starting with capital letter ***"
                + System.lineSeparator()
        );

        // For each question / answer pair:
        questionsAndAnswersMap.forEach((question, answer) -> {
            System.out.println("-------------------------------------------------------------------------------");
            displayQuestionAndProcessAnswer(question, answer);
        });

        System.out.println("-------------------------------------------------------------------------------");

        System.out.println(
                "End of game"
                + System.lineSeparator()
        );

        incrementNumGamesPlayed();

        displayGameScore();
    }

    //===

    /**
     * Saves score to a list of scores and to a file.
     * @throws IOException if there was an issue while creating or writing to file
     */
    public void saveScoreForAllGamesAtCurrentDate() throws IOException
    {
        final Score score;

        score = createScore();

//        checkIfNewHighScore(score);

        scores.add(score);
        Score.appendScoreToFile(score, SCORE_FILE_PATH_NAME);


        resetAllScoreValues();
    }

//    private void checkIfNewHighScore(final Score score)
//    {
//
//        Consumer<String> consumer;
//        consumer =
//        scores.forEach()
//    }

    private Score createScore()
    {
        final LocalDateTime dateTimePlayed;
        final Score score;

        dateTimePlayed = LocalDateTime.now();
        score = new Score(
                dateTimePlayed,
                numGamesPlayed,
                numCorrectQuestionsFirstAttempt,
                numCorrectQuestionsSecondAttempt,
                numIncorrectQuestions
        );

        return score;
    }

    private void resetAllScoreValues()
    {
        numGamesPlayed                      = INITIAL_NUM_GAMES_PLAYED;
        numCorrectQuestionsFirstAttempt     = INITIAL_QUESTION_SCORE_COUNT;
        numCorrectQuestionsSecondAttempt    = INITIAL_QUESTION_SCORE_COUNT;
        numIncorrectQuestions               = INITIAL_QUESTION_SCORE_COUNT;
    }

    private void displayGameScore()
    {
        final String        scoreData;
        final StringBuilder sbScoreData;

        sbScoreData = new StringBuilder();

        sbScoreData.append(numGamesPlayed);

        if (numGamesPlayed == ONE_WORD_GAME)
        {
            sbScoreData.append(" word game played");
        }
        else
        {
            sbScoreData.append(" word games played");
        }

        sbScoreData.append(System.lineSeparator());

        sbScoreData.append(numCorrectQuestionsFirstAttempt);
        sbScoreData.append(" correct answers on the first attempt");
        sbScoreData.append(System.lineSeparator());

        sbScoreData.append(numCorrectQuestionsSecondAttempt);
        sbScoreData.append(" correct answers on the second attempt");
        sbScoreData.append(System.lineSeparator());

        sbScoreData.append(numIncorrectQuestions);
        sbScoreData.append(" incorrect answers on two attempts each");
        sbScoreData.append(System.lineSeparator());

        scoreData = sbScoreData.toString();

        System.out.println(scoreData);
    }

    private void displayQuestionAndProcessAnswer(final String question, final String answer)
    {
        final Scanner answerInputScanner;
        int attemptNumber;
        boolean answeredCorrectly;

        answerInputScanner  = new Scanner(System.in);
        attemptNumber       = INITIAL_ATTEMPT_NUM;
        answeredCorrectly   = false;

        // Display current question
        System.out.print(
                question
                + System.lineSeparator()
        );

        while (!answeredCorrectly && ( attemptNumber <= ALLOWED_NUM_ATTEMPTS ))
        {
            final String userAnswer;

            System.out.print(
                    "[Attempt number " + attemptNumber + " / " + ALLOWED_NUM_ATTEMPTS + "]"
                    + System.lineSeparator()
                    + "Your answer: "
            );

            // Accept answer from user
            userAnswer = answerInputScanner.nextLine()
                                            .trim();

            // Check user input with the correct answer
            if (userAnswer.equals(answer))
            {
                answeredCorrectly = true;
                System.out.println(System.lineSeparator() + "CORRECT" + System.lineSeparator());
            }
            else
            {
                System.out.println(System.lineSeparator() + "INCORRECT" + System.lineSeparator());

                // Increment attempt number for the next iteration
                attemptNumber++;
            }

            // add number of attempts to corresponding attempt record value
            if (answeredCorrectly)
            {
                if (attemptNumber == INITIAL_ATTEMPT_NUM)
                {
                    incrementNumCorrectQuestionsFirstAttempt();
                }
                else // (attemptNumber == ALLOWED_NUM_ATTEMPTS)
                {
                    incrementNumCorrectQuestionsSecondAttempt();
                }
            }
            else
            {
                if (attemptNumber > ALLOWED_NUM_ATTEMPTS)
                {
                    incrementNumIncorrectQuestions();

                    System.out.println(
                            "The correct answer was " + answer
                            + System.lineSeparator()
                    );
                }
            }
        }
    }

    private void incrementNumGamesPlayed()
    {
        numGamesPlayed++;
    }

    private void incrementNumCorrectQuestionsFirstAttempt()
    {
        numCorrectQuestionsFirstAttempt++;
    }

    private void incrementNumCorrectQuestionsSecondAttempt()
    {
        numCorrectQuestionsSecondAttempt++;
    }

    private void incrementNumIncorrectQuestions()
    {
        numIncorrectQuestions++;
    }

    //===

    private List<String> getRandomSublistOfCountryNames()
    {
        final List<String> allCountryNames;
        final List<String> randomSublistOfCountryNames;

        allCountryNames = worldObjWordGame.getAllCountryNames();

        if (NUM_RANDOM_COUNTRY_NAMES > allCountryNames.size())
        {
            throw new IllegalArgumentException(
                    "Requested number of country names value ("
                    + NUM_RANDOM_COUNTRY_NAMES
                    + ") is greater than the country name list length."
            );
        }

        Collections.shuffle(allCountryNames);
        randomSublistOfCountryNames = allCountryNames.subList(RANDOM_COUNTRY_NAME_LIST_FIRST_INDEX, NUM_RANDOM_COUNTRY_NAMES);

        return randomSublistOfCountryNames;
    }

    private Map<String, String> createRandomizedQuestionsAndAnswersMap(final List<String> randomCountryNamesList)
    {
        if (randomCountryNamesList == null || randomCountryNamesList.isEmpty())
        {
            throw new IllegalArgumentException("randomCountryNamesList is null or empty");
        }

        final Map<String, String> questionsAndAnswersMap;

        questionsAndAnswersMap = new HashMap<>();

        // For each country name in the randomized list:
            // generate a random question and answer for the entered country and add them as key-value to questionsAndAnswersMap
        randomCountryNamesList.forEach(countryName ->
            createAndStoreCountryQuestionAnswerPair(countryName, questionsAndAnswersMap)
        );

        return questionsAndAnswersMap;

    }

    private void createAndStoreCountryQuestionAnswerPair(final String countryName, final Map<String, String> questionsAndAnswersMap)
    {
        validateCountryName(countryName);

        final Country country;
        final String[] questionAndAnswer;

        country = worldObjWordGame.getCountry(countryName);

        if (country == null)
        {
            throw new IllegalArgumentException("Country name " + countryName + " does not exist. Unable to generate question.");
        }

        questionAndAnswer = generateQuestionAndAnswerForCountry(country);
        questionsAndAnswersMap.put(questionAndAnswer[QUESTION_INDEX], questionAndAnswer[ANSWER_INDEX]);
    }

    private static String[] generateQuestionAndAnswerForCountry(final Country country)
    {
        if (country == null)
        {
            throw new IllegalArgumentException("Country name is null");
        }

        final String countryName;
        final String countryCapitalCityName;
        final String[] countryFactsArray;

        final String[] questionAndAnswer;
        final Random randNumGen;
        final int randomQuestionNum;

        countryName             = country.getName();
        countryCapitalCityName  = country.getCapitalCityName();
        countryFactsArray       = country.getFacts();

        questionAndAnswer       = new String[QUESTION_ANSWER_ARR_SIZE];
        randNumGen              = new Random();
        randomQuestionNum       = randNumGen.nextInt(FIRST_QUESTION_NUM, QUESTION_NUM_UPPER_BOUND_EXCL);

        // Generate a random question and answer for the country
        switch (randomQuestionNum)
        {
            case (FIRST_QUESTION_NUM):
            {
                questionAndAnswer[QUESTION_INDEX]   = formatQuestion(RANDOM_QUESTION_1, countryCapitalCityName);
                questionAndAnswer[ANSWER_INDEX]     = countryName;

                break;
            }
            case (SECOND_QUESTION_NUM):
            {
                questionAndAnswer[QUESTION_INDEX]   = formatQuestion(RANDOM_QUESTION_2, countryName);
                questionAndAnswer[ANSWER_INDEX]     = countryCapitalCityName;

                break;
            }
            case (THIRD_QUESTION_NUM):
            {
                final int numberOfCountryFacts;
                final int randomFactNum;

                numberOfCountryFacts                = Country.NUMBER_OF_COUNTRY_FACTS;
                randomFactNum                       = randNumGen.nextInt(COUNTRY_FACT_ONE_INDEX, numberOfCountryFacts);

                questionAndAnswer[QUESTION_INDEX]   = formatQuestion(RANDOM_QUESTION_3, countryFactsArray[randomFactNum]);
                questionAndAnswer[ANSWER_INDEX]     = countryName;

                break;
            }
            default:
            {
                throw new IllegalArgumentException("Random question number generated is not valid");
            }
        }

        return questionAndAnswer;
    }

    private static String formatQuestion(final String question, final String questionSubject)
    {
        final String formattedQuestion;

        final StringBuilder sbFormattedQuestion;
        final StringBuilder stringAfterQuestion;

        sbFormattedQuestion = new StringBuilder(question);
        stringAfterQuestion = new StringBuilder();

        if (question.equals(RANDOM_QUESTION_3))
        {
            sbFormattedQuestion.append(":");
            sbFormattedQuestion.append(System.lineSeparator());
        }
        else
        {
            sbFormattedQuestion.append(" ");

            stringAfterQuestion.append("?");
        }

        sbFormattedQuestion.append(questionSubject);
        sbFormattedQuestion.append(stringAfterQuestion);
        sbFormattedQuestion.append(System.lineSeparator());

        formattedQuestion = sbFormattedQuestion.toString();

        return formattedQuestion;
    }



    private static void validateCountryName(final String countryName)
    {
        if (countryName == null || countryName.isBlank())
        {
            throw new IllegalArgumentException("Country name is null or empty");
        }
    }

}
