public class Todo extends Task {

    public Todo(String todo) {
        super(todo);
    }

    public Todo(String todo, boolean isDone) {
        super(todo, isDone);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
