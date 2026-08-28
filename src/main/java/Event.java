import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task with a start and end time.
 */
public class Event extends Task {
    private LocalDate startDate;
    private LocalDate endDate;

    public Event(String name, String startTime, String endTime) throws DukeException {
        super(name);
        try {
            this.startDate = LocalDate.parse(startTime);
            this.endDate = LocalDate.parse(endTime);
        } catch (DateTimeParseException e) {
            throw new DukeException("Invalid date format. Please use yyyy-MM-dd (e.g., 2019-10-15).");
        }
    }

    /**
     * Formats the Event for saving to a file.
     *
     * @return A pipe-separated string representing the Event's state.
     */
    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | " + startDate + " | " + endDate;
    }

    @Override
    public String getTaskIcon() {
        return "E";
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + startDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + " to: " + endDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
}