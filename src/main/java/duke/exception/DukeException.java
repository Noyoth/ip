package duke.exception;

/**
 * Represents an exception specific to the Duke application.
 */
public class DukeException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a DukeException with the specified error detail message.
     *
     * @param message The detail message.
     */
    public DukeException(String message) {
        super(message);
    }
}
