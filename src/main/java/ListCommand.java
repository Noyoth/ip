/**
 * Represents a command to list all tasks in the task list.
 */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showLine();
        for (int i = 0; i < tasks.size(); i++) {
            try {
                Task t = tasks.getTask(i);
                System.out.println((i + 1) + ". [" + t.getTaskIcon() + "]["
                        + t.getStatusIcon() + "] " + t);
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            }
        }
        ui.showLine();
    }
}
