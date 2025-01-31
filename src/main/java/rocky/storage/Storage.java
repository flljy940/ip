package rocky.storage;

import rocky.task.TaskList;
import rocky.task.Task;

import rocky.exception.RockyException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    protected String filename;
    protected File file;

    public Storage(String filename) {
        this.filename = filename;
        this.file = new File(filename);
    }

    public void saveTasks(TaskList taskList) throws IOException {
        FileWriter fileWriter = new FileWriter(this.filename, false);
        fileWriter.write(taskList.tasksFileSaveFormat());
        fileWriter.close();
    }

    public TaskList loadTasks() throws IOException, RockyException {
        // If file doesn't exist, create it
        if (!this.file.exists()) {
            this.file.getParentFile().mkdirs(); // Create directories if needed
            this.file.createNewFile(); // Create an empty file
        }

        try {
            Scanner fileReader = new Scanner(this.file);

            TaskList taskList = new TaskList();
            while (fileReader.hasNextLine()) {
                try {
                    String line = fileReader.nextLine().trim();
                    if (!line.isEmpty()) {
                        Task task = Task.parseFileSaveFormat(line);
                        taskList.addTask(task);
                    }
                } catch (RockyException e) {
                    throw new RockyException("Error parsing task: " + e.getMessage());
                }
            }
            fileReader.close();
            return taskList;
        } catch (FileNotFoundException e) {
            return new TaskList();
        }
    }
}
