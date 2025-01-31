import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    protected LocalDate date;
    protected LocalTime startTime;
    protected LocalTime endTime;
    protected static final DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("d/M/yyyy");
    protected static final DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HHmm");
    protected static final DateTimeFormatter outputDateFmt = DateTimeFormatter.ofPattern("MMM dd yyyy");
    protected static final DateTimeFormatter outputTimeFmt = DateTimeFormatter.ofPattern("h:mm a");

    public Event(String name, String date, String timeRange) throws DateTimeParseException{
        super(name, 'E');
        this.date = LocalDate.parse(date, dateFmt);

        String[] times = timeRange.split("-");
        if (times.length == 2) {
            this.startTime = LocalTime.parse(times[0], timeFmt);
            this.endTime = LocalTime.parse(times[1], timeFmt);
        } else {
            throw new DateTimeParseException("Invalid time range", timeRange, 0);
        }
    }

    public Event(String name, String date, String timeRange, boolean isDone) throws DateTimeParseException {
        super(name, 'E', isDone);
        this.date = LocalDate.parse(date, dateFmt);

        String[] times = timeRange.split("-");
        if (times.length == 2) {
            this.startTime = LocalTime.parse(times[0], timeFmt);
            this.endTime = LocalTime.parse(times[1], timeFmt);
        } else {
            throw new DateTimeParseException("Invalid time range", timeRange, 0);
        }
    }

    public Event(String name, LocalDate date, LocalTime startTime, LocalTime endTime) {
        super(name);
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Event(String name, LocalDate date, LocalTime startTime, LocalTime endTime, boolean isDone) {
        super(name, 'E', isDone);
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() +
                String.format(" (on: %s at %s - %s)",
                        this.date.format(outputDateFmt),
                        this.startTime.format(outputTimeFmt),
                        this.endTime.format(outputTimeFmt));
    }

    @Override
    public String fileSaveFormat() {
        return String.format("%s|%s %s-%s",
                super.fileSaveFormat(),
                date.format(dateFmt),
                startTime.format(timeFmt),
                endTime.format(timeFmt));
    }
}
