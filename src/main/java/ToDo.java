/**
 * Represents a to-do task.
 */
public class ToDo extends Task {
    public ToDo(String name) {
        super(name);
    }

    @Override
    public String getTaskIcon() {
        return "T";
    }
}
