package duke.ui;

import java.util.Scanner;

import duke.exception.DukeException;
import duke.task.Task;
import duke.task.TaskList;

/**
 * Handles all user interactions and text output for the Duke application.
 */
public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
    private final Scanner scanner;

    /**
     * Constructs a Ui instance initialized with standard console input.
     */
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
     * Displays all tasks currently in the task list.
     *
     * @param tasks The TaskList containing tasks to display.
     */
    public void showTaskList(TaskList tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            try {
                Task t = tasks.getTask(i);
                System.out.println((i + 1) + ". [" + t.getTaskIcon() + "]["
                        + t.getStatusIcon() + "] " + t);
            } catch (DukeException e) {
                showError(e.getMessage());
            }
        }
    }

    /**
     * Displays confirmation that a task has been added.
     *
     * @param task       The added task.
     * @param totalTasks The new total count of tasks.
     */
    public void showTaskAdded(Task task, int totalTasks) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  [" + task.getTaskIcon() + "][" + task.getStatusIcon() + "] " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Displays confirmation that a task has been removed.
     *
     * @param task       The removed task.
     * @param totalTasks The new total count of tasks.
     */
    public void showTaskDeleted(Task task, int totalTasks) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  [" + task.getTaskIcon() + "]["
                + task.getStatusIcon() + "] " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Displays confirmation that a task has been marked as done.
     *
     * @param task The marked task.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  [" + task.getTaskIcon() + "][" + task.getStatusIcon() + "] " + task);
    }

    /**
     * Displays confirmation that a task has been marked as not done.
     *
     * @param task The unmarked task.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  [" + task.getTaskIcon() + "][" + task.getStatusIcon() + "] " + task);
    }
}
