package WordGame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Score {

    private final LocalDateTime dateTimePlayed;
    private final int           numGamesPlayed;
    private final int           numCorrectFirstAttempt;
    private final int           numCorrectSecondAttempt;
    private final int           numIncorrectTwoAttempts;

    private static final int    CORRECT_FIRST_ATTEMPT_SCORE_MULTIPLIER     = 2;
    private static final int    CORRECT_SECOND_ATTEMPT_SCORE_MULTIPLIER    = 1;

    private static final int    SCORE_FIELD_VALUE_INDEX                    = 1;
    private static final int    SCORE_FIELD_LENGTH_WITH_LABEL_VALUE        = 2;

    private static final int    MIN_NUM_GAMES_PLAYED                       = 1;
    private static final int    MIN_ATTEMPT_NUM_VALUE                      = 0;

    private static final String AVG_SCORE_FORMAT_PATTERN                   = "#0.00";
    private static final int    TWO_DIGIT_TIME_UNIT                        = 10;
    private static final int    EQUAL_DATE_STRING_COMPARE_RESULT           = 0;
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter formatter;
    static
    {
        formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
    }

    public Score(final LocalDateTime dateTimePlayed,
                 final int numGamesPlayed,
                 final int numCorrectFirstAttempt,
                 final int numCorrectSecondAttempt,
                 final int numIncorrectTwoAttempts)
    {
        validateDateTimePlayed(dateTimePlayed);
        validateNumGamesPlayed(numGamesPlayed);
        validateAttemptNumValue(numCorrectFirstAttempt);
        validateAttemptNumValue(numCorrectSecondAttempt);
        validateAttemptNumValue(numIncorrectTwoAttempts);

        this.dateTimePlayed             = dateTimePlayed;
        this.numGamesPlayed             = numGamesPlayed;
        this.numCorrectFirstAttempt     = numCorrectFirstAttempt;
        this.numCorrectSecondAttempt    = numCorrectSecondAttempt;
        this.numIncorrectTwoAttempts    = numIncorrectTwoAttempts;
    }

    public String getDatePlayed() {
        final StringBuilder sbDatePlayed;
        final String        datePlayed;
        final int           year;
        final int           month;
        final int           day;

        sbDatePlayed    = new StringBuilder();
        year            = this.dateTimePlayed.getYear();
        month           = this.dateTimePlayed.getMonthValue();
        day             = this.dateTimePlayed.getDayOfMonth();

        sbDatePlayed.append(year);
        sbDatePlayed.append("-");
        sbDatePlayed.append(month);
        sbDatePlayed.append("-");
        sbDatePlayed.append(day);

        datePlayed = sbDatePlayed.toString();

        return datePlayed;
    }

    public String getTimePlayed() {
        final StringBuilder sbTimePlayed;
        final String        timePlayed;
        final String        hour;
        final String        minute;
        final String        second;

        sbTimePlayed    = new StringBuilder();
        hour            = formatTimeUnit(this.dateTimePlayed.getHour());
        minute          = formatTimeUnit(this.dateTimePlayed.getMinute());
        second          = formatTimeUnit(this.dateTimePlayed.getSecond());

        sbTimePlayed.append(hour);
        sbTimePlayed.append(":");
        sbTimePlayed.append(minute);
        sbTimePlayed.append(":");
        sbTimePlayed.append(second);

        timePlayed = sbTimePlayed.toString();

        return timePlayed;
    }

    public int getScore()
    {
        final int totalScore;

        totalScore = calculateTotalScore(this.numCorrectFirstAttempt, this.numCorrectSecondAttempt);

        return totalScore;
    }

    public double getAverageScore()
    {
        final NumberFormat formatter;
        final double avgScore;

        formatter = new DecimalFormat("#0.00");

        avgScore = (double) this.getScore() / (double) this.getNumGamesPlayed();

        formatter.format(avgScore);

        return avgScore;
    }

    public int getNumGamesPlayed()
    {
        return numGamesPlayed;
    }

    @Override
    public String toString() {
        final String        scoreObjData;
        final StringBuilder sbScoreObjData;
        final String        formattedDateTimePlayed;

        sbScoreObjData = new StringBuilder();
        formattedDateTimePlayed = dateTimePlayed.format(formatter);

        sbScoreObjData.append("Date and Time: ");
        sbScoreObjData.append(formattedDateTimePlayed);
        sbScoreObjData.append("\n");

        sbScoreObjData.append("Games Played: ");
        sbScoreObjData.append(numGamesPlayed);
        sbScoreObjData.append("\n");

        sbScoreObjData.append("Correct First Attempts: ");
        sbScoreObjData.append(numCorrectFirstAttempt);
        sbScoreObjData.append("\n");

        sbScoreObjData.append("Correct Second Attempts: ");
        sbScoreObjData.append(numCorrectSecondAttempt);
        sbScoreObjData.append("\n");

        sbScoreObjData.append("Incorrect Attempts: ");
        sbScoreObjData.append(numIncorrectTwoAttempts);
        sbScoreObjData.append("\n");

        sbScoreObjData.append("Score: ");
        sbScoreObjData.append(this.getScore());
        sbScoreObjData.append(" points");
        sbScoreObjData.append("\n");

        scoreObjData = sbScoreObjData.toString();

        return scoreObjData;
    }

    //===

    public static void appendScoreToFile(final Score score,
                                         final String scoreFilePathName) throws IOException
    {
        validateScore(score);
        validateScoreFilePathName(scoreFilePathName);

        final Path scoreFilePath;

        final StringBuilder sbScoreData;
        final String scoreDataString;

        final NumberFormat formatter;
        final double avgScore;
        final String formattedAvgScore;

        scoreFilePath       = Paths.get(scoreFilePathName);

        sbScoreData         = new StringBuilder();
        formatter           = new DecimalFormat(AVG_SCORE_FORMAT_PATTERN);
        avgScore            = score.getAverageScore();
        formattedAvgScore   = formatter.format(avgScore);

        sbScoreData.append(score);
        sbScoreData.append("Average Score: ");
        sbScoreData.append(formattedAvgScore);
        sbScoreData.append(" points/game");
        sbScoreData.append(System.lineSeparator());
        sbScoreData.append(System.lineSeparator());

        scoreDataString = sbScoreData.toString();

        // Create score file path if not exists
        if (Files.notExists(scoreFilePath))
        {
            Files.createFile(scoreFilePath);
        }

        Files.writeString(scoreFilePath, scoreDataString, StandardOpenOption.APPEND);
    }

    public static List<Score> readScoresFromFile(final String scoreFilePathName) throws FileNotFoundException
    {
        validateScoreFilePathName(scoreFilePathName);

        final Path          scoreFilePath;
        final List<Score>   scores;

        scores          = new ArrayList<>();
        scoreFilePath   = Paths.get(scoreFilePathName);

        if (Files.notExists(scoreFilePath))
        {
            throw new FileNotFoundException("Score file does not exist");
        }

        try (final BufferedReader br = Files.newBufferedReader(scoreFilePath))
        {
            String currentLine;

            // Read the first line of the file
            currentLine = br.readLine();

            while (currentLine != null)
            {
                if (!currentLine.isBlank())
                {
                    final Score score;
                    score = parseScoreFields(br, currentLine);
                    scores.add(score);
                }

                currentLine = br.readLine();
            }
        }
        catch(final IOException exception)
        {
            System.err.println("Unable to read score from file");
            System.err.println(exception.getMessage());
            exception.printStackTrace();
        }

        return scores;
    }

    private static Score parseScoreFields(final BufferedReader br, final String initialLine) throws IOException
    {
        final String[]      initialLineScoreFieldLabelValue;
        final String        dateTimePlayedString;

        final Score         score;
        final LocalDateTime dateTimePlayed;
        final int           numGamesPlayed;
        final int           numCorrectFirstAttempt;
        final int           numCorrectSecondAttempt;
        final int           numIncorrectTwoAttempts;

        // Add the initial line containing the first Score field value (read outside of this method)
        initialLineScoreFieldLabelValue = initialLine.split(": ", SCORE_FIELD_LENGTH_WITH_LABEL_VALUE);
        dateTimePlayedString            = initialLineScoreFieldLabelValue[SCORE_FIELD_VALUE_INDEX];

        // Read each score field from the file
        dateTimePlayed          = LocalDateTime.parse( dateTimePlayedString, formatter);
        numGamesPlayed          = Integer.parseInt( readFileLineAndParseFieldValue(br) );
        numCorrectFirstAttempt  = Integer.parseInt( readFileLineAndParseFieldValue(br) );
        numCorrectSecondAttempt = Integer.parseInt( readFileLineAndParseFieldValue(br) );
        numIncorrectTwoAttempts = Integer.parseInt( readFileLineAndParseFieldValue(br) );

        // Read and discard the next two lines to skip over the "score" and "average score" fields (not instance variables in Score class)
        br.readLine();
        br.readLine();

        score = new Score(
                dateTimePlayed,
                numGamesPlayed,
                numCorrectFirstAttempt,
                numCorrectSecondAttempt,
                numIncorrectTwoAttempts
        );

        return score;
    }

    private static String readFileLineAndParseFieldValue(final BufferedReader br) throws IOException
    {
        final String    currentLine;
        final String[]  scoreFieldLabelAndValue;
        final String    fieldValue;

        currentLine             = br.readLine();
        scoreFieldLabelAndValue = currentLine.split(": ", SCORE_FIELD_LENGTH_WITH_LABEL_VALUE);

        if (scoreFieldLabelAndValue.length != SCORE_FIELD_LENGTH_WITH_LABEL_VALUE) {
            throw new IllegalArgumentException("Invalid line format: " + currentLine);
        }

        fieldValue = scoreFieldLabelAndValue[SCORE_FIELD_VALUE_INDEX];

        return fieldValue;

    }

    private static int calculateTotalScore(final int numCorrectFirstAttempt,
                                    final int numCorrectSecondAttempt)
    {
        final int totalScore;
        final int totalScoreFirstAttempt;
        final int totalScoreSecondAttempt;

        totalScoreFirstAttempt = numCorrectFirstAttempt     * CORRECT_FIRST_ATTEMPT_SCORE_MULTIPLIER;
        totalScoreSecondAttempt = numCorrectSecondAttempt   * CORRECT_SECOND_ATTEMPT_SCORE_MULTIPLIER;

        totalScore = totalScoreFirstAttempt + totalScoreSecondAttempt;

        return totalScore;
    }

    private static String formatTimeUnit(final int timeUnit)
    {
        final StringBuilder sbTimeUnit;
        final String formattedTimeUnit;

        sbTimeUnit = new StringBuilder();

        if (timeUnit < TWO_DIGIT_TIME_UNIT)
        {
            sbTimeUnit.append("0");
        }

        sbTimeUnit.append(timeUnit);
        formattedTimeUnit = sbTimeUnit.toString();

        return formattedTimeUnit;
    }

    //===

    private static void validateDateTimePlayed(final LocalDateTime dateTimePlayed)
    {
        if (dateTimePlayed == null)
        {
            throw new IllegalArgumentException("Entered date time played cannot be null");
        }

        final LocalDateTime currentTime;
        final String formattedCurrentDateTime;
        final String formattedDateTimePlayed;

        currentTime                 = LocalDateTime.now();
        formattedCurrentDateTime    = currentTime.format(formatter);
        formattedDateTimePlayed     = dateTimePlayed.format(formatter);

        if (formattedCurrentDateTime.compareTo(formattedDateTimePlayed) < EQUAL_DATE_STRING_COMPARE_RESULT)
        {
            throw new IllegalArgumentException(
                    "Entered date time played cannot come before the current date"
                    + "Current date: " + formattedCurrentDateTime
                    + "Entered date time: " + formattedDateTimePlayed
            );
        }
    }

    private static void validateNumGamesPlayed(final int numGamesPlayed)
    {
        if (numGamesPlayed < MIN_NUM_GAMES_PLAYED)
        {
            throw new IllegalArgumentException("Number of games played must be greater than or equal to " + MIN_NUM_GAMES_PLAYED);
        }
    }

    private static void validateAttemptNumValue(final int attemptNumValue)
    {
        if (attemptNumValue < MIN_ATTEMPT_NUM_VALUE)
        {
            throw new IllegalArgumentException("Attempt number must be greater than or equal to " + MIN_ATTEMPT_NUM_VALUE);
        }
    }

    private static void validateScore(final Score score)
    {
        if (score == null)
        {
            throw new IllegalArgumentException("Entered score cannot be null");
        }
    }

    private static void validateScoreFilePathName(final String scoreFilePathName)
    {
        if (scoreFilePathName == null)
        {
            throw new IllegalArgumentException("Entered score file path name cannot be null");
        }
    }

}
