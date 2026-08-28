package duke.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.ToDo;

/**
 * Unit tests for the {@link Storage} class.
 */
public class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    public void load_nonExistentFile_returnsEmptyList() throws DukeException {
        File file = tempDir.resolve("nonexistent.txt").toFile();
        Storage storage = new Storage(file.getAbsolutePath());
        ArrayList<Task> tasks = storage.load();
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void load_validFileWithAllTaskTypes_success() throws Exception {
        File file = tempDir.resolve("tasks.txt").toFile();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("T | 1 | read book\n");
            writer.write("D | 0 | return book | 2026-09-01T18:00\n");
            writer.write("E | 1 | orientation | 2026-09-02T09:00 | 2026-09-02T17:00\n");
            writer.write("corrupted line that should be skipped\n");
        }

        Storage storage = new Storage(file.getAbsolutePath());
        ArrayList<Task> tasks = storage.load();
        assertEquals(3, tasks.size());

        assertEquals("T", tasks.get(0).getTaskIcon());
        assertEquals("X", tasks.get(0).getStatusIcon());
        assertEquals("read book", tasks.get(0).toString());

        assertEquals("D", tasks.get(1).getTaskIcon());
        assertEquals(" ", tasks.get(1).getStatusIcon());

        assertEquals("E", tasks.get(2).getTaskIcon());
        assertEquals("X", tasks.get(2).getStatusIcon());
    }

    @Test
    public void saveAndLoad_taskListRoundTrip_success() throws Exception {
        File file = tempDir.resolve("nested/dir/tasks.txt").toFile();
        Storage storage = new Storage(file.getAbsolutePath());

        TaskList taskList = new TaskList();
        taskList.addTask(new ToDo("buy groceries"));
        Deadline deadline = new Deadline("submit project", "2026-10-15 2359");
        deadline.markAsDone();
        taskList.addTask(deadline);
        taskList.addTask(new Event("hackathon", "2026-11-01 0900", "2026-11-02 1800"));

        storage.save(taskList);
        assertTrue(file.exists());

        ArrayList<Task> reloadedTasks = storage.load();
        assertEquals(3, reloadedTasks.size());
        assertEquals("buy groceries", reloadedTasks.get(0).toString());
        assertEquals("X", reloadedTasks.get(1).getStatusIcon());
        assertEquals("E", reloadedTasks.get(2).getTaskIcon());
    }
}
