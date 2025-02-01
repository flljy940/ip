package rocky.task;

import rocky.exception.RockyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskListTest {
    @Test
    public void size_empty_zeroReturned() {
        assertEquals(new TaskList().size(), 0);
    }

    @Test
    public void size_nonEmpty() {
        TaskList tasks = new TaskList();

        tasks.addTask(new Todo("first"));
        tasks.addTask(new Todo("second"));
        tasks.addTask(new Todo("third"));

        assertEquals(tasks.size(), 3);

        try {
            tasks.deleteTask(2);

            tasks.addTask(new Task("new 1"));
            tasks.addTask(new Task("new 2"));

            assertEquals(tasks.size(), 4);
        } catch (RockyException e) {
            System.out.println("Invalid");
            assert false;
        }
    }

    @Test
    public void getTask_indexOutOfBounds_exceptionThrown() {
        TaskList tasks = new TaskList();

        tasks.addTask(new Todo("first"));
        tasks.addTask(new Todo("second"));
        tasks.addTask(new Todo("third"));

        assertThrows(RockyException.class, () -> tasks.getTask(-1));
        assertThrows(RockyException.class, () -> tasks.getTask(3));
    }

    @Test
    public void deleteTask_indexOutOfBounds_exceptionThrown() {
        TaskList tasks = new TaskList();

        tasks.addTask(new Todo("first"));
        tasks.addTask(new Todo("second"));
        tasks.addTask(new Todo("third"));

        assertThrows(RockyException.class, () -> tasks.deleteTask(-1));
        assertThrows(RockyException.class, () -> tasks.deleteTask(3));
    }

    @Test
    public void markTask_indexOutOfBounds_exceptionThrown() {
        TaskList tasks = new TaskList();

        tasks.addTask(new Todo("first"));
        tasks.addTask(new Todo("second"));
        tasks.addTask(new Todo("third"));

        assertThrows(RockyException.class, () -> tasks.markTask(-1));
        assertThrows(RockyException.class, () -> tasks.markTask(3));
    }

    @Test
    public void unmarkTask_indexOutOfBounds_exceptionThrown() {
        TaskList tasks = new TaskList();

        tasks.addTask(new Todo("first"));
        tasks.addTask(new Todo("second"));
        tasks.addTask(new Todo("third"));

        assertThrows(RockyException.class, () -> tasks.unmarkTask(-1));
        assertThrows(RockyException.class, () -> tasks.unmarkTask(3));
    }
}
