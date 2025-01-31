package rocky.task;

public class Todo extends Task {

    public Todo(String todo) {
        super(todo, 'T');
    }

    public Todo(String todo, boolean isDone) {
        super(todo, 'T', isDone);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String fileSaveFormat() {
        return String.format("%s|", super.fileSaveFormat());
    }
}
