package duke.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import duke.exception.DukeException;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");

    private LocalDateTime deadlineDate;

    /**
     * Constructs a Deadline task with the given description and date string.
     *
     * @param name     The description of the deadline task.
     * @param deadline The deadline date/time string (yyyy-MM-dd or yyyy-MM-dd HHmm).
     * @throws DukeException If the date/time string format is invalid.
     */
    public Deadline(String name, String deadline) throws DukeException {
        super(name);
        this.deadlineDate = parseDateTime(deadline);
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
        return super.toString() + " (by: " + deadlineDate.format(OUTPUT_FORMAT) + ")";
    }
}
