/**
 * Represents a generic task with a name and completion status.
 */
public class Task {
    private String name;
    private boolean isDone;

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
        return (isDone ? "X" : " "); // mark done task with X
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
     *
     * @return The task itself.
     */
    public Task markAsDone() {
        this.isDone = true;
        return this;
    }

    /**
     * Marks the task as not done.
     *
     * @return The task itself.
     */
    public Task markAsNotDone() {
        this.isDone = false;
        return this;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
