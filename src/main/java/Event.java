public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String event, String from, String to) {
        super(event);
        this.from = from;
        this.to = to;
    }

    public Event(String event, String from, String to, boolean isDone) {
        super(event, isDone);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + String.format(" (from: %s to: %s)", from, to);
    }
}
