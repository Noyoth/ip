package duke.parser;

import duke.command.AddCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.ExitCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;
import duke.command.MarkCommand;
import duke.command.UndoCommand;
import duke.command.UnmarkCommand;
import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.ToDo;

/**
 * Parses user input strings into actionable Command objects.
 */
public class Parser {
    /**
     * Prevents instantiation of this utility class.
     */
    private Parser() {
    }

    /**
     * Parses the user's full input string and returns the corresponding Command.
     *
     * @param fullCommand The raw input string from the user.
     * @return The Command representing the parsed user instruction.
     * @throws DukeException If the command is invalid or missing required parameters.
     */
    public static Command parse(String fullCommand) throws DukeException {
        assert fullCommand != null : "Command string must not be null";
        String trimmed = fullCommand.trim();
        if (trimmed.isEmpty()) {
            throw new DukeException("OOPS!!! Command cannot be empty.");
        }

        String[] parts = trimmed.split("\\s+");
        String commandWord = parts[0];

        switch (commandWord) {
            case "bye":
                return new ExitCommand();
            case "list":
                return new ListCommand();
            case "mark":
                return new MarkCommand(parseTaskIndex(parts));
            case "unmark":
                return new UnmarkCommand(parseTaskIndex(parts));
            case "delete":
                return new DeleteCommand(parseTaskIndex(parts));
            case "todo":
                return parseToDo(trimmed, commandWord);
            case "deadline":
                return parseDeadline(trimmed, commandWord);
            case "event":
                return parseEvent(trimmed, commandWord);
            case "find":
                return parseFind(trimmed, commandWord);
            case "undo":
                return new UndoCommand();
            default:
                throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    private static int parseTaskIndex(String[] parts) throws DukeException {
        if (parts.length < 2) {
            throw new DukeException("OOPS!!! The task number cannot be empty.");
        }
        try {
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new DukeException("OOPS!!! The task number provided is invalid.");
        }
    }

    private static Command parseToDo(String trimmed, String commandWord) throws DukeException {
        String todoDesc = trimmed.substring(commandWord.length()).trim();
        if (todoDesc.isEmpty()) {
            throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
        }
        return new AddCommand(new ToDo(todoDesc));
    }

    private static Command parseDeadline(String trimmed, String commandWord) throws DukeException {
        String deadlineInput = trimmed.substring(commandWord.length()).trim();
        if (deadlineInput.isEmpty()) {
            throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
        }
        String[] deadlineParts = deadlineInput.split(" /by ");
        if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty()
                || deadlineParts[1].trim().isEmpty()) {
            throw new DukeException("OOPS!!! The description and /by time of a deadline cannot be empty.");
        }
        return new AddCommand(new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim()));
    }

    private static Command parseEvent(String trimmed, String commandWord) throws DukeException {
        String eventInput = trimmed.substring(commandWord.length()).trim();
        if (eventInput.isEmpty()) {
            throw new DukeException("OOPS!!! The description of an event cannot be empty.");
        }
        String[] eventParts = eventInput.split(" /from ");
        if (eventParts.length < 2 || eventParts[0].trim().isEmpty()) {
            throw new DukeException("OOPS!!! The description and /from time of an event cannot be empty.");
        }
        String[] timeParts = eventParts[1].split(" /to ");
        if (timeParts.length < 2 || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
            throw new DukeException("OOPS!!! The /to time of an event cannot be empty.");
        }
        return new AddCommand(new Event(eventParts[0].trim(), timeParts[0].trim(), timeParts[1].trim()));
    }

    private static Command parseFind(String trimmed, String commandWord) throws DukeException {
        String keyword = trimmed.substring(commandWord.length()).trim();
        if (keyword.isEmpty()) {
            throw new DukeException("OOPS!!! The search keyword cannot be empty.");
        }
        return new FindCommand(keyword);
    }
}
