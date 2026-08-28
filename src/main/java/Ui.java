import java.util.Scanner;

/**
 * Handles all user interactions and text output for the Duke application.
 */
public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads the next line of input from the user.
     *
     * @return The raw input string from the user.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Prints a standard horizontal divider line.
     */
    public void showLine() {
        System.out.println(DIVIDER);
    }

    /**
     * Prints the welcome message upon application startup.
     */
    public void showWelcome() {
        showLine();
        System.out.println("Hello! I'm TBC.");
        System.out.println("What can I do for you?");
        showLine();
    }

    /**
     * Prints the exit goodbye message.
     */
    public void showGoodbye() {
        System.out.println("Bye bye.");
    }

    /**
     * Prints an error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Prints a loading error message if tasks could not be loaded from file.
     */
    public void showLoadingError() {
        System.out.println("Error while loading tasks: file could not be loaded.");
    }

    /**
     * Prints a general message.
     *
     * @param message The message to display.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }
}
