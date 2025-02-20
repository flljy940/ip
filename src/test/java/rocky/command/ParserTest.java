package rocky.command;

import rocky.exception.RockyException;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    @Test
    public void parseCommand() {
        Parser parser = new Parser();

        try {
            Command cmd;

            cmd = parser.parseCommand("list");
            assertEquals("list", cmd.getCmd());
            assertEquals("", cmd.getArgs());
            assertEquals(0, cmd.getKwargs().size());

            cmd = parser.parseCommand("deadline project proposal /by 3/3/2025");
            assertEquals("deadline", cmd.getCmd());
            assertEquals("project proposal", cmd.getArgs());
            assertEquals(1, cmd.getKwargs().size());

            cmd = parser.parseCommand("deadline project proposal /by 20/2/2025");
            assertEquals("deadline", cmd.getCmd());
            assertEquals("project proposal", cmd.getArgs());
            assertEquals(1, cmd.getKwargs().size());

            cmd = parser.parseCommand("todo buy picnic mat");
            assertEquals("todo", cmd.getCmd());
            assertEquals("buy picnic mat", cmd.getArgs());
            assertEquals(0, cmd.getKwargs().size());

            cmd = parser.parseCommand("event bff's birthday /at 7/3/2025 1600-2100");
            assertEquals("event", cmd.getCmd());
            assertEquals("bff's birthday", cmd.getArgs());
            assertEquals(2, cmd.getKwargs().size());

            cmd = parser.parseCommand("delete 2");
            assertEquals("delete", cmd.getCmd());
            assertEquals("2", cmd.getArgs());
            assertEquals(0, cmd.getKwargs().size());

            cmd = parser.parseCommand("unmark 3");
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
        Parser parser = new Parser();
        assertThrows(RockyException.class, () -> parser.parseCommand(input));
    }
}