import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private List<Task> list = new ArrayList<>();

    public void addTask(Task task) {
        this.list.add(task);
    }

    @Override
    public String toString() {
        String res = "";

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