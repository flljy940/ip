package rocky.ui;

import rocky.task.Task;

public class Ui {
    /**
     * Color class to display colored text in the terminal
     */
    protected static class Color {
        public static final String NONE = "\033[m";
        public static final String GREEN = "\033[32m";
        public static final String RED = "\033[31m";
        public static final String YELLOW = "\033[33m";
    }

    /**
     * Logo string
     */
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

    /**
     * Introduction string
     */
    private static final String introduction = "Hello, I'm Rocky\n" +
                                                 "What can I do for you?";

    /**
     * Prints logo containing introduction
     */
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

    /**
     * Method for Rocky to print error messages
     *
     * @param message error message to print
     */
    public void error(String message) {
        say(Color.RED + message);
    }

    /**
     * Method for Rocky to print warning messages
     *
     * @param message warning message to print
     */
    public void warning(String message) {
        say(Color.YELLOW + message);
    }

    /**
     * Log the new tasks in the same format
     *
     * @param task task added to be logged
     * @param taskCount number of tasks
     */
    public static void logNewTask(Task task, int taskCount) {
        say("Got it. I've added this task:\n" +
                        task.toString() +
                        "\nNow you have " + taskCount + " tasks in the list."
        );
    }
}
