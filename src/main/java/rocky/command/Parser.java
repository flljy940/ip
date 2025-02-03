package rocky.command;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.format.DateTimeFormatter;

import rocky.exception.RockyException;

public class Parser {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy");

    private static final String[][] COMMANDS = {
            { "bye", "bye", "bye" },
            { "list", "list", "list" },
            { "find", "find (.*)", "find <some string>" },
            { "mark", "mark (\\d+)", "mark <task number>" },
            { "unmark", "unmark (\\d+)", "unmark <task number>" },
            { "todo", "todo (.*)", "todo <description>" },
            {
                    "deadline",
                    "deadline (.*) /(by) ((?:[1-9]|[12][0-9]|3[01])/(?:[1-9]|1[0-2])/[0-9]{4})",
                    "deadline <description> /by <d/M/yyyy>"
            },
            {
                    "event",
                    "event (.*) /(at) ((?:[1-9]|[12][0-9]|3[01])/(?:[1-9]|1[0-2])/[0-9]{4}) (([01][0-9]|2[0-3])[0-5][0-9])-(([01][0-9]|2[0-3])[0-5][0-9])",
                    "event <description> /at <d/M/yyyy> <HHmm-HHmm>"
            },
            { "delete", "delete (\\d+)", "delete <task number>" },
    };

    private final Scanner input;

    public Parser(Scanner input) {
        this.input = input;
    }

    public static Command parseCommand(String line) throws RockyException {
        String cmdName = line.split(" ")[0];
        for (String[] cmd : COMMANDS) {
            // Not the target command
            if (!cmdName.equals(cmd[0])) {
                continue;
            }

            Pattern pattern = Pattern.compile(cmd[1]);
            Matcher matcher = pattern.matcher(line);

            // Syntax error
            if (!matcher.matches()) {
                throw new RockyException(String.format(
                        "Wui, that's not how you do it...\n"
                                + "\tUsage: %s",
                        cmd[2]
                ));
            }

            // No args or kwargs
            if (matcher.groupCount() == 0) {
                return new Command(cmdName);
            }

            // Match args
            String arg = matcher.group(1);
            HashMap<String, String> kwargs = new HashMap<>();

            // Match kwargs
            for (int i = 2; i < matcher.groupCount(); i += 2) {
                kwargs.put(matcher.group(i), matcher.group(i + 1));
            }

            return new Command(cmdName, arg, kwargs);
        }

        throw new RockyException("What are you trying to do?");
    }

    public Command readAndParse() throws RockyException {
        String cmdLine = this.input.nextLine();
        Command cmd = Parser.parseCommand(cmdLine);
        return cmd;
    }
}
