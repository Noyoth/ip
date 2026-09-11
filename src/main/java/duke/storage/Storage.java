package duke.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.ToDo;

/**
 * Handles loading tasks from a file and saving tasks to a file.
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a Storage handler configured for the specified file path.
     *
     * @param filePath The path to the file used for loading and saving tasks.
     */
    public Storage(String filePath) {
        assert filePath != null && !filePath.trim().isEmpty() : "File path must not be null or empty";
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file specified by filePath.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws DukeException If there is an issue accessing or reading the file.
     */
    public ArrayList<Task> load() throws DukeException {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return loadedTasks;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                Task task = parseTask(line);
                if (task != null) {
                    loadedTasks.add(task);
                }
            }
        } catch (FileNotFoundException e) {
            throw new DukeException("Could not find data file at: " + filePath);
        } catch (Exception e) {
            throw new DukeException("Error loading tasks from file: " + e.getMessage());
        }
        return loadedTasks;
    }

    private Task parseTask(String line) {
        try {
            String[] parts = line.split("\\s*\\|\\s*");
            if (parts.length < 3) {
                return null;
            }
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String name = parts[2];

            Task task = null;
            if (type.equals("T")) {
                task = new ToDo(name);
            } else if (type.equals("D") && parts.length >= 4) {
                task = new Deadline(name, parts[3]);
            } else if (type.equals("E") && parts.length >= 5) {
                task = new Event(name, parts[3], parts[4]);
            }

            if (task != null && isDone) {
                task.markAsDone();
            }
            return task;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Saves the tasks in the given TaskList to the file specified by filePath.
     *
     * @param taskList The TaskList containing tasks to save.
     * @throws DukeException If an I/O error occurs while saving.
     */
    public void save(TaskList taskList) throws DukeException {
        assert taskList != null : "TaskList to save must not be null";
        save(taskList.getTasks());
    }

    /**
     * Saves the given list of tasks to the file specified by filePath.
     *
     * @param tasks The list of tasks to save.
     * @throws DukeException If an I/O error occurs while saving.
     */
    public void save(ArrayList<Task> tasks) throws DukeException {
        assert tasks != null : "Tasks list to save must not be null";
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
                throw new DukeException("Failed to create directory: " + parentDir.getAbsolutePath());
            }
            List<String> lines = tasks.stream()
                    .map(Task::toFileFormat)
                    .collect(Collectors.toList());
            Files.write(file.toPath(), lines);
        } catch (IOException e) {
            throw new DukeException("An error occurred while saving tasks.");
        }
    }
}
