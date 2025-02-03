package rocky.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to capture fields and encapsulate behavior of a Command
 * A Command is parsed from user input
 */
public class Command {
    /**
     * Command name
     */
    private String cmd;

    /**
     * Arguments of Command
     */
    private String args;

    /**
     * Keyword arguments
     */
    private Map<String, String> kwargs;

    /**
     * Creates a Command object
     *
     * @param cmd name of command
     */
    public Command(String cmd) {
        this.cmd = cmd;
        this.args = "";
        this.kwargs = new HashMap<>();
    }

    /**
     * Creates a Command object with arg
     *
     * @param cmd name of command
     * @param arg argument of command
     */
    public Command(String cmd, String arg) {
        this.cmd = cmd;
        this.args = arg;
        this.kwargs = new HashMap<>();
    }

    /**
     * Creates a Command object with arg and kwargs
     *
     * @param cmd name of command
     * @param arg argument of command
     * @param kwargs keyword arguments of command
     */
    public Command(String cmd, String arg, Map<String, String> kwargs) {
        this.cmd = cmd;
        this.args = arg;
        this.kwargs = kwargs;
    }

    /**
     * Getter for name
     *
     * @return name of command
     */
    public String getCmd() {
        return this.cmd;
    }

    /**
     * Getter for argument
     *
     * @return argument of command
     */
    public String getArgs() {
        return this.args;
    }

    /**
     * Getter for keyword arguments
     *
     * @return keyword arguments of command
     */
    public Map<String, String> getKwargs() {
        return this.kwargs;
    }
}
