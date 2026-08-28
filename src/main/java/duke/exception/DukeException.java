package duke.exception;

/**
 * Represents an exception specific to the Duke application.
 */
public class DukeException extends Exception {
    private static final long serialVersionUID = 1L;

    public DukeException(String message) {
        super(message);
    }
}
