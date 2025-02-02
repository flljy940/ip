package rocky.task;

import rocky.exception.RockyException;

public class Task {
    protected String task;
    protected boolean isDone;
    private final char type;

    public Task(String task) {
        this.task = task;
        this.type = ' ';
        this.isDone = false;
    }

    public Task(String task, boolean isDone) {
        this.task = task;
        this.type = ' ';
        this.isDone = isDone;
    }

    public Task(String task, char type) {
        this.task = task;
        this.type = type;
        this.isDone = false;
    }

    public Task(String task, char type, boolean isDone) {
        this.task = task;
        this.type = type;
        this.isDone = isDone;
    }

    public String getTask() {
        return this.task;
    }

    public char getTaskType() {
        return this.type;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public String getStatusIcon() {
        return (this.isDone ? "X" : " ");
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void unmarkAsDone() {
        this.isDone = false;
    }

    public String toFileSaveFormat() {
        return String.format("%c|%c|%s",
                this.type,
                this.isDone() ? '1' : '0',
                this.task);
    }

    public static Task parseFileSaveFormat(String fmt) throws RockyException {
        String[] taskDetails = fmt.split("\\|");

        if (taskDetails.length < 3) {
            throw new RockyException("Invalid task format");
        }

        char taskType = taskDetails[0].charAt(0);
        boolean isDone = taskDetails[1].equals("1");
        String taskName = taskDetails[2];

        Task task = null;

        switch (taskType) {
        case 'T':
            task = new Todo(taskName, isDone);
            break;

        case 'D':
            try {
                String dueDate = taskDetails[3];
                task = new Deadline(taskName, dueDate, isDone);
            } catch (IndexOutOfBoundsException e) {
                throw new RockyException("Invalid deadline format");
            }
            break;

        case 'E':
            try {
                String[] eventDetails = taskDetails[3].split(" ");
                String date = eventDetails[0];
                String timeRange = eventDetails[1];
                task = new Event(taskName, date, timeRange, isDone);
            } catch (IndexOutOfBoundsException e) {
                throw new RockyException("Invalid event format");
            }
            break;

        default:
//            task = new Task(taskName, isDone);
//            break;
            throw new RockyException("Unknown task type");
        }

        return task;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", this.getStatusIcon(), this.task);
    }
}
