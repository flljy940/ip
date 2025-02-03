package rocky.task;

/**
 * Encapsulates behavior of Todo type of Task
 */
public class Todo extends Task {
    /**
     * Instantiate Todo object
     *
     * @param todo name of Todo
     */
    public Todo(String todo) {
        super(todo, 'T');
    }

    /**
     * Instantiate Todo object with status
     *
     * @param todo name of Todo
     * @param isDone status of completion
     */
    public Todo(String todo, boolean isDone) {
        super(todo, 'T', isDone);
    }

    /**
     * Returns the type, status, name, date, and time of the Todo, formatted
     *
     * @return formatted string of the Todo info
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Parses a formatted string from file storage, then returns the Todo object
     *
     * @return Todo object represented by the string
     */
    @Override
    public String toFileSaveFormat() {
        return String.format("%s|", super.toFileSaveFormat());
    }
}
