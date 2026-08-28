/**
 * Represents a command to exit the application.
 */
public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showLine();
        ui.showGoodbye();
        ui.showLine();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
