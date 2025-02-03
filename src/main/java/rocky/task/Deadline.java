package rocky.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import rocky.command.Parser;

/**
 * Encapsulates behavior of Deadline type of Task
 */
public class Deadline extends Task {
    protected LocalDate dueDate;

    /**
     * Instantiates Deadline object with date string
     *
     * @param task name for Deadline task
     * @param dueDate date of Deadline (in d/M/yyyy format)
     * @throws DateTimeParseException date format error
     */
    public Deadline(String task, String dueDate) throws DateTimeParseException {
        super(task, 'D');
        this.dueDate = LocalDate.parse(dueDate, Parser.DATE_FORMAT);
    }

    /**
     * Instantiates Deadline object with date string, and status
     *
     * @param task name for Deadline task
     * @param dueDate date of Deadline (in d/M/yyyy format)
     * @param isDone status of completion
     * @throws DateTimeParseException date format error
     */
    public Deadline(String task, String dueDate, boolean isDone) throws DateTimeParseException {
        super(task, 'D', isDone);
        this.dueDate = LocalDate.parse(dueDate, Parser.DATE_FORMAT);
    }

    /**
     * Instantiates Deadline object
     *
     * @param task name for Deadline task
     * @param dueDate date of Deadline
     */
    public Deadline(String task, LocalDate dueDate) {
        super(task, 'D');
        this.dueDate = dueDate;
    }

    /**
     * Instantiates Deadline object with status
     *
     * @param task name for Deadline task
     * @param dueDate date of Deadline
     * @param isDone status of completion
     */
    public Deadline(String task, LocalDate dueDate, boolean isDone) {
        super(task, 'D', isDone);
        this.dueDate = dueDate;
    }

    /**
     * Returns the type, status, name, and date of the Deadline, formatted
     *
     * @return formatted string of the Deadline info
     */
    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return "[D]" + super.toString() + " (by: " + this.dueDate.format(fmt) + ")";
    }

    /**
     * Parses a formatted string from file storage, then returns the Deadline object
     *
     * @return Deadline object represented by the string
     */
    @Override
    public String toFileSaveFormat() {
        return String.format("%s|%s",
                super.toFileSaveFormat(),
                this.dueDate.format(Parser.DATE_FORMAT));
    }
}
