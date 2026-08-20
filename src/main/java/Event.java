public class Event extends Task{
    private String startTime;
    private String endTime;

    public Event(String name, String startTime, String endTime) {
        super(name);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTaskIcon() {
        return "E";
    }

    @Override
    public String toString() {
        return super.toString() + "(from: " + startTime + " to: " + endTime + ")";
    }
}