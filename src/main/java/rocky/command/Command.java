package rocky.command;

import java.util.HashMap;
import java.util.Map;

public class Command {
    private String cmd;
    private String args;
    private Map<String, String> kwargs;

    public Command(String cmd) {
        this.cmd = cmd;
        this.args = "";
        this.kwargs = new HashMap<>();
    }

    public Command(String cmd, String arg) {
        this.cmd = cmd;
        this.args = arg;
        this.kwargs = new HashMap<>();
    }

    public Command(String cmd, String arg, Map<String, String> kwargs) {
        this.cmd = cmd;
        this.args = arg;
        this.kwargs = kwargs;
    }

    public String getCmd() {
        return this.cmd;
    }

    public String getArgs() {
        return this.args;
    }

    public Map<String, String> getKwargs() {
        return this.kwargs;
    }
}
