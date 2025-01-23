import java.util.Scanner;

class Color {
    public static final String none = "\033[m";
    public static final String green = "\033[32m";
    public static final String red = "\033[31m";
}

public class Rocky {
    private static final Scanner input = new Scanner(System.in);

    private static final String logo = "                    __                  \n" +
            "                   /\\ \\                 \n" +
            " _ __   ___     ___\\ \\ \\/'\\   __  __    \n" +
            "/\\`'__\\/ __`\\  /'___\\ \\ , <  /\\ \\/\\ \\   \n" +
            "\\ \\ \\//\\ \\L\\ \\/\\ \\__/\\ \\ \\\\`\\\\ \\ \\_\\ \\  \n" +
            " \\ \\_\\\\ \\____/\\ \\____\\\\ \\_\\ \\_\\/`____ \\ \n" +
            "  \\/_/ \\/___/  \\/____/ \\/_/\\/_/`/___/> \\\n" +
            "                                  /\\___/\n" +
            "                                  \\/__/ \n";

    private static final String introduction = "Hello, I'm Rocky\n" +
                                                "What can I do for you?";

    private static final TaskList tasks = new TaskList();

    private static void say(String message, String color) {
        // Color theme for Rocky
        System.out.println(color);

        System.out.println("<<<<<<<");
        System.out.println(message);
        System.out.println(">>>>>>>");

        // Reset to default
        System.out.println(Color.none);
    }

    /**
     * Log the new tasks in the same format
     *
     * @param task Task added to be logged
     */
    private static void logNewTask(Task task) {
        say("Got it. I've added this task:\n" +
                task.toString() +
                "\nNow you have " + tasks.size() + " tasks in the list.",
                Color.green
        );
    }

    private static void handleAction(String action) throws RockyException {
        String[] command = action.split(" ", 2);
        String instruction = command[0];
        String details = command.length > 1 ? command[1] : "";

        switch (instruction) {
            case "bye":
                say("Bye. Hope to see you again soon!", Color.green);
                System.exit(0);

            case "list":
                if (tasks.isEmpty()) {
                    say("Your list is empty! Please add some tasks!", Color.green);
                } else {
                    say(tasks.toString(), Color.green);
                }
                break;

            case "mark":
                try {
                    int mark_idx = Integer.parseInt(command[1]) - 1;
                    tasks.markTask(mark_idx);
                    say("Nice! I've marked this mark as done:\n" + tasks.getTask(mark_idx).toString(), Color.green);
                } catch (NumberFormatException e) {
                    throw new RockyException("Please provide a valid number" + "\uD83E\uDD7A");
                } catch (IndexOutOfBoundsException e) {
                    throw new RockyException(tasks.isEmpty()
                            ? "List is empty"
                            : (tasks.size() == 1
                                ? "There is only one task. Please choose task 1 :)"
                                : String.format("Please choose tasks from 1 to %d :))", tasks.size())));
                }
                break;

            case "unmark":
                try {
                    int unmark_idx = Integer.parseInt(command[1]) - 1;
                    tasks.unmarkTask(unmark_idx);
                    say("OK, I've marked this task as not done yet:\n" + tasks.getTask(unmark_idx).toString(), Color.green);
                } catch (NumberFormatException e) {
                    throw new RockyException("Please provide a valid number" + "\uD83E\uDD7A");
                } catch (IndexOutOfBoundsException e) {
                    throw new RockyException(tasks.isEmpty()
                                ? "List is empty"
                                : (tasks.size() == 1
                                        ? "There is only one task. Please choose task 1 :)"
                                        : String.format("Please choose tasks from 1 to %d :))", tasks.size())));
                }
                break;

            case "delete":
                try {
                    int dlt_idx = Integer.parseInt(command[1]) - 1;
                    tasks.deleteTask(dlt_idx);
                    say("Noted. I've removed this task from the list:\n" + tasks.getTask(dlt_idx).toString()
                                    + "\nNow you have " + tasks.size() + " tasks in your list", Color.green);
                } catch (NumberFormatException e) {
                    throw new RockyException("Please provide a valid number" + "\uD83E\uDD7A");
                } catch (IndexOutOfBoundsException e) {
                    throw new RockyException(tasks.isEmpty()
                            ? "List is empty"
                            : (tasks.size() == 1
                            ? "There is only one task. Please choose task 1 :)"
                            : String.format("Please choose tasks from 1 to %d :))", tasks.size())));
                }
                break;

            case "todo":
                if (details.isEmpty()) {
                    throw new RockyException("I don't know what u are trying to do. Add some task!");
                }
                Todo todo = new Todo(details);
                tasks.addTask(todo);
                logNewTask(todo);
                break;

            case "deadline":
                if (details.isEmpty()) {
                    throw new RockyException("I don't know what u are trying to do. Add some task!");
                }
                String[] parts = command[1].split(" /by ", 2);
                if (parts.length < 2) {
                    throw new RockyException("You must add the task and the due date with /by.");
                }
                Deadline deadline = new Deadline(parts[0], parts[1]);
                tasks.addTask(deadline);
                logNewTask(deadline);
                break;

            case "event":
                if (details.isEmpty()) {
                    throw new RockyException("I don't know what u are trying to do. Add some event!");
                }
                String[] pt = command[1].split(" /from ", 2);
                if (pt.length < 2 || !pt[1].contains(" /to ")) {
                    throw new RockyException("An event must have '/from' and '/to' clauses.");
                }
                String[] time = pt[1].split(" /to ", 2);
                Event event = new Event(pt[0], time[0], time[1]);
                tasks.addTask(event);
                logNewTask(event);
                break;

            default:
                throw new RockyException("Sry!! I don't know what that means" + "\uD83E\uDD7A");
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello from\n" + logo);
        say(introduction, Color.green);

        while (true) {
            String action = input.nextLine();
            try {
                handleAction(action);
            } catch (RockyException e) {
                say(e.getLocalizedMessage(), Color.red);
            } catch (Exception e) {
                say("Something went wrong! Please try again.", Color.red);
            }
        }
    }
}
