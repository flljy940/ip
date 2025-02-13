package rocky.command;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.format.DateTimeFormatter;

import rocky.exception.RockyException;

/**
 * Class to encapsulate methods related to parsing user input
 * Contains available commands and their relative syntax
 */
public class Parser {
    /**
     * Date format for parsing from user input, file i/o
     */
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy");

    /**
     * Avaiable commands, their syntax (in regex), and usage documentation
     *
     * NOTE: syntax convention should be below
     * Semantics: command-name arg [[/key kwargs] ... ]
     * Regex: command-name (.*) (/[.+]) (.*))*
     */
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
                    "event (.*) /(at) ((?:[1-9]|[12][0-9]|3[01])/(?:[1-9]|1[0-2])/[0-9]{4} ([01][0-9]|2[0-3])[0-5][0-9]-([01][0-9]|2[0-3])[0-5][0-9])",
            "event <description> /at <d/M/yyyy> <HHmm-HHmm>"
            },
            { "delete", "delete (\\d+)", "delete <task number>" },
    };

    /**
     * Checks whether a command matches
     *
     * @param line command line to check validity
     * @return validity of the command
     * @throws RockyException invalid command or syntax
     */
    public static Command parseCommand(String line) throws RockyException {
        String cmdName = line.split(" ")[0];
        System.out.println("Command: " + cmdName);
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
            System.out.println("Arg: " + arg);
            HashMap<String, String> kwargs = new HashMap<>();

            // Match kwargs
            for (int i = 2; i < matcher.groupCount(); i += 2) {
                kwargs.put(matcher.group(i), matcher.group(i + 1));
            }
            System.out.println("Kwargs: " + kwargs);

            return new Command(cmdName, arg, kwargs);
        }

        throw new RockyException("What are you trying to do?");
    }

//    public Command readAndParse() throws RockyException {
//        String cmdLine = this.input.nextLine();
//        Command cmd = Parser.parseCommand(cmdLine);
//        return cmd;
//    }
}
