package rocky.task;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> list = new ArrayList<>();

    public void addTask(Task task) {
        this.list.add(task);
    }

    public void markTask(int index) {
        list.get(index).markAsDone();
    }

    public void unmarkTask(int index) {
        list.get(index).unmarkAsDone();
    }

    public Task deleteTask(int index) {
        return this.list.remove(index);
    }

    public Task getTask(int index) {
        return this.list.get(index);
    }

    public List<Task> getTasks() {
        return this.list;
    }

    public int size() {
        return this.list.size();
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public String tasksFileSaveFormat() {
        String result = "";

        for (int i = 0; i < this.list.size(); i++) {
            result += this.getTask(i).fileSaveFormat();

            if (i != this.list.size() - 1) {
                result += "\n";
            }
        }
        return result;
    }

    @Override
    public String toString() {
        if (this.list.isEmpty()) {
            return "Your task list is empty. Please add at least one task.";
        }

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