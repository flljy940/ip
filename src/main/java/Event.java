public class Event extends Task {
    protected String dateRange;

    public Event(String event, String dateRange) {
        super(event);
        this.dateRange = dateRange;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(from: " + dateRange + ")";
    }
}
