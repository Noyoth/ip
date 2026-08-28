public class Deadline extends Task{
    private String deadline;

    public Deadline(String name, String deadline) {
        super(name);
        this.deadline = deadline;
    }

    @Override

    /**
     * Formats the Deadline for saving to a file.
     *
     * @return A pipe-separated string representing the Deadline's state.
     */
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