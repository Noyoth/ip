/**
 * Parses user input strings into actionable Command objects.
 */
public class Parser {
    /**
     * Parses the user's full input string and returns the corresponding Command.
     *
     * @param fullCommand The raw input string from the user.
     * @return The Command representing the parsed user instruction.
     * @throws DukeException If the command is invalid or missing required parameters.
     */
    public static Command parse(String fullCommand) throws DukeException {
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
            if (parts.length < 2) {
                throw new DukeException("OOPS!!! The task number cannot be empty.");
            }
            try {
                int index = Integer.parseInt(parts[1]) - 1;
                return new MarkCommand(index);
            } catch (NumberFormatException e) {
                throw new DukeException("OOPS!!! The task number provided is invalid.");
            }
        case "unmark":
            if (parts.length < 2) {
                throw new DukeException("OOPS!!! The task number cannot be empty.");
            }
            try {
                int index = Integer.parseInt(parts[1]) - 1;
                return new UnmarkCommand(index);
            } catch (NumberFormatException e) {
                throw new DukeException("OOPS!!! The task number provided is invalid.");
            }
        case "delete":
            if (parts.length < 2) {
                throw new DukeException("OOPS!!! The task number cannot be empty.");
            }
            try {
                int index = Integer.parseInt(parts[1]) - 1;
                return new DeleteCommand(index);
            } catch (NumberFormatException e) {
                throw new DukeException("OOPS!!! The task number provided is invalid.");
            }
        case "todo":
            if (parts.length < 2) {
                throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
            }
            String todoDesc = trimmed.substring(commandWord.length()).trim();
            return new AddCommand(new ToDo(todoDesc));
        case "deadline":
            if (parts.length < 2) {
                throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
            }
            String deadlineInput = trimmed.substring(commandWord.length()).trim();
            String[] deadlineParts = deadlineInput.split(" /by ");
            if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty()
                    || deadlineParts[1].trim().isEmpty()) {
                throw new DukeException("OOPS!!! The description and /by time of a deadline cannot be empty.");
            }
            return new AddCommand(new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim()));
        case "event":
            if (parts.length < 2) {
                throw new DukeException("OOPS!!! The description of an event cannot be empty.");
            }
            String eventInput = trimmed.substring(commandWord.length()).trim();
            String[] eventParts = eventInput.split(" /from ");
            if (eventParts.length < 2 || eventParts[0].trim().isEmpty()) {
                throw new DukeException("OOPS!!! The description and /from time of an event cannot be empty.");
            }
            String[] timeParts = eventParts[1].split(" /to ");
            if (timeParts.length < 2 || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
                throw new DukeException("OOPS!!! The /to time of an event cannot be empty.");
            }
            return new AddCommand(new Event(eventParts[0].trim(), timeParts[0].trim(), timeParts[1].trim()));
        default:
            throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
