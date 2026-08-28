package duke.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
                try {
                    String[] parts = line.split("\\s*\\|\\s*");
                    if (parts.length < 3) {
                        continue;
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

                    if (task != null) {
                        if (isDone) {
                            task.markAsDone();
                        }
                        loadedTasks.add(task);
                    }
                } catch (Exception ex) {
                    // Skip corrupt or unparseable line
                }
            }
        } catch (FileNotFoundException e) {
            throw new DukeException("Could not find data file at: " + filePath);
        } catch (Exception e) {
            throw new DukeException("Error loading tasks from file: " + e.getMessage());
        }
        return loadedTasks;
    }

    /**
     * Saves the tasks in the given TaskList to the file specified by filePath.
     *
     * @param taskList The TaskList containing tasks to save.
     * @throws DukeException If an I/O error occurs while saving.
     */
    public void save(TaskList taskList) throws DukeException {
        save(taskList.getTasks());
    }

    /**
     * Saves the given list of tasks to the file specified by filePath.
     *
     * @param tasks The list of tasks to save.
     * @throws DukeException If an I/O error occurs while saving.
     */
    public void save(ArrayList<Task> tasks) throws DukeException {
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
                throw new DukeException("Failed to create directory: " + parentDir.getAbsolutePath());
            }
            try (FileWriter writer = new FileWriter(file)) {
                for (Task task : tasks) {
                    writer.write(task.toFileFormat() + "\n");
                }
            }
        } catch (IOException e) {
            throw new DukeException("An error occurred while saving tasks.");
        }
    }
}
