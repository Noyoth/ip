/**
 * Represents a command to mark a task as completed.
 */
public class MarkCommand extends Command {
    private final int targetIndex;

    public MarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        Task task = tasks.getTask(targetIndex);
        task.markAsDone();
        storage.save(tasks);
        ui.showLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  [" + task.getTaskIcon() + "][" + task.getStatusIcon() + "] " + task);
        ui.showLine();
    }
}
