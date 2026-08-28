/**
 * Represents a command to mark a task as not completed yet.
 */
public class UnmarkCommand extends Command {
    private final int targetIndex;

    public UnmarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        Task task = tasks.getTask(targetIndex);
        task.markAsNotDone();
        storage.save(tasks);
        ui.showTaskUnmarked(task);
    }
}
