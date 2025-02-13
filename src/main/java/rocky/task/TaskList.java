package rocky.task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import rocky.exception.RockyException;

/**
 * Class to encapsulate the behavior of a List of Tasks
 */
public class TaskList {
    /**
     * Main data structure to store Task objects
     */
    private final List<Task> list;

    /**
     * Instantiates an empty TaskList
     */
    public TaskList() {
        this(new ArrayList<>());
    }

    /**
     * Instantiates TaskList with List of Tasks
     *
     * @param list list of Tasks
     */
    public TaskList(List<Task> list) {
        this.list = list;
    }

    /**
     * Adds a Task to existing TaskList
     *
     * @param task Task to add
     */
    public void addTask(Task task) {
        this.list.add(task);
    }

    /**
     * Filter TaskList for  Tasks whose names contain the search string
     *
     * @param search substring to search for
     * @return filtered TaskList
     */
    public TaskList searchTasks(String search) {
        return new TaskList(
                this.list.stream()
                        .filter(task -> task.getTask().contains(search))
                        .collect(Collectors.toList())
        );
    }

    /**
     * Marks the Task at specified index (marked as done)
     *
     * @param index index of Task to mark
     * @throws RockyException index out of range
     */
    public void markTask(int index) throws RockyException {
        checkIndex(index);
        this.list.get(index).markAsDone();
    }

    /**
     * Unmarks the Task at specified index (marked as undone)
     * @param index index of task
     * @throws RockyException exception if index is out of bound
     */
    public void unmarkTask(int index) throws RockyException {
        checkIndex(index);
        this.list.get(index).markAsUndone();
    }

    /**
     * Deletes a Task from the existing list
     *
     * @param index index of Task to delete
     * @return the removed Task
     * @throws RockyException index out of range
     */
    public Task deleteTask(int index) throws RockyException {
        checkIndex(index);
        return this.list.remove(index);
    }

    /**
     * Retrieves Task at index
     *
     * @param index index of Task to get
     * @return requested Task
     * @throws RockyException index out of range
     */
    public Task getTask(int index) throws RockyException {
        checkIndex(index);
        return this.list.get(index);
    }

    /**
     * Checks if index is in range of current  list
     *
     * @param index index to check
     * @throws RockyException index out of range
     */
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

    /**
     *
     * @return size of TaskList
     */
    public int size() {
        return this.list.size();
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    /**
     * Returns a string, where each line is a Task formatted for saving to file
     *
     * @return formatted string of Tasks for saving to file
     */
    public String listFileSaveFormat() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < this.size(); i++) {
            result.append(this.list.get(i).toFileSaveFormat());

            if (i != this.size() - 1) {
                result.append("\n");
            }
        }
        return result.toString();
    }

    /**
     * Returns String as an ordered list with the numbering and status of the Task
     *
     * @return string representation of TaskList
     */
    @Override
    public String toString() {
        if (this.list.isEmpty()) {
            return "Your task list is empty. Please add at least one task.";
        }

        StringBuilder res = new StringBuilder("Here are the tasks in your list:\n");

        for (int i = 0; i < this.list.size(); i++) {
            res.append(String.format(
                    "%d. %s",
                    i + 1,
                    this.list.get(i)
            ));

            if (i != this.list.size() - 1) {
                res.append("\n");
            }
        }
        return res.toString();
    }
}