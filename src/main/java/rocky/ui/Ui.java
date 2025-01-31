package rocky.ui;

import rocky.task.Task;

public class Ui {
    protected static class Color {
        public static final String NONE = "\033[m";
        public static final String GREEN = "\033[32m";
        public static final String RED = "\033[31m";
        public static final String YELLOW = "\033[33m";
    }

    private static final String logo =
                    "                    __                  \n" +
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

    public static void rocky() {
        System.out.println("Hello from\n" + logo);
    }

    /**
     * Method for Rocky to print message in a formatted style
     *
     * @param message Message to print
     */
    public static void say(String message) {
        String defaultColor = Color.GREEN;
        System.out.println(defaultColor);

        System.out.println("<<<<<<<");
        System.out.println(message);
        // Default back to green to allow different colored message
        System.out.println(defaultColor + ">>>>>>>");

        // Reset to default
        System.out.println(Color.NONE);
    }

    public void error(String message) {
        say(Color.RED + message);
    }

    public void warning(String message) {
        say(Color.YELLOW + message);
    }

    /**
     * Log the new tasks in the same format
     *
     * @param task Task added to be logged
     */
    public static void logNewTask(Task task, int taskCount) {
        say("Got it. I've added this task:\n" +
                        task.toString() +
                        "\nNow you have " + taskCount + " tasks in the list."
        );
    }
}
