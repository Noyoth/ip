package duke.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import duke.command.AddCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.ExitCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;
import duke.command.MarkCommand;
import duke.command.UnmarkCommand;
import duke.exception.DukeException;

/**
 * Unit tests for the {@link Parser} class.
 */
public class ParserTest {

    @Test
    public void parse_byeCommand_returnsExitCommand() throws DukeException {
        Command command = Parser.parse("bye");
        assertInstanceOf(ExitCommand.class, command);
        assertTrue(command.isExit());
    }

    @Test
    public void parse_listCommand_returnsListCommand() throws DukeException {
        Command command = Parser.parse("list");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    public void parse_validTodoCommand_returnsAddCommand() throws DukeException {
        Command command = Parser.parse("todo read book");
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    public void parse_emptyTodoDescription_exceptionThrown() {
        DukeException exception = assertThrows(DukeException.class, () -> Parser.parse("todo"));
        assertEquals("OOPS!!! The description of a todo cannot be empty.", exception.getMessage());
    }

    @Test
    public void parse_validDeadlineCommand_returnsAddCommand() throws DukeException {
        Command command = Parser.parse("deadline return book /by 2026-09-01 1800");
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    public void parse_deadlineMissingByKeyword_exceptionThrown() {
        DukeException exception = assertThrows(DukeException.class, () -> Parser.parse("deadline return book"));
        assertEquals("OOPS!!! The description and /by time of a deadline cannot be empty.", exception.getMessage());
    }

    @Test
    public void parse_deadlineMissingByTime_exceptionThrown() {
        DukeException exception = assertThrows(DukeException.class, () -> Parser.parse("deadline return book /by "));
        assertEquals("OOPS!!! The description and /by time of a deadline cannot be empty.", exception.getMessage());
    }

    @Test
    public void parse_validEventCommand_returnsAddCommand() throws DukeException {
        Command command = Parser.parse("event team meeting /from 2026-09-01 1400 /to 2026-09-01 1600");
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    public void parse_eventMissingToKeyword_exceptionThrown() {
        DukeException exception = assertThrows(DukeException.class, () ->
                Parser.parse("event team meeting /from 2026-09-01 1400"));
        assertEquals("OOPS!!! The /to time of an event cannot be empty.", exception.getMessage());
    }

    @Test
    public void parse_validMarkCommand_returnsMarkCommand() throws DukeException {
        Command command = Parser.parse("mark 2");
        assertInstanceOf(MarkCommand.class, command);
    }

    @Test
    public void parse_markNonNumericIndex_exceptionThrown() {
        DukeException exception = assertThrows(DukeException.class, () -> Parser.parse("mark abc"));
        assertEquals("OOPS!!! The task number provided is invalid.", exception.getMessage());
    }

    @Test
    public void parse_markMissingIndex_exceptionThrown() {
        DukeException exception = assertThrows(DukeException.class, () -> Parser.parse("mark"));
        assertEquals("OOPS!!! The task number cannot be empty.", exception.getMessage());
    }

    @Test
    public void parse_validUnmarkCommand_returnsUnmarkCommand() throws DukeException {
        Command command = Parser.parse("unmark 3");
        assertInstanceOf(UnmarkCommand.class, command);
    }

    @Test
    public void parse_validDeleteCommand_returnsDeleteCommand() throws DukeException {
        Command command = Parser.parse("delete 1");
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    public void parse_validFindCommand_returnsFindCommand() throws DukeException {
        Command command = Parser.parse("find book");
        assertInstanceOf(FindCommand.class, command);
    }

    @Test
    public void parse_emptyFindKeyword_exceptionThrown() {
        DukeException exception = assertThrows(DukeException.class, () -> Parser.parse("find"));
        assertEquals("OOPS!!! The search keyword cannot be empty.", exception.getMessage());
    }

    @Test
    public void parse_emptyInput_exceptionThrown() {
        DukeException exception = assertThrows(DukeException.class, () -> Parser.parse("   "));
        assertEquals("OOPS!!! Command cannot be empty.", exception.getMessage());
    }

    @Test
    public void parse_unknownCommand_exceptionThrown() {
        DukeException exception = assertThrows(DukeException.class, () -> Parser.parse("invalidCommand"));
        assertEquals("OOPS!!! I'm sorry, but I don't know what that means :-(", exception.getMessage());
    }
}
