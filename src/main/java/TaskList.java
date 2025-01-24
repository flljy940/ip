import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private List<Task> list = new ArrayList<>();

    public void addTask(Task task) {
        this.list.add(task);
    }

    public void markTask(int index) {
        list.get(index).markAsDone();
    }

    public void unmarkTask(int index) {
        list.get(index).unmarkAsDone();
    }

    public void deleteTask(int index) {
        this.list.remove(index);
    }

    public Task getTask(int index) {
        return this.list.get(index);
    }

    public int size() {
        return this.list.size();
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public String toString() {
        String res = "Here are the tasks in your list:\n";

        for (int i = 0; i < this.list.size(); i++) {
            res += String.format(
                    "%d. %s",
                    i + 1,
                    this.list.get(i)
            );

            if (i != this.list.size() - 1) {
                res += "\n";
            }
        }
        return res;
    }
}