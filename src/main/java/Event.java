import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    public Event(String event, String from, String to) throws DateTimeParseException{
        super(event);
        this.from = LocalDate.parse(from);
        this.to = LocalDate.parse(to);
    }

    public Event(String event, String from, String to, boolean isDone) throws DateTimeParseException {
        super(event, 'E', isDone);
        this.from = LocalDate.parse(from);
        this.to = LocalDate.parse(to);
    }

    public Event(String name, LocalDate from, LocalDate to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    public Event(String name, LocalDate from, LocalDate to, boolean isDone) {
        super(name, 'E', isDone);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd yyyy");

        return "[E]" + super.toString() + String.format(" (from: %s to: %s)", this.from.format(fmt), this.to.format(fmt));
    }
}
