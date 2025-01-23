class Color {
    public static final String none = "\033[m";

    public static final String green = "\033[32m";
    public static final String purple = "\033[35m";
}

public class Rocky {
    private static final String logo = "██████╗  ██████╗  ██████╗██╗  ██╗██╗   ██╗\n"
                                     + "██╔══██╗██╔═══██╗██╔════╝██║ ██╔╝╚██╗ ██╔╝\n"
                                     + "██████╔╝██║   ██║██║     █████╔╝  ╚████╔╝\n"
                                     + "██╔══██╗██║   ██║██║     ██╔═██╗   ╚██╔╝\n"
                                     + "██║  ██║╚██████╔╝╚██████╗██║  ██╗   ██║\n"
                                     + "╚═╝  ╚═╝ ╚═════╝  ╚═════╝╚═╝  ╚═╝   ╚═╝\n";

    private static final String introduction = "Hello, I'm Rocky\n" +
                                                "What can I do for you?";

    private static void say(String message) {
        System.out.println(Color.green);

        System.out.println("<<<<<<<");
        System.out.println(message);
        System.out.println(">>>>>>>");

        System.out.println(Color.none);
    }

    public static void main(String[] args) {
        System.out.println("Hello from\n" + logo);
        say(introduction);

        say("Bye. Hope to see you again soon!");
        System.exit(0);
    }
}
