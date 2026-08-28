public class Task {
    private String name;
    private boolean isDone;

    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

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

    public String getTaskIcon() {
        return " ";
    }

    public Task markAsDone() {
        this.isDone = true;
        return this;
    }

    public Task markAsNotDone() {
        this.isDone = false;
        return this;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
