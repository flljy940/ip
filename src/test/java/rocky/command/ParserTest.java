package rocky.command;

import rocky.exception.RockyException;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    @Test
    public void readAndParse() {
        String input = "list\n"
                + "event fam's birthday /at 13/12/2025 1700-2200\n"
                + "todo buy picnic mat\n"
                + "delete 2\n"
                + "unmark 3";

        Parser parser = new Parser(new Scanner(input));

        try {
            Command cmd;

            cmd = parser.readAndParse();
            assertEquals("list", cmd.getCmd());
            assertEquals("", cmd.getArgs());
            assertEquals(0, cmd.getKwargs().size());

            cmd = parser.readAndParse();
            assertEquals("event", cmd.getCmd());
            assertEquals("fam's birthday", cmd.getArgs());
            assertEquals(1, cmd.getKwargs().size());

            cmd = parser.readAndParse();
            assertEquals("todo", cmd.getCmd());
            assertEquals("buy picnic mat", cmd.getArgs());
            assertEquals(0, cmd.getKwargs().size());

            cmd = parser.readAndParse();
            assertEquals("delete", cmd.getCmd());
            assertEquals("2", cmd.getArgs());
            assertEquals(0, cmd.getKwargs().size());

            cmd = parser.readAndParse();
            assertEquals("unmark", cmd.getCmd());
            assertEquals("3", cmd.getArgs());
            assertEquals(0, cmd.getKwargs().size());
        } catch (RockyException e) {
            assert false;
        }
    }

    @Test
    public void readAndParseInvalid() {
        String input = "deadline ahhh /by Sunday";
        Parser parser = new Parser(new Scanner(input));
        assertThrows(RockyException.class, () -> parser.readAndParse());
    }
}