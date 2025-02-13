package rocky.task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Encapsulates behavior of Event type of Task
 */
public class Event extends Task {
    protected LocalDate date;
    protected LocalTime startTime;
    protected LocalTime endTime;

    /**
     * Date format for user input
     */
    protected static final DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("d/M/yyyy");

    /**
     * Time format for user input
     */
    protected static final DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HHmm");

    /**
     * Date format for output
     */
    protected static final DateTimeFormatter outputDateFmt = DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Time format for output
     */
    protected static final DateTimeFormatter outputTimeFmt = DateTimeFormatter.ofPattern("h:mm a");

    /**
     * Instantiates Event object with date string and time range string
     *
     * @param name name for Event
     * @param date date of Event (in d/M/yyyy format)
     * @param timeRange time range of Event (in HHmm-HHmm format)
     * @throws DateTimeParseException date format error
     */
    public Event(String name, String date, String timeRange) throws DateTimeParseException{
        super(name, 'E');
        parseDateTime(date, timeRange);
    }

    /**
     * Instantiates Event object with date string, time range string and status
     *
     * @param name name for Event
     * @param date date of Event (in d/M/yyyy format)
     * @param timeRange time range of Event (in HHmm-HHmm format)
     * @param isDone status of completion
     * @throws DateTimeParseException date format error
     */
    public Event(String name, String date, String timeRange, boolean isDone) throws DateTimeParseException {
        super(name, 'E', isDone);
        parseDateTime(date, timeRange);
    }

    private void parseDateTime(String date, String timeRange) throws DateTimeParseException {
        this.date = LocalDate.parse(date, dateFmt);
        String[] times = timeRange.split("-");
        if (times.length == 2) {
            this.startTime = LocalTime.parse(times[0], timeFmt);
            this.endTime = LocalTime.parse(times[1], timeFmt);
        } else {
            throw new DateTimeParseException("Invalid time range", timeRange, 0);
        }
    }

    /**
     * Returns the type, status, name, date, and time of the Event, formatted
     *
     * @return formatted string of the Event info
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() +
                String.format(" (on: %s at %s - %s)",
                        this.date.format(outputDateFmt),
                        this.startTime.format(outputTimeFmt),
                        this.endTime.format(outputTimeFmt));
    }

    /**
     * Parses a formatted string from file storage, then returns the Event object
     *
     * @return Event object represented by the string
     */
    @Override
    public String toFileSaveFormat() {
        return String.format("%s|%s %s-%s",
                super.toFileSaveFormat(),
                date.format(dateFmt),
                startTime.format(timeFmt),
                endTime.format(timeFmt));
    }
}
