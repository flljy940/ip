import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class Deadline extends Task {
    protected LocalDate dueDate;

    public Deadline(String task, String dueDate) throws DateTimeParseException {
        super(task);
        this.dueDate = LocalDate.parse(dueDate, Parser.DATE_FORMAT);
    }

    public Deadline(String task, String dueDate, boolean isDone) throws DateTimeParseException {
        super(task, isDone);
        this.dueDate = LocalDate.parse(dueDate, Parser.DATE_FORMAT);
    }

    public Deadline(String task, LocalDate dueDate) {
        super(task);
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
    public String fileSaveFormat() {
        return String.format("%s|%s",
                super.fileSaveFormat(),
                this.dueDate.format(Parser.DATE_FORMAT));
    }
}
