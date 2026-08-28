import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    private LocalDate deadlineDate;

    public Deadline(String name, String deadline) throws DukeException {
        super(name);
        try {
            this.deadlineDate = LocalDate.parse(deadline);
        } catch (DateTimeParseException e) {
            throw new DukeException("Invalid date format. Please use yyyy-MM-dd (e.g., 2019-10-15).");
        }
    }

    /**
     * Formats the Deadline for saving to a file.
     *
     * @return A pipe-separated string representing the Deadline's state.
     */
    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | " + deadlineDate;
    }

    @Override
    public String getTaskIcon() {
        return "D";
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + deadlineDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
}