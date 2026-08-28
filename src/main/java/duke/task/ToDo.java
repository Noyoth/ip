package duke.task;

/**
 * Represents a to-do task.
 */
public class ToDo extends Task {
    /**
     * Constructs a ToDo task with the given description.
     *
     * @param name The description of the to-do task.
     */
    public ToDo(String name) {
        super(name);
    }

    @Override
    public String getTaskIcon() {
        return "T";
    }
}
