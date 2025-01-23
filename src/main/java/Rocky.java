import java.util.Scanner;

class Color {
    public static final String none = "\033[m";
    public static final String green = "\033[32m";
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

    private static void say(String message) {
        // Color theme for Rocky
        System.out.println(Color.green);

        System.out.println("<<<<<<<");
        System.out.println(message);
        System.out.println(">>>>>>>");

        // Reset to default
        System.out.println(Color.none);
    }

    public static void main(String[] args) {
        System.out.println("Hello from\n" + logo);
        say(introduction);

        while (true) {
            String action = input.nextLine();
            String[] command = action.split(" ");

            switch (command[0]) {
                case "bye":
                    say("Bye. Hope to see you again soon!");
                    System.exit(0);
                case "list":
                    say(tasks.toString());
                    break;
                case "mark":
                    int mark_idx = Integer.parseInt(command[1]) - 1;
                    tasks.markTask(mark_idx);
                    say("Nice! I've marked this mark as done:\n" + tasks.getTask(mark_idx).toString());
                    break;
                case "unmark":
                    int unmark_idx = Integer.parseInt(command[1]) - 1;
                    tasks.unmarkTask(unmark_idx);
                    say("OK, I've marked this task as not done yet:\n" + tasks.getTask(unmark_idx).toString());
                    break;
                default:
                    say("added: " + action);
                    tasks.addTask(new Task(action));
            }
        }
    }
}
