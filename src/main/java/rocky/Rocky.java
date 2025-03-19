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
     * Constructs a Rocky instance
     *
     * @param filename File to load and save Tasks
     */
    public Rocky(String filename) {
        try {
            storage = new Storage(filename);
            tasks = storage.loadTasks();
        } catch (IOException | RockyException e) {
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
        try {
            switch (action.getCmd()) {
                case "bye":
                    response = handleBye();
                    break;

                case "list":
                    response = ui.getListReport(tasks);
                    break;

                case "find":
                    response = handleFind(action.getArgs());
                    break;

                case "mark":
                    response = handleMark(action.getArgs());
                    break;

                case "unmark":
                    response = handleUnmark(action.getArgs());
                    break;

                case "delete":
                    response = handleDelete(action.getArgs());
                    break;

                case "todo":
                    response = handleTodo(action.getArgs());
                    break;

                case "deadline":
                    response = handleDeadline(action);
                    break;

                case "event":
                    response = handleEvent(action);
                    break;

                default:
                    throw new RockyException("Sry!! I don't know what that means\uD83E\uDD7A");
            }

            assert !response.isEmpty(); // Ensures that every valid command returns a non-empty response
            return response;
        } catch (RockyException | NumberFormatException e) {
            return handleError(e);
        }
    }

    private String handleBye() throws RockyException {
        String response = "Bye. Hope to see you again soon!";
        try {
            storage.saveTasks(tasks);
        } catch (IOException e) {
            response += "\nOh... I can't save your tasks to file.";
        }
        isStopped = true;
        return response;
    }

    private String handleFind(String pattern) {
        return ui.getListReport(tasks.searchTasks(pattern.toLowerCase()));
    }

    private String handleMark(String arg) throws RockyException {
        int markIdx = parseIndex(arg);
        tasks.markTask(markIdx);
        return ui.getMarkTaskResponse(tasks.getTask(markIdx));
    }

    private String handleUnmark(String arg) throws RockyException {
        int unmarkIdx = parseIndex(arg);
        tasks.unmarkTask(unmarkIdx);
        return ui.getUnmarkTaskResponse(tasks.getTask(unmarkIdx));
    }

    private String handleDelete(String arg) throws RockyException {
        int dltIdx = parseIndex(arg);
        Task deletedTask = tasks.deleteTask(dltIdx);
        String response = ui.getDeletedTaskResponse(deletedTask, tasks.size());
        response += ui.getTaskCountReport(tasks.size());
        return response;
    }

    private String handleTodo(String arg) {
        Todo todo = new Todo(arg);
        tasks.addTask(todo);
        return ui.getNewTaskResponse(todo) + ui.getTaskCountReport(tasks.size());
    }

    private String handleDeadline(Command action) {
        String deadlineName = action.getArgs();
        String deadlineDate = action.getKwargs().get("by");
        Deadline deadline = new Deadline(deadlineName, deadlineDate);
        tasks.addTask(deadline);
        return ui.getNewTaskResponse(deadline) + ui.getTaskCountReport(tasks.size());
    }

    private String handleEvent(Command action) throws RockyException {
        String eventName = action.getArgs();
        String eventTime = action.getKwargs().get("at");

        if (eventTime == null) {
            throw new RockyException("Missing event time. Usage: event <description> /at <d/M/yyyy> <HHmm-HHmm>");
        }

        String eventDate = eventTime.substring(0, eventTime.indexOf(" "));
        String timeRange = eventTime.substring(eventTime.indexOf(" ") + 1);
        Event event = new Event(eventName, eventDate, timeRange);
        tasks.addTask(event);
        return ui.getNewTaskResponse(event) + ui.getTaskCountReport(tasks.size());
    }

    // Helper method to handle index parsing
    private int parseIndex(String arg) throws RockyException {
        try {
            return Integer.parseInt(arg) - 1;
        } catch (NumberFormatException e) {
            throw new RockyException("Invalid index: " + arg);
        }
    }

    // General error handling for invalid commands
    private String handleError(Exception e) {
        return "Error: " + e.getMessage();
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
            return handleActionAndRespond(action);
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
