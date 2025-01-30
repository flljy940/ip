import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected LocalDate by;

    public Deadline(String deadline, String by) throws DateTimeParseException {
        super(deadline);
        this.by = LocalDate.parse(by);
    }

    public Deadline(String deadline, String by, boolean isDone) throws DateTimeParseException {
        super(deadline, 'D', isDone);
        this.by = LocalDate.parse(by);
    }

    public Deadline(String deadline, LocalDate by, boolean isDone) {
        super(deadline, 'D', isDone);
        this.by = by;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd yyyy");

        return "[D]" + super.toString() + " (by: " + this.by.format(fmt) + ")";
    }

    @Override
    public String fileSaveFormat() {
        return String.format("%s|%s",
                super.fileSaveFormat(),
                this.by);
    }
}
