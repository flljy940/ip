package rocky.ui;

import rocky.task.Task;
import rocky.task.TaskList;

public class Ui {
    /**
     * Logs the list of Tasks in TaskList's string representation format
     * @param tasks List of tasks
     * @return formatted string
     */
    public String getListReport(TaskList tasks) {
        return String.format(
                "Here are the matching tasks in your list.\n" +
                        "Total %d found:\n"
                + tasks.toString(),
                tasks.size()
        );
    }

    /**
     * Logs the number of tasks in list
     *
     * @param taskCount task count to print
     * @return formatted string
     */
    public String getTaskCountReport(int taskCount) {
        return String.format("Now you have %d tasks in the list", taskCount);
    }

    /**
     * Logs the addition of tasks in the same format
     *
     * @param task task added to be logged
     * @return string representation of task added
     */
    public String getNewTaskResponse(Task task) {
        return String.format(
                "Added following task:\n"
                + "\t%s\n",
                task.toString()
        );
    }

    /**
     * Logs the deletion of task from the list
     *
     * @param task deleted Task
     * @param taskCount size of task list
     * @return formatted string
     */
    public String getDeletedTaskResponse(Task task, int taskCount) {
        return String.format(
                "Ok! Task removed:\n"
                + "\t%s\n" + "Now you have %d tasks in the list.",
                task.toString(), taskCount
        );
    }

    /**
     * Logs the task as done from the list
     *
     * @param task marked Task
     * @return formatted string
     */
    public String getMarkTaskResponse(Task task) {
        return "Nice! Marked as done:\n"
                + "\t" + task.toString();
    }

    /**
     * Logs the task as undone from the list
     *
     * @param task unmarked Task
     * @return formatted string
     */
    public String getUnmarkTaskResponse(Task task) {
        return "OK, marked as not done yet:\n"
                + "\t" + task.toString();
    }
}
