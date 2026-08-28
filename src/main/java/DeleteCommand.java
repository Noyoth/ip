/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private final int targetIndex;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        Task removedTask = tasks.deleteTask(targetIndex);
        storage.save(tasks);
        ui.showTaskDeleted(removedTask, tasks.size());
    }
}
