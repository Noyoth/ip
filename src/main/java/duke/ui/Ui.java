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
    private String lastResponse = "";

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
        String msg = "Hello! I'm TBC.\nWhat can I do for you?";
        System.out.println(msg);
        showLine();
        lastResponse = msg;
    }

    /**
     * Prints the exit goodbye message.
     */
    public void showGoodbye() {
        lastResponse = "Bye bye.";
        System.out.println(lastResponse);
    }

    /**
     * Prints an error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        lastResponse = message;
        System.out.println(message);
    }

    /**
     * Prints a loading error message if tasks could not be loaded from file.
     */
    public void showLoadingError() {
        lastResponse = "Error while loading tasks: file could not be loaded.";
        System.out.println(lastResponse);
    }

    /**
     * Displays all tasks currently in the task list.
     *
     * @param tasks The TaskList containing tasks to display.
     */
    public void showTaskList(TaskList tasks) {
        if (tasks.size() == 0) {
            lastResponse = "There are no tasks in your list.";
            System.out.println(lastResponse);
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            try {
                Task t = tasks.getTask(i);
                String line = (i + 1) + ". [" + t.getTaskIcon() + "]["
                        + t.getStatusIcon() + "] " + t;
                System.out.println(line);
                if (i > 0) {
                    sb.append("\n");
                }
                sb.append(line);
            } catch (DukeException e) {
                showError(e.getMessage());
            }
        }
        lastResponse = sb.toString();
    }

    /**
     * Displays confirmation that a task has been added.
     *
     * @param task       The added task.
     * @param totalTasks The new total count of tasks.
     */
    public void showTaskAdded(Task task, int totalTasks) {
        String msg = "Got it. I've added this task:\n"
                + "  [" + task.getTaskIcon() + "][" + task.getStatusIcon() + "] " + task + "\n"
                + "Now you have " + totalTasks + " tasks in the list.";
        System.out.println(msg);
        lastResponse = msg;
    }

    /**
     * Displays confirmation that a task has been removed.
     *
     * @param task       The removed task.
     * @param totalTasks The new total count of tasks.
     */
    public void showTaskDeleted(Task task, int totalTasks) {
        String msg = "Noted. I've removed this task:\n"
                + "  [" + task.getTaskIcon() + "]["
                + task.getStatusIcon() + "] " + task + "\n"
                + "Now you have " + totalTasks + " tasks in the list.";
        System.out.println(msg);
        lastResponse = msg;
    }

    /**
     * Displays confirmation that a task has been marked as done.
     *
     * @param task The marked task.
     */
    public void showTaskMarked(Task task) {
        String msg = "Nice! I've marked this task as done:\n"
                + "  [" + task.getTaskIcon() + "][" + task.getStatusIcon() + "] " + task;
        System.out.println(msg);
        lastResponse = msg;
    }

    /**
     * Displays confirmation that a task has been marked as not done.
     *
     * @param task The unmarked task.
     */
    public void showTaskUnmarked(Task task) {
        String msg = "OK, I've marked this task as not done yet:\n"
                + "  [" + task.getTaskIcon() + "][" + task.getStatusIcon() + "] " + task;
        System.out.println(msg);
        lastResponse = msg;
    }

    /**
     * Displays the list of tasks matching a search keyword.
     *
     * @param matchingTasks The TaskList containing matched tasks.
     */
    public void showFoundTasks(TaskList matchingTasks) {
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:");
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < matchingTasks.size(); i++) {
            try {
                Task t = matchingTasks.getTask(i);
                String line = (i + 1) + ". [" + t.getTaskIcon() + "]["
                        + t.getStatusIcon() + "] " + t;
                System.out.println(line);
                sb.append("\n").append(line);
            } catch (DukeException e) {
                showError(e.getMessage());
            }
        }
        lastResponse = sb.toString();
    }

    /**
     * Returns the most recent message formatted by the UI.
     *
     * @return The latest response string.
     */
    public String getLastResponse() {
        return lastResponse;
    }
}
