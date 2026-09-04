package duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Unit tests for the {@link Duke} class.
 */
public class DukeTest {

    @TempDir
    Path tempDir;

    private Duke duke;

    @BeforeEach
    public void setUp() {
        duke = new Duke(tempDir.resolve("test_duke.txt").toString());
    }

    @Test
    public void getResponse_validTodoCommand_returnsAddedMessage() {
        String response = duke.getResponse("todo read book");
        assertTrue(response.contains("Got it. I've added this task:"));
        assertTrue(response.contains("[T][ ] read book"));
    }

    @Test
    public void getResponse_invalidCommand_returnsErrorMessage() {
        String response = duke.getResponse("invalid command");
        assertEquals("OOPS!!! I'm sorry, but I don't know what that means :-(", response);
    }

    @Test
    public void getResponse_emptyTodoDescription_returnsErrorMessage() {
        String response = duke.getResponse("todo");
        assertEquals("OOPS!!! The description of a todo cannot be empty.", response);
    }
}
