/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    private String deadline;

    public Deadline(String name, String deadline) {
        super(name);
        this.deadline = deadline;
    }

    /**
     * Formats the Deadline for saving to a file.
     *
     * @return A pipe-separated string representing the Deadline's state.
     */
    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | " + deadline;
    }

    @Override
    public String getTaskIcon() {
        return "D";
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + deadline + ")";
    }
}