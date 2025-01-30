public class Deadline extends Task {
    protected String by;

    public Deadline(String deadline, String by) {
        super(deadline);
        this.by = by;
    }

    public Deadline(String deadline, String by, boolean isDone) {
        super(deadline, 'D', isDone);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
