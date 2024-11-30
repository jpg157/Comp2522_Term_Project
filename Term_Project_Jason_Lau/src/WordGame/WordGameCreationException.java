package WordGame;

/**
 * Exception thrown when there is an error in the creation of the WordGame class
 * This exception is used in the WordGame class constructor.
 *
 * <p>This is a custom exception that extends the {@link java.lang.RuntimeException} class.</p>
 * @author Jason Lau
 * @version 1.0
 */
public class WordGameCreationException extends RuntimeException {

    /**
     * Constructs a new WordGameCreationException with a specified detail message.
     *
     * @param message the detail message explaining the cause of the exception
     */
    WordGameCreationException(final String message) {
        super(message);
    }
}
