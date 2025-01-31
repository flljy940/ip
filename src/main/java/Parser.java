import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy");

    private static final String[][] COMMANDS = {
            { "bye", "bye", "bye" },
            { "list", "list", "list" },
            { "mark", "mark (\\d+)", "mark <task number>" },
            { "unmark", "unmark (\\d+)", "unmark <task number>" },
            { "todo", "todo (.*)", "todo <description>" },
            { "deadline",
                    "deadline (.*) /(by) ((?:[1-9]|[12][0-9]|3[01])/(?:[1-9]|1[0-2])/[0-9]{4})",
                    "deadline <description> /by <d/M/yyyy>" },
            { "event",
                    "event (.*) /(on) ((?:[1-9]|[12][0-9]|3[01])/(?:[1-9]|1[0-2])/[0-9]{4})",
                    "event <description> /at <d/M/yyyy>" },
            { "delete", "delete (\\d+)", "delete <task number>" },
    };

    private final Scanner input;

    public Parser(Scanner input) {
        this.input = input;
    }

    private static Command parseCommand(String line) throws RockyException {
        String cmdName = line.split(" ")[0];
        for (String[] command : COMMANDS) {
            // Not the target command
            if (!cmdName.equals(command[0])) continue;

            Pattern pattern = Pattern.compile(command[1]);
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                if (matcher.groupCount() > 0) {
                    String arg = matcher.group(1);
                    HashMap<String, String> kwargs = new HashMap<>();

                    // kwargs start at 2,
                    // the last one is groupCount() - 1, its value being groupCount()
                    for (int i = 2; i < matcher.groupCount(); i+=2) {
                        kwargs.put(matcher.group(i), matcher.group(i + 1));
                    }

                    return new Command(cmdName, arg, kwargs);
                } else {
                    return new Command(cmdName);
                }
            } else {
                //Syntax error
                throw new RockyException(String.format(
                        "Wui, that's not how you do it...\n" +
                                "\tUsage: s",
                        command[2]
                ));
            }
        }

        throw new RockyException("What are you trying to do?");
    }

    public Command readAndParse() throws RockyException {
        String cmdLine = this.input.nextLine();
        Command cmd = Parser.parseCommand(cmdLine);
        return cmd;
    }
}
