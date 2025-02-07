package rocky.ui;

import rocky.task.Task;
import rocky.task.TaskList;

public class Ui {
    public String getListReport(TaskList tasks) {
        return String.format(
                "Total %d found:\n"
                + tasks.toString(),
                tasks.size()
        );
    }

    public String getTaskCountReport(int taskCount) {
        return String.format("Now you have %d tasks in the list", taskCount);
    }

    /**
     * Log the addition of tasks in the same format
     *
     * @param task task added to be logged
     */
    public String getNewTaskResponse(Task task) {
        return String.format(
                "Added following task:\n"
                + "\t%s\n",
                task.toString()
        );
    }

    public String getDeletedTaskResponse(Task task) {
        return String.format(
                "Removed this task:\n"
                + "\t%s\n",
                task.toString()
        );
    }

    public String getUpdateTaskResponse(Task task) {
        return "Updated the following task\n"
                + "\t" + task.toString();
    }
}
