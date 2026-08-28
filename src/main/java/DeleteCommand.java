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
        ui.showLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  [" + removedTask.getTaskIcon() + "]["
                + removedTask.getStatusIcon() + "] " + removedTask);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        ui.showLine();
    }
}
