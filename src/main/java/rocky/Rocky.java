package rocky;

import java.io.IOException;

import rocky.task.TaskList;
import rocky.task.Task;
import rocky.task.Todo;
import rocky.task.Deadline;
import rocky.task.Event;

import rocky.command.Command;
import rocky.command.Parser;

import rocky.storage.Storage;

import rocky.ui.Ui;

import rocky.exception.RockyException;

/**
 * Main class that abstracts the implementation of task list chatbot
 */
public class Rocky {
    /**
     * Status of Nikki (stopped  or not)
     */
    private boolean isStopped = false;
    /**
     * List of Tasks
     */
    private static TaskList tasks;

    /**
     * Storage component for load/save Tasks upon program start/end
     */
    private static Storage storage;

    /**
     * Parser for handling user input
     * Contains predefined command list and syntax
     */
    private static final Parser cmd = new Parser();

    /**
     * Ui component for displaying text in format and color
     */
    private static final Ui ui = new Ui();

    /**
     * Introduction string
     */
    String introduction = "Hello, I'm Rocky\n" + "What can I do for you?";

    /**
     * Construct a Rocky instance
     *
     * @param filename File to load and save Tasks
     */
    public Rocky(String filename) {
        try {
            storage = new Storage(filename);
            tasks = storage.loadTasks();
        } catch (IOException | RockyException e) {
//            ui.warning("[!] Error reading file - initializing task list as empty list");
            tasks = new TaskList();
        }
    }

    /**
     * Performs corresponding action according to the command passed and responds with a message
     *
     * @param action Command from user
     * @return return response after performing action
     * @throws RockyException General exception for invalid user command: invalid command, invalid arguments, etc.
     */
    private String handleActionAndRespond(Command action) throws RockyException, InterruptedException {
        String response;

        switch (action.getCmd()) {
        case "bye":
            response = "Bye. Hope to see you again soon!";

            try {
                storage.saveTasks(tasks);
            } catch (IOException e) {
                response += "\nOh... I can't save your tasks to file.";
            }
            isStopped = true;
            break;

        case "list":
            response = ui.getListReport(tasks);
            break;

        case "find":
            String pattern = action.getArgs();
            response = ui.getListReport(tasks.searchTasks(pattern));
            break;

        case "mark":
            int mark_idx = Integer.parseInt(action.getArgs()) - 1;
            tasks.markTask(mark_idx);
            response = ui.getMarkTaskResponse(tasks.getTask(mark_idx));
            break;

        case "unmark":
            int unmark_idx = Integer.parseInt(action.getArgs()) - 1;
            tasks.unmarkTask(unmark_idx);
            response = ui.getUnmarkTaskResponse(tasks.getTask(unmark_idx));
            break;

        case "delete":
            int dlt_idx = Integer.parseInt(action.getArgs()) - 1;
            Task deletedTask = tasks.deleteTask(dlt_idx);
            response = ui.getDeletedTaskResponse(deletedTask, tasks.size());
            response += ui.getTaskCountReport(tasks.size());
            break;

        case "todo":
            String todoName = action.getArgs();
            Todo todo = new Todo(todoName);
            tasks.addTask(todo);
            response = ui.getNewTaskResponse(todo);
            response += ui.getTaskCountReport(tasks.size());
            break;

        case "deadline":
            String deadlineName = action.getArgs();
            String deadlineDate = action.getKwargs().get("by");
            Deadline deadline = new Deadline(deadlineName, deadlineDate);
            tasks.addTask(deadline);
            response = ui.getNewTaskResponse(deadline);
            response += ui.getTaskCountReport(tasks.size());
            break;

        case "event":
            String eventName = action.getArgs();
            String eventTime = action.getKwargs().get("at");
            if (eventTime == null) {
                throw new RockyException("Missing event time. Usage: event <description> /at <d/M/yyyy> <HHmm-HHmm>");
            }
            String eventDate = eventTime.substring(0, eventTime.indexOf(" "));
            String timeRange = eventTime.substring(eventTime.indexOf(" ") + 1);
            Event event = new Event(eventName, eventDate, timeRange);
            tasks.addTask(event);
            response = ui.getNewTaskResponse(event);
            response += ui.getTaskCountReport(tasks.size());
            break;

        default:
            throw new RockyException("Sry!! I don't know what that means\uD83E\uDD7A");
        }

        // All valid command handler should give a response
        // Else, an exception should have been raised
        assert !response.isEmpty();

        return response;
    }

    /**
     * Responds to input passed by user to input
     *
     * @param input input from user
     * @return return response
     */
    public String interact(String input) {
        try {
            if (isStopped) {
                throw new RockyException("I've already stopped");
            }
            Command action = cmd.parseCommand(input);
            String response = handleActionAndRespond(action);
            return response;
        } catch (RockyException e) {
            return e.getLocalizedMessage();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Getter for introduction string
     *
     * @return introduction string
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * Getter for status of Rocky
     * @return boolean value for whether Rocky has stopped
     */
    public boolean isStopped() {
        return isStopped;
    }
}
