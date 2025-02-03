package rocky.task;

import rocky.exception.RockyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskListTest {
    @Test
    public void size_empty_zeroReturned() {
        assertEquals(0, new TaskList().size());
    }

    @Test
    public void size_nonEmpty() {
        TaskList tasks = new TaskList();

        tasks.addTask(new Todo("first"));
        tasks.addTask(new Todo("second"));
        tasks.addTask(new Todo("third"));

        assertEquals(3, tasks.size());

        try {
            tasks.deleteTask(2);

            tasks.addTask(new Todo("new 1"));
            tasks.addTask(new Todo("new 2"));

            assertEquals(4, tasks.size());
        } catch (RockyException e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void searchTasks() {
        TaskList tasks = new TaskList();

        tasks.addTask(new Todo("first"));
        tasks.addTask(new Todo("second"));
        tasks.addTask(new Todo("third"));
        tasks.addTask(new Todo("new 1"));
        tasks.addTask(new Todo("new 2"));

        assertEquals(2, tasks.searchTasks("new").size());
        assertEquals(2, tasks.searchTasks("ir").size());
        assertEquals(1, tasks.searchTasks("fir").size());
        assertEquals(0, tasks.searchTasks("fourth").size());
    }

    @Test
    public void getTask_invalidIndex_exceptionThrown() {
        TaskList tasks = new TaskList();

        tasks.addTask(new Todo("first"));
        tasks.addTask(new Todo("second"));
        tasks.addTask(new Todo("third"));

        assertThrows(RockyException.class, () -> tasks.getTask(-1));
        assertThrows(RockyException.class, () -> tasks.getTask(3));
    }

    @Test
    public void deleteTask_invalidIndex_exceptionThrown() {
        TaskList tasks = new TaskList();

        tasks.addTask(new Todo("first"));
        tasks.addTask(new Todo("second"));
        tasks.addTask(new Todo("third"));

        assertThrows(RockyException.class, () -> tasks.deleteTask(-1));
        assertThrows(RockyException.class, () -> tasks.deleteTask(3));
    }

    @Test
    public void markTask_invalidIndex_exceptionThrown() {
        TaskList tasks = new TaskList();

        tasks.addTask(new Todo("first"));
        tasks.addTask(new Todo("second"));
        tasks.addTask(new Todo("third"));

        assertThrows(RockyException.class, () -> tasks.markTask(-1));
        assertThrows(RockyException.class, () -> tasks.markTask(3));
    }

    @Test
    public void unmarkTask_invalidIndex_exceptionThrown() {
        TaskList tasks = new TaskList();

        tasks.addTask(new Todo("first"));
        tasks.addTask(new Todo("second"));
        tasks.addTask(new Todo("third"));

        assertThrows(RockyException.class, () -> tasks.unmarkTask(-1));
        assertThrows(RockyException.class, () -> tasks.unmarkTask(3));
    }
}
