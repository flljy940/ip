package rocky;

import java.io.IOException;
import java.util.Scanner;

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

    private static int parseIndex(String input) throws RockyException {
        try {
            int index = Integer.parseInt(input) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new RockyException(tasks.isEmpty()
                        ? "List is empty"
                        : (tasks.size() == 1
                            ? "There is only one task. Please choose task 1 :)"
                            : String.format("Please choose tasks from 1 to %d :))", tasks.size())
                        )
                );
            }
            return index;
        } catch (NumberFormatException e) {
            throw new RockyException("Please provide a valid number\uD83E\uDD7A");
        }
    }

    /**
     * Performs corresponding action according to the command passed and responds with a message
     *
     * @param action Command from user
     * @return return response after performing action
     * @throws RockyException General exception for invalid user command: invalid command, invalid arguments, etc.
     */
    private String handleActionAndRespond(Command action) throws RockyException {
        String response = "";

        switch (action.getCmd()) {
            case "bye":
                response = "Bye. Hope to see you again soon!";
                try {
                    storage.saveTasks(tasks);
                } catch (IOException e) {
                    response += "Oh... I can't save your tasks to file.";
                }
                break;

            case "list":
                response = ui.getListReport(tasks);
                break;

        case "find":
            String pattern = action.getArgs();
//            Ui.say("Here are the matching tasks in your list:\n" +
//                    tasks.searchTasks(pattern).toString());
            response = ui.getListReport(tasks.searchTasks(pattern));
            break;

        case "mark":
            int mark_idx = Integer.parseInt(action.getArgs()) - 1;
            tasks.markTask(mark_idx);
//            Ui.say("Nice! Marked as done:\n"
//                    + tasks.getTask(mark_idx));
            response = ui.getUpdateTaskResponse(tasks.getTask(mark_idx));
            break;

        case "unmark":
            int unmark_idx = Integer.parseInt(action.getArgs()) - 1;
            tasks.unmarkTask(unmark_idx);
//            Ui.say("OK, marked as not done yet:\n"
//                    + tasks.getTask(unmark_idx));
            response = ui.getUpdateTaskResponse(tasks.getTask(unmark_idx));
            break;

        case "delete":
            int dlt_idx = Integer.parseInt(action.getArgs()) - 1;
            Task deletedTask = tasks.deleteTask(dlt_idx);
//            Ui.say("Noted. Task removed:\n"
//                    + deletedTask
//                    + "\nNow you have " + tasks.size() + " tasks in your list");
            response = ui.getDeletedTaskResponse(deletedTask);
            response += ui.getTaskCountReport(tasks.size());
            break;

            case "todo":
                String todoName = action.getArgs();
                Todo todo = new Todo(todoName);
                tasks.addTask(todo);
//                Ui.logNewTask(todo, tasks.size());
                response = ui.getNewTaskResponse(todo);
                response += ui.getTaskCountReport(tasks.size());
                break;

            case "deadline":
                String deadlineName = action.getArgs();
                String deadlineDate = action.getKwargs().get("by");
                Deadline deadline = new Deadline(deadlineName, deadlineDate);
                tasks.addTask(deadline);
//                Ui.logNewTask(deadline, tasks.size());
                response = ui.getNewTaskResponse(deadline);
                response += ui.getTaskCountReport(tasks.size());
                break;

            case "event":
                String eventName = action.getArgs();
                String eventTime = action.getKwargs().get("at");
                String[] times = eventTime.split(" ");
                String eventDate = times[0];
                String timeRange = times[1];
                Event event = new Event(eventName, eventDate, timeRange);
                tasks.addTask(event);
//                Ui.logNewTask(event, tasks.size());
                response = ui.getNewTaskResponse(event);
                response += ui.getTaskCountReport(tasks.size());
                break;

            default:
                throw new RockyException("Sry!! I don't know what that means\uD83E\uDD7A");
        }

        return response;
    }

    /**
     * Interaction method to pass input to Rocky and get response
     *
     * @param input input from user
     * @return return response
     */
    public String interact(String input) {
        try {
//            Command action = cmd.readAndParse();
            Command action = cmd.parseCommand(input);
            String response = handleActionAndRespond(action);
            return response;
        } catch (RockyException e) {
            return e.getLocalizedMessage();
        }
    }

    /**
     * Getter for introduction string
     * @return introduction string
     */
    public String getIntroduction() {
        return introduction;
    }
}
