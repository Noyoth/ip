/**
 * Represents an event task with a start and end time.
 */
public class Event extends Task {
    private String startTime;
    private String endTime;

    public Event(String name, String startTime, String endTime) {
        super(name);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Formats the Event for saving to a file.
     *
     * @return A pipe-separated string representing the Event's state.
     */
    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | " + startTime + "-" + endTime;
    }

    @Override
    public String getTaskIcon() {
        return "E";
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + startTime + " to: " + endTime + ")";
    }
}