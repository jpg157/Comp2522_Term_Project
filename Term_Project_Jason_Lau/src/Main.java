import java.util.Scanner;

import static MyGame.MyGame.MY_GAME_NAME;

public class Main {

    private static final String WORD_GAME_LETTER_OPTION     = "W";
    private static final String NUMBER_GAME_LETTER_OPTION   = "N";
    private static final String MY_GAME_LETTER_OPTION       = "M";
    private static final String QUIT_LETTER_OPTION          = "Q";

    public static void main(final String[] args)
    {
        final Scanner userinputScanner;
        String enteredLetterOption;

        userinputScanner = new Scanner(System.in);

        System.out.print(
                System.lineSeparator() +
                "Welcome! " +
                System.lineSeparator() +
                System.lineSeparator()
        );

        do
        {
            System.out.print(
                "Please enter one of the following options: "   + System.lineSeparator() +
                "Press W to play the Word game."                + System.lineSeparator() +
                "Press N to play the Number game."              + System.lineSeparator() +
                "Press M to play " + MY_GAME_NAME + "."         + System.lineSeparator() +
                "Press Q to quit."                              + System.lineSeparator() +
                                                                System.lineSeparator() +
                "Your option: "
            );

            enteredLetterOption = userinputScanner.nextLine()
                                                  .strip();

            switch (enteredLetterOption)
            {
                case WORD_GAME_LETTER_OPTION:
                    System.out.println(
                            System.lineSeparator() +
                            "Starting Word game ..." +
                            System.lineSeparator()
                    );
                    break;

                case NUMBER_GAME_LETTER_OPTION:
                    System.out.println(
                            System.lineSeparator() +
                            "Starting Number game ..." +
                            System.lineSeparator()
                    );
                    break;

                case MY_GAME_LETTER_OPTION:
                    System.out.println(
                            System.lineSeparator() +
                            "Starting "  + MY_GAME_NAME + " ..." +
                            System.lineSeparator()
                    );
                    break;

                case QUIT_LETTER_OPTION:
                    System.out.println(
                            System.lineSeparator() +
                            "Ending program." +
                            System.lineSeparator()
                    );
                    break;

                default:
                    System.out.println(
                            System.lineSeparator() +
                            "Invalid option entered. Please try again." +
                            System.lineSeparator()
                    );
            }

        }
        while ( !(enteredLetterOption.equals(QUIT_LETTER_OPTION)) );

        userinputScanner.close();
    }
}
