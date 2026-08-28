package duke.task;

import java.util.ArrayList;

import duke.exception.DukeException;

/**
 * Encapsulates the collection of tasks and operations on the task list.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList initialized with the given list of tasks.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the list at the specified index.
     *
     * @param index The 0-based index of the task to delete.
     * @return The removed task.
     * @throws DukeException If the index is out of bounds.
     */
    public Task deleteTask(int index) throws DukeException {
        if (index < 0 || index >= tasks.size()) {
            throw new DukeException("OOPS!!! The task number is invalid.");
        }
        return tasks.remove(index);
    }

    /**
     * Retrieves a task from the list at the specified index.
     *
     * @param index The 0-based index of the task to retrieve.
     * @return The task at the specified index.
     * @throws DukeException If the index is out of bounds.
     */
    public Task getTask(int index) throws DukeException {
        if (index < 0 || index >= tasks.size()) {
            throw new DukeException("OOPS!!! The task number is invalid.");
        }
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the underlying list of tasks.
     *
     * @return The ArrayList of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Finds all tasks whose description contains the specified keyword.
     *
     * @param keyword The search keyword.
     * @return A new TaskList containing matching tasks.
     */
    public TaskList findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.toString().contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        return new TaskList(matchingTasks);
    }
}
