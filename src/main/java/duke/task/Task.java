package duke.task;

/**
 * Represents a generic task with a name and completion status.
 */
public class Task {
    private final String name;
    private boolean isDone;

    /**
     * Constructs a new Task with the given name.
     *
     * @param name The description of the task.
     */
    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    /**
     * Returns the status icon for the task.
     *
     * @return "X" if done, " " otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Formats the Task for saving to a file.
     *
     * @return A pipe-separated string representing the Task's state.
     */
    public String toFileFormat() {
        return getTaskIcon() + " | " + (isDone ? "1" : "0") + " | " + name;
    }

    /**
     * Returns the task type icon.
     *
     * @return A string representing the task type.
     */
    public String getTaskIcon() {
        return " ";
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
