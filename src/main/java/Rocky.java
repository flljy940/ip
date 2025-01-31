import java.io.IOException;
import java.util.Scanner;

public class Rocky {
    private static TaskList tasks;
    private static Storage storage;
    private static final Parser cmd = new Parser(new Scanner(System.in));
    private static final Ui ui = new Ui();

    private static int parseIndex(String input) throws RockyException {
        try {
            int index = Integer.parseInt(input) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new RockyException(tasks.isEmpty()
                        ? "List is empty"
                        : (tasks.size() == 1
                            ? "There is only one task. Please choose task 1 :)"
                            : String.format("Please choose tasks from 1 to %d :))", tasks.size())
                        )
                );
            }
            return index;
        } catch (NumberFormatException e) {
            throw new RockyException("Please provide a valid number\uD83E\uDD7A");
        }
    }

    /**
     * Perform corresponding action according to the command passed
     *
     * @param action Command from user
     * @throws RockyException General exception for invalid user command: invalid command, invalid arguments, etc.
     */
    private static void handleAction(Command action) throws RockyException {
        switch (action.getCmd()) {
            case "bye":
                Ui.say("Bye. Hope to see you again soon!");
                try {
                    storage.saveTasks(tasks);
                } catch (IOException e) {
                    ui.error("Can't save tasks to file");
                }
                System.exit(0);

            case "list":
                Ui.say(tasks.toString());
                break;

            case "mark":
                int mark_idx = Integer.parseInt(action.getArgs()) - 1;
                tasks.markTask(mark_idx);
                Ui.say("Nice! Marked as done:\n"
                        + tasks.getTask(mark_idx));
                break;

            case "unmark":
                int unmark_idx = Integer.parseInt(action.getArgs()) - 1;
                tasks.unmarkTask(unmark_idx);
                Ui.say("OK, marked as not done yet:\n"
                        + tasks.getTask(unmark_idx));
                break;

            case "delete":
                int dlt_idx = Integer.parseInt(action.getArgs()) - 1;
                Task deletedTask = tasks.deleteTask(dlt_idx);
                Ui.say("Noted. Task removed:\n"
                        + deletedTask
                        + "\nNow you have " + tasks.size() + " tasks in your list");
                break;

            case "todo":
                String todoName = action.getArgs();
                Todo todo = new Todo(todoName);
                tasks.addTask(todo);
                Ui.logNewTask(todo, tasks.size());
                break;

            case "deadline":
                String deadlineName = action.getArgs();
                String deadlineDate = action.getKwargs().get("by");
                Deadline deadline = new Deadline(deadlineName, deadlineDate);
                tasks.addTask(deadline);
                Ui.logNewTask(deadline, tasks.size());
                break;

            case "event":
                String eventName = action.getArgs();
                String[] eventTime = action.getKwargs().get("at").split(" ");
                String eventDate = eventTime[0];
                String timeRange = eventTime[1];
                Event event = new Event(eventName, eventDate, timeRange);
                tasks.addTask(event);
                Ui.logNewTask(event, tasks.size());
                break;

            default:
                throw new RockyException("Sry!! I don't know what that means\uD83E\uDD7A");
        }
    }

    public Rocky(String filename) {
        Ui.rocky();

        String introduction = "Hello, I'm Rocky\n" + "What can I do for you?";
        Ui.say(introduction);

        try {
            storage = new Storage(filename);
            tasks = storage.loadTasks();
        } catch (IOException | RockyException e) {
            ui.warning("[!] Error reading file - initializing task list as empty list");
            tasks = new TaskList();
        }
    }

    public void run() {
        while (true) {
            try {
                Command action = cmd.readAndParse();
                handleAction(action);
            } catch (RockyException e) {
                ui.error("Something went wrong! Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        new Rocky("data/tasks.txt").run();
    }
}
