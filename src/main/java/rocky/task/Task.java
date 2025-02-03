package rocky.task;

import rocky.exception.RockyException;

/**
 * Class to encapsulate basic properties and methods of a Task
 */
public class Task {
    /**
     * Name of Task
     */
    protected String task;

    /**
     * Status of completion of Task
     */
    protected boolean isDone;

    /**
     * Type of Task
     */
    private final char type;

    /**
     * Instantiates Task with name and status
     * Default type is ' ' and default status is false
     *
     * @param task name of Task
     */
    public Task(String task) {
        this.task = task;
        this.type = ' ';
        this.isDone = false;
    }

    /**
     * Instantiates Task with name and type
     * Default type is ' '
     *
     * @param task name of Task
     * @param isDone status of completion of Task
     */
    public Task(String task, boolean isDone) {
        this.task = task;
        this.type = ' ';
        this.isDone = isDone;
    }

    /**
     * Instantiates Task with name and type
     * Default status is false
     *
     * @param task name of Task
     * @param type type of Task
     */
    public Task(String task, char type) {
        this.task = task;
        this.type = type;
        this.isDone = false;
    }

    /**
     * Instantiates Task with name, tag, and status
     *
     * @param task name of Task
     * @param type type of Task
     * @param isDone status of completion of Task
     */
    public Task(String task, char type, boolean isDone) {
        this.task = task;
        this.type = type;
        this.isDone = isDone;
    }

    /**
     * Getter for the name of the Task
     *
     * @return name of the Task
     */
    public String getTask(int index) {
        return this.task;
    }

    /**
     * Getter for the type of the Task
     *
     * @return type of the Task
     */
    public char getTaskType() {
        return this.type;
    }

    /**
     * Returns the state (isDone) of the Task
     *
     * @return rocky.task is done or not
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Getter of the icon shown in the checkbox defining the status of the task
     *
     * @return "X" for completed Task and "" for uncompleted Task
     */
    public String getStatusIcon() {
        return (this.isDone ? "X" : " ");
    }

    /**
     * Marks the Task (marked as done)
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Unmarks the Task (marked as undone)
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Returns Task info in a standard format for saving in file
     *
     * @return formatted string for saving Task
     */
    public String toFileSaveFormat() {
        return String.format("%c|%c|%s",
                this.type,
                this.isDone() ? '1' : '0',
                this.task);
    }

    /**
     * Parses a formatted string from file storage, then returns the Task object
     *
     * @param fmt certain format for the string representation
     * @return Task object represented by the string
     * @throws RockyException format error
     */
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

    /**
     * Returns the type, status, and name of the Task, formatted
     *
     * @return formatted string representation of the Task info
     */
    @Override
    public String toString() {
        return String.format("[%s] %s", this.getStatusIcon(), this.task);
    }
}
