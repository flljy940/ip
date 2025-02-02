package rocky.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import rocky.command.Parser;

public class Deadline extends Task {
    protected LocalDate dueDate;

    public Deadline(String task, String dueDate) throws DateTimeParseException {
        super(task, 'D');
        this.dueDate = LocalDate.parse(dueDate, Parser.DATE_FORMAT);
    }

    public Deadline(String task, String dueDate, boolean isDone) throws DateTimeParseException {
        super(task, 'D', isDone);
        this.dueDate = LocalDate.parse(dueDate, Parser.DATE_FORMAT);
    }

    public Deadline(String task, LocalDate dueDate) {
        super(task, 'D');
        this.dueDate = dueDate;
    }

    public Deadline(String task, LocalDate dueDate, boolean isDone) {
        super(task, 'D', isDone);
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return "[D]" + super.toString() + " (by: " + this.dueDate.format(fmt) + ")";
    }

    @Override
    public String toFileSaveFormat() {
        return String.format("%s|%s",
                super.toFileSaveFormat(),
                this.dueDate.format(Parser.DATE_FORMAT));
    }
}
