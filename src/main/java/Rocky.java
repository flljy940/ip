import java.util.Scanner;

class Color {
    public static final String none = "\033[m";
    public static final String green = "\033[32m";
}

public class Rocky {
    private static final Scanner input = new Scanner(System.in);

    private static final String logo = "██████╗  ██████╗  ██████╗██╗  ██╗██╗   ██╗\n"
                                     + "██╔══██╗██╔═══██╗██╔════╝██║ ██╔╝╚██╗ ██╔╝\n"
                                     + "██████╔╝██║   ██║██║     █████╔╝  ╚████╔╝\n"
                                     + "██╔══██╗██║   ██║██║     ██╔═██╗   ╚██╔╝\n"
                                     + "██║  ██║╚██████╔╝╚██████╗██║  ██╗   ██║\n"
                                     + "╚═╝  ╚═╝ ╚═════╝  ╚═════╝╚═╝  ╚═╝   ╚═╝\n";

    private static final String introduction = "Hello, I'm Rocky\n" +
                                                "What can I do for you?";

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
            switch (action) {
                case "bye":
                    say("Bye. Hope to see you again soon!");
                    System.exit(0);
                default:
                    say(action);
            }
        }
    }
}
