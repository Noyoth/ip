package duke.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Deadline;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.ToDo;
import duke.ui.Ui;

/**
 * Unit tests for {@link Command} subclasses.
 */
public class CommandTest {

    @TempDir
    Path tempDir;

    private TaskList tasks;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        tasks = new TaskList();
        ui = new Ui();
        storage = new Storage(tempDir.resolve("test_tasks.txt").toString());
    }

    @Test
    public void addCommand_execute_addsTaskAndSaves() throws DukeException {
        Task todo = new ToDo("read book");
        Command command = new AddCommand(todo);
        command.execute(tasks, ui, storage);

        assertEquals(1, tasks.size());
        assertEquals(todo, tasks.getTask(0));
        assertFalse(command.isExit());
    }

    @Test
    public void markCommand_execute_marksTaskAsDone() throws DukeException {
        Task todo = new ToDo("read book");
        tasks.addTask(todo);
        Command command = new MarkCommand(0);
        command.execute(tasks, ui, storage);

        assertEquals("X", todo.getStatusIcon());
    }

    @Test
    public void unmarkCommand_execute_unmarksTask() throws DukeException {
        Task todo = new ToDo("read book");
        todo.markAsDone();
        tasks.addTask(todo);
        Command command = new UnmarkCommand(0);
        command.execute(tasks, ui, storage);

        assertEquals(" ", todo.getStatusIcon());
    }

    @Test
    public void deleteCommand_execute_removesTask() throws DukeException {
        Task todo = new ToDo("read book");
        Task deadline = new Deadline("submit report", "2026-09-01 1800");
        tasks.addTask(todo);
        tasks.addTask(deadline);

        Command command = new DeleteCommand(0);
        command.execute(tasks, ui, storage);

        assertEquals(1, tasks.size());
        assertEquals(deadline, tasks.getTask(0));
    }

    @Test
    public void exitCommand_isExit_returnsTrue() throws DukeException {
        Command command = new ExitCommand();
        command.execute(tasks, ui, storage);
        assertTrue(command.isExit());
    }

    @Test
    public void findCommand_execute_success() throws DukeException {
        Task todo = new ToDo("read book");
        tasks.addTask(todo);
        Command command = new FindCommand("book");
        command.execute(tasks, ui, storage);
        assertFalse(command.isExit());
    }
}
