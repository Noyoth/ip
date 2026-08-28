import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task with a start and end time.
 */
public class Event extends Task {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");

    public Event(String name, String startTime, String endTime) throws DukeException {
        super(name);
        this.startDate = parseDateTime(startTime);
        this.endDate = parseDateTime(endTime);
    }

    private LocalDateTime parseDateTime(String input) throws DukeException {
        try {
            if (input.contains("T")) {
                return LocalDateTime.parse(input);
            } else if (input.contains(" ")) {
                return LocalDateTime.parse(input, INPUT_FORMAT);
            } else {
                return LocalDate.parse(input).atStartOfDay();
            }
        } catch (DateTimeParseException e) {
            throw new DukeException(
                    "Invalid date format. Please use yyyy-MM-dd or yyyy-MM-dd HHmm (e.g., 2019-10-15 1800).");
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
        return super.toString() + " (from: " + startDate.format(OUTPUT_FORMAT)
                + " to: " + endDate.format(OUTPUT_FORMAT) + ")";
    }
}
