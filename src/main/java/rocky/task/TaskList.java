package rocky.task;

import java.util.ArrayList;
import java.util.List;

import rocky.exception.RockyException;

public class TaskList {
    private List<Task> list;

    public TaskList() {
        this(new ArrayList<>());
    }

    public TaskList(List<Task> list) {
        this.list = list;
    }

    public void addTask(Task task) {
        this.list.add(task);
    }

    public void markTask(int index) throws RockyException {
        checkIndex(index);
        this.list.get(index).markAsDone();
    }

    public void unmarkTask(int index) throws RockyException {
        checkIndex(index);
        this.list.get(index).unmarkAsDone();
    }

    public Task deleteTask(int index) throws RockyException {
        checkIndex(index);
        return this.list.remove(index);
    }

    public Task getTask(int index) throws RockyException {
        checkIndex(index);
        return this.list.get(index);
    }

    private void checkIndex(int index) throws RockyException {
        if (index >= 0 && index < this.size()) {
            return;
        }

        throw new RockyException(
                this.size() == 0
                        ? "List is empty"
                        : String.format("Please choose tasks from 1 to %d", this.size())
        );
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

    public String listFileSaveFormat() {
        String result = "";

        for (int i = 0; i < this.size(); i++) {
            result += this.list.get(i).toFileSaveFormat();

            if (i != this.size() - 1) {
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