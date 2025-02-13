package rocky.storage;

import rocky.task.TaskList;
import rocky.task.Task;

import rocky.exception.RockyException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class to abstract and encapsulate file interactions for Rocky.
 *
 * File format:
 * type|<0 or 1>|name[|additional arguments...]</0>
 */
public class Storage {
    protected String filename;
    protected File file;

    /**
     * Instantiates a FileManager
     *
     * @param filename path to file of interaction
     */
    public Storage(String filename) {
        this.filename = filename;
        this.file = new File(filename);
    }

    /**
     * Checks the existence of the file, creates it if it doesn't exist
     *
     * @throws IOException cannot create directories
     */
    private void createIfNotExist() throws IOException {
        if (this.file.exists()) {
            return;
        }
        this.file.getParentFile().mkdirs();
        this.file.createNewFile();
    }

    /**
     * Saves Tasks to file in a standard format
     *
     * @param taskList list of Tasks to save
     * @throws IOException file access error
     */
    public void saveTasks(TaskList taskList) throws IOException {
        createIfNotExist();
        FileWriter fileWriter = new FileWriter(this.filename, false);
        fileWriter.write(taskList.listFileSaveFormat());
        fileWriter.close();
    }

    /**
     * Loads Tasks from file
     *
     * @return TaskList populated with data from file
     * @throws IOException file access error
     * @throws RockyException when format error is present in file
     */
    public TaskList loadTasks() throws IOException, RockyException {
        createIfNotExist();

        try {
            Scanner fileReader = new Scanner(this.file);
            return loadTaskListFromScanner(fileReader);
        } catch (FileNotFoundException e) {
            return new TaskList();
        }
    }

    /**
     * Loads a TaskList from a Scanner object.
     * Each line of the Scanner should represent a Task
     *
     * @param scanner Scanner object to load from
     * @return loaded TaskList
     * @throws RockyException when format error is present in a Scanner line
     */
    private TaskList loadTaskListFromScanner(Scanner scanner) throws RockyException {
        TaskList taskList = new TaskList();
        for (int i = 1; scanner.hasNextLine(); i++) {
            try {
                Task task = Task.parseFileSaveFormat(scanner.nextLine());
                taskList.addTask(task);
            } catch (RockyException e) {
                throw new RockyException(String.format("Wrong format in line %d", i));
            }
        }
        scanner.close();
        return taskList;
    }
}
