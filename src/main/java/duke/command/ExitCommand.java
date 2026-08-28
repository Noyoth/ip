package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Represents a command to exit the application.
 */
public class ExitCommand extends Command {
    /**
     * Constructs an ExitCommand.
     */
    public ExitCommand() {
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
