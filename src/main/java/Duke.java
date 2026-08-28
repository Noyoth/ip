/**
 * Represents the main entry point for the Duke chatbot application.
 */
public class Duke {
    private static final String FILE_PATH = "./data/duke.txt";

    /**
     * The main entry point for the Duke application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showWelcome();

        Storage storage = new Storage(FILE_PATH);
        TaskList tasks;
        try {
            tasks = new TaskList(storage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }

        while (true) {
            String input = ui.readCommand();
            if (input.isEmpty()) {
                continue;
            }
            
            String[] inputParts = input.split("\\s+");
            String command = inputParts[0];
            int indexOfTask;
            Task currentTask;

            try {
                switch (command) {
                    case "bye":
                        ui.showLine();
                        ui.showGoodbye();
                        ui.showLine();
                        return;
                    case "list":
                        ui.showLine();
                        for (int i = 0; i < tasks.size(); i++) {
                            Task t = tasks.getTask(i);
                            System.out.println((i + 1) + ". [" + t.getTaskIcon() + "]["
                                    + t.getStatusIcon() + "] " + t);
                        }
                        ui.showLine();
                        continue;
                    case "mark":
                        if (inputParts.length < 2) {
                            throw new DukeException("OOPS!!! The task number cannot be empty.");
                        }
                        indexOfTask = Integer.parseInt(inputParts[1]) - 1;
                        currentTask = tasks.getTask(indexOfTask);
                        currentTask.markAsDone();
                        storage.save(tasks);
                        ui.showLine();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  [" + currentTask.getTaskIcon() + "]["
                                + currentTask.getStatusIcon() + "] " + currentTask);
                        ui.showLine();
                        continue;
                    case "unmark":
                        if (inputParts.length < 2) {
                            throw new DukeException("OOPS!!! The task number cannot be empty.");
                        }
                        indexOfTask = Integer.parseInt(inputParts[1]) - 1;
                        currentTask = tasks.getTask(indexOfTask);
                        currentTask.markAsNotDone();
                        storage.save(tasks);
                        ui.showLine();
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  [" + currentTask.getTaskIcon() + "]["
                                + currentTask.getStatusIcon() + "] " + currentTask);
                        ui.showLine();
                        continue;
                    case "delete":
                        if (inputParts.length < 2) {
                            throw new DukeException("OOPS!!! The task number cannot be empty.");
                        }
                        indexOfTask = Integer.parseInt(inputParts[1]) - 1;
                        Task removedTask = tasks.deleteTask(indexOfTask);
                        storage.save(tasks);
                        ui.showLine();
                        System.out.println("Noted. I've removed this task:");
                        System.out.println("  [" + removedTask.getTaskIcon() + "]["
                                + removedTask.getStatusIcon() + "] " + removedTask);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        ui.showLine();
                        continue;
                    case "todo":
                        if (inputParts.length < 2) {
                            throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
                        }
                        String todoName = input.substring(command.length()).trim();
                        tasks.addTask(new ToDo(todoName));
                        storage.save(tasks);
                        ui.showLine();
                        System.out.println("Got it. I've added this task:");
                        currentTask = tasks.getTask(tasks.size() - 1);
                        System.out.println("  [" + currentTask.getTaskIcon() + "]["
                                + currentTask.getStatusIcon() + "] " + currentTask);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        ui.showLine();
                        continue;
                    case "deadline":
                        if (inputParts.length < 2) {
                            throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
                        }
                        String deadlineInput = input.substring(command.length()).trim();
                        String[] deadlineParts = deadlineInput.split(" /by ");
                        if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty()
                                || deadlineParts[1].trim().isEmpty()) {
                            throw new DukeException(
                                     "OOPS!!! The description and /by time of a deadline cannot be empty.");
                        }
                        tasks.addTask(new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim()));
                        storage.save(tasks);
                        ui.showLine();
                        System.out.println("Got it. I've added this task:");
                        currentTask = tasks.getTask(tasks.size() - 1);
                        System.out.println("  [" + currentTask.getTaskIcon() + "]["
                                + currentTask.getStatusIcon() + "] " + currentTask);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        ui.showLine();
                        continue;
                    case "event":
                        if (inputParts.length < 2) {
                            throw new DukeException("OOPS!!! The description of an event cannot be empty.");
                        }
                        String eventInput = input.substring(command.length()).trim();
                        String[] eventParts = eventInput.split(" /from ");
                        if (eventParts.length < 2 || eventParts[0].trim().isEmpty()) {
                            throw new DukeException(
                                    "OOPS!!! The description and /from time of an event cannot be empty.");
                        }
                        String[] timeParts = eventParts[1].split(" /to ");
                        if (timeParts.length < 2 || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
                            throw new DukeException("OOPS!!! The /to time of an event cannot be empty.");
                        }
                        tasks.addTask(new Event(eventParts[0].trim(), timeParts[0].trim(), timeParts[1].trim()));
                        storage.save(tasks);
                        ui.showLine();
                        System.out.println("Got it. I've added this task:");
                        currentTask = tasks.getTask(tasks.size() - 1);
                        System.out.println("  [" + currentTask.getTaskIcon() + "]["
                                + currentTask.getStatusIcon() + "] " + currentTask);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        ui.showLine();
                        continue;
                    default:
                        throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (DukeException e) {
                ui.showLine();
                ui.showError(e.getMessage());
                ui.showLine();
            } catch (NumberFormatException e) {
                ui.showLine();
                ui.showError("OOPS!!! The task number provided is invalid.");
                ui.showLine();
            } catch (Exception e) {
                ui.showLine();
                ui.showError("OOPS!!! An unexpected error occurred: " + e.getMessage());
                ui.showLine();
            }
        }
    }
}

