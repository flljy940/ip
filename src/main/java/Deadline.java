import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected LocalDate dueDate;
    protected static final DateTimeFormatter inputFmt = DateTimeFormatter.ofPattern("d/M/yyyy");
    protected static final DateTimeFormatter outputFmt = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Deadline(String deadline, String dueDate) throws DateTimeParseException {
        super(deadline, 'D');
        this.dueDate = LocalDate.parse(dueDate, inputFmt);
    }

    public Deadline(String deadline, String dueDate, boolean isDone) throws DateTimeParseException {
        super(deadline, 'D', isDone);
        this.dueDate = LocalDate.parse(dueDate, inputFmt);
    }

    public Deadline(String deadline, LocalDate dueDate, boolean isDone) {
        super(deadline, 'D', isDone);
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.dueDate.format(outputFmt) + ")";
    }

    @Override
    public String fileSaveFormat() {
        return String.format("%s|%s",
                super.fileSaveFormat(),
                this.dueDate.format(inputFmt));
    }
}
