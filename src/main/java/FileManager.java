import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {
    protected String filename;
    protected File file;

    public FileManager(String filename) {
        this.filename = filename;
        this.file = new File(filename);
    }

    public void saveTasks(TaskList taskList) throws IOException {
        FileWriter fileWriter = new FileWriter(this.filename, false);
        fileWriter.write(taskList.toString());
        fileWriter.close();
    }

    public TaskList loadTasks() throws IOException, RockyException {
        if (!this.file.exists()) {
            this.file.getParentFile().mkdirs();
            this.file.createNewFile();
        }

        try {
            Scanner fileReader = new Scanner(this.file);

            TaskList taskList = new TaskList();
            for (int i = 1; fileReader.hasNextLine(); i++) {
                try {
                    Task task = Task.parseFileSaveFormat(fileReader.nextLine());
                    taskList.addTask(task);
                } catch (RockyException e) {
                    throw new RockyException(e.getMessage());
                }
            }

            return taskList;
        } catch (FileNotFoundException e) {
            return new TaskList();
        }
    }
}
