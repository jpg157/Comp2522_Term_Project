import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

//TODO: check if this is allowed
import WordGame.WordGame;
import NumberGame.NumberGame;
import MyGame.MyGame;

/**
 * The {@code Main} class has a command line
 * interface to play one the games from the list:
 * <ul>
 *     <li>Word Game</li>
 *     <li>Number Game</li>
 *     <li>My (custom) Game</li>
 * </ul>
 *
 * @author Vincent Fung and Jason Lau
 * @version 1.0
 */
public class Main {

    private static final String WORD_GAME_LETTER_OPTION     = "W";
    private static final String NUMBER_GAME_LETTER_OPTION   = "N";
    private static final String MY_GAME_LETTER_OPTION       = "M";
    private static final String QUIT_LETTER_OPTION          = "Q";

    // WORD GAME CONSTANTS
    private static final String PLAY_GAME_AGAIN_VALUE       = "yes";
    private static final String QUIT_GAME_VALUE             = "no";

    public static void main(final String[] args)
    {
        final WordGame      wordGame;
        final NumberGame    numberGame;
        final MyGame        myGame;

        final Scanner       userinputScanner;
        String              enteredLetterOption;

        wordGame            = new WordGame();
        numberGame          = new NumberGame();
        myGame              = new MyGame();

        userinputScanner    = new Scanner(System.in);

        System.out.print(
                System.lineSeparator() +
                "Welcome! " +
                System.lineSeparator()
        );

        do
        {
            System.out.print(
                "Please enter one of the following options: "   + System.lineSeparator() +
                "Press W to play the Word game."                + System.lineSeparator() +
                "Press N to play the Number game."              + System.lineSeparator() +
                "Press M to play " + MyGame.MY_GAME_NAME + "."  + System.lineSeparator() +
                "Press Q to quit."                              + System.lineSeparator() +
                                                                System.lineSeparator() +
                "Your option: "
            );

            enteredLetterOption = userinputScanner.nextLine()
                                                  .strip();

            switch (enteredLetterOption)
            {
                case WORD_GAME_LETTER_OPTION:
                {
                    System.out.println(
                            System.lineSeparator() +
                            "Starting Word game ..." +
                            System.lineSeparator()
                    );

                    playWordGame(wordGame);

                    break;
                }


                case NUMBER_GAME_LETTER_OPTION:
                {
                    System.out.println(
                            System.lineSeparator() +
                            "Starting Number game ..."
                    );
                    break;
                }

                case MY_GAME_LETTER_OPTION:
                {
                    System.out.println(
                            System.lineSeparator() +
                            "Starting "  + MY_GAME_NAME + " ..."
                    );
                    break;
                }

                case QUIT_LETTER_OPTION:
                {
                    System.out.println(
                            System.lineSeparator() +
                            "Ending program."
                    );
                    break;
                }

                default:
                {
                    System.out.println(
                            System.lineSeparator() +
                            "Invalid option entered. Please try again." +
                            System.lineSeparator()
                    );
                }
            }

        }
        while ( !(enteredLetterOption.equals(QUIT_LETTER_OPTION)) );

        userinputScanner.close();
    }

    private static void playWordGame(final WordGame wordGame)
    {
        if (wordGame == null)
        {
            throw new NullPointerException("Word game cannot be null");
        }

        final Scanner userInputScanner;
        String enteredPlayAgainChoice;

        userInputScanner = new Scanner(System.in);

        do
        {
            String userInputBuffer;

            // Play the word game
            wordGame.playGame();

            do
            {
                System.out.print("Would you like to play again? (enter yes or no): ");

                userInputBuffer = userInputScanner.nextLine();
                userInputBuffer = userInputBuffer.trim()
                                                 .toLowerCase();

                if (!userInputBuffer.equals(PLAY_GAME_AGAIN_VALUE)
                        &&
                    !userInputBuffer.equals(QUIT_GAME_VALUE))
                {
                    System.out.println(
                            System.lineSeparator() +
                            "Invalid option entered. Please try again."
                    );
                }
                System.out.print(System.lineSeparator());
            }
            while (
                    !userInputBuffer.equals(PLAY_GAME_AGAIN_VALUE)
                    &&
                    !userInputBuffer.equals(QUIT_GAME_VALUE)
            );

            if (userInputBuffer.equals(QUIT_GAME_VALUE))
            {
                try
                {
                    wordGame.checkAndDisplayIfCurrentWordGameIsHighScore();
                    wordGame.saveWordGameScoreAndDeleteCurrent();
                }
                catch(final IOException exception)
                {
                    System.err.println("Unable to save score for all games at current date");
                    System.err.println(exception.getMessage());
                }
            }

            enteredPlayAgainChoice = userInputBuffer;
        }
        while ( enteredPlayAgainChoice.equals(PLAY_GAME_AGAIN_VALUE) );

    }


}
