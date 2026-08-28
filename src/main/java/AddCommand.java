/**
 * Represents a command to add a task to the task list.
 */
public class AddCommand extends Command {
    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        tasks.addTask(task);
        storage.save(tasks);
        ui.showLine();
        System.out.println("Got it. I've added this task:");
        System.out.println("  [" + task.getTaskIcon() + "][" + task.getStatusIcon() + "] " + task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        ui.showLine();
    }
}
