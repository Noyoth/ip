import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * Represents the main entry point for the Duke chatbot application.
 */
public class Duke {
    /**
     * The main entry point for the Duke application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        String chatbotName = "TBC";
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm " + chatbotName + ".");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int numOfTasks = loadTasks(tasks);

        while (true) {
            String input = scanner.nextLine().trim();
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
                        System.out.println("____________________________________________________________");
                        System.out.println("Bye bye.");
                        System.out.println("____________________________________________________________");
                        return;
                    case "list":
                        System.out.println("____________________________________________________________");
                        for (int i = 0; i < numOfTasks; i++) {
                            System.out.println((i + 1) + ". [" + tasks[i].getTaskIcon() + "]["
                                    + tasks[i].getStatusIcon() + "] " + tasks[i]);
                        }
                        System.out.println("____________________________________________________________");
                        continue;
                    case "mark":
                        if (inputParts.length < 2) {
                            throw new DukeException("OOPS!!! The task number cannot be empty.");
                        }
                        indexOfTask = Integer.parseInt(inputParts[1]) - 1;
                        if (indexOfTask < 0 || indexOfTask >= numOfTasks) {
                            throw new DukeException("OOPS!!! The task number is invalid.");
                        }
                        tasks[indexOfTask].markAsDone();
                        saveTasks(tasks, numOfTasks);
                        System.out.println("____________________________________________________________");
                        System.out.println("Nice! I've marked this task as done:");
                        currentTask = tasks[indexOfTask];
                        System.out.println("  [" + currentTask.getTaskIcon() + "]["
                                + currentTask.getStatusIcon() + "] " + currentTask);
                        System.out.println("____________________________________________________________");
                        continue;
                    case "unmark":
                        if (inputParts.length < 2) {
                            throw new DukeException("OOPS!!! The task number cannot be empty.");
                        }
                        indexOfTask = Integer.parseInt(inputParts[1]) - 1;
                        if (indexOfTask < 0 || indexOfTask >= numOfTasks) {
                            throw new DukeException("OOPS!!! The task number is invalid.");
                        }
                        tasks[indexOfTask].markAsNotDone();
                        saveTasks(tasks, numOfTasks);
                        System.out.println("____________________________________________________________");
                        System.out.println("OK, I've marked this task as not done yet:");
                        currentTask = tasks[indexOfTask];
                        System.out.println("  [" + currentTask.getTaskIcon() + "]["
                                + currentTask.getStatusIcon() + "] " + currentTask);
                        System.out.println("____________________________________________________________");
                        continue;
                    case "delete":
                        if (inputParts.length < 2) {
                            throw new DukeException("OOPS!!! The task number cannot be empty.");
                        }
                        indexOfTask = Integer.parseInt(inputParts[1]) - 1;
                        if (indexOfTask < 0 || indexOfTask >= numOfTasks) {
                            throw new DukeException("OOPS!!! The task number is invalid.");
                        }
                        Task removedTask = tasks[indexOfTask];
                        for (int i = indexOfTask; i < numOfTasks - 1; i++) {
                            tasks[i] = tasks[i + 1];
                        }
                        tasks[numOfTasks - 1] = null;
                        numOfTasks--;
                        saveTasks(tasks, numOfTasks);
                        System.out.println("____________________________________________________________");
                        System.out.println("Noted. I've removed this task:");
                        System.out.println("  [" + removedTask.getTaskIcon() + "]["
                                + removedTask.getStatusIcon() + "] " + removedTask);
                        System.out.println("Now you have " + numOfTasks + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                        continue;
                    case "todo":
                        if (inputParts.length < 2) {
                            throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
                        }
                        if (numOfTasks >= tasks.length) {
                            throw new DukeException("OOPS!!! The task list is full.");
                        }
                        String todoName = input.substring(command.length()).trim();
                        tasks[numOfTasks] = new ToDo(todoName);
                        numOfTasks++;
                        saveTasks(tasks, numOfTasks);
                        System.out.println("____________________________________________________________");
                        System.out.println("Got it. I've added this task:");
                        currentTask = tasks[numOfTasks - 1];
                        System.out.println("  [" + currentTask.getTaskIcon() + "]["
                                + currentTask.getStatusIcon() + "] " + currentTask);
                        System.out.println("Now you have " + numOfTasks + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                        continue;
                    case "deadline":
                        if (inputParts.length < 2) {
                            throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
                        }
                        if (numOfTasks >= tasks.length) {
                            throw new DukeException("OOPS!!! The task list is full.");
                        }
                        String deadlineInput = input.substring(command.length()).trim();
                        String[] deadlineParts = deadlineInput.split(" /by ");
                        if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty()
                                || deadlineParts[1].trim().isEmpty()) {
                            throw new DukeException(
                                    "OOPS!!! The description and /by time of a deadline cannot be empty.");
                        }
                        tasks[numOfTasks] = new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim());
                        numOfTasks++;
                        saveTasks(tasks, numOfTasks);
                        System.out.println("____________________________________________________________");
                        System.out.println("Got it. I've added this task:");
                        currentTask = tasks[numOfTasks - 1];
                        System.out.println("  [" + currentTask.getTaskIcon() + "]["
                                + currentTask.getStatusIcon() + "] " + currentTask);
                        System.out.println("Now you have " + numOfTasks + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                        continue;
                    case "event":
                        if (inputParts.length < 2) {
                            throw new DukeException("OOPS!!! The description of an event cannot be empty.");
                        }
                        if (numOfTasks >= tasks.length) {
                            throw new DukeException("OOPS!!! The task list is full.");
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
                        tasks[numOfTasks] = new Event(eventParts[0].trim(), timeParts[0].trim(), timeParts[1].trim());
                        numOfTasks++;
                        saveTasks(tasks, numOfTasks);
                        System.out.println("____________________________________________________________");
                        System.out.println("Got it. I've added this task:");
                        currentTask = tasks[numOfTasks - 1];
                        System.out.println("  [" + currentTask.getTaskIcon() + "]["
                                + currentTask.getStatusIcon() + "] " + currentTask);
                        System.out.println("Now you have " + numOfTasks + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                        continue;
                    default:
                        throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (DukeException e) {
                System.out.println("____________________________________________________________");
                System.out.println(e.getMessage());
                System.out.println("____________________________________________________________");
            } catch (NumberFormatException e) {
                System.out.println("____________________________________________________________");
                System.out.println("OOPS!!! The task number provided is invalid.");
                System.out.println("____________________________________________________________");
            } catch (Exception e) {
                System.out.println("____________________________________________________________");
                System.out.println("OOPS!!! An unexpected error occurred: " + e.getMessage());
                System.out.println("____________________________________________________________");
            }
        }
    }

    /**
     * Loads tasks from the hard disk at ./data/duke.txt when the chatbot starts up.
     *
     * @param tasks The array to populate with loaded tasks.
     * @return The number of tasks loaded.
     */
    private static int loadTasks(Task[] tasks) {
        int loadedCount = 0;
        try {
            File file = new File("./data/duke.txt");
            if (!file.exists()) {
                return 0;
            }
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                if (loadedCount >= tasks.length) {
                    System.out.println("Warning: Task list is full. Some tasks were not loaded.");
                    break;
                }
                String line = fileScanner.nextLine();
                try {
                    String[] parts = line.split("\\s*\\|\\s*");
                    if (parts.length < 3) {
                        continue;
                    }
                    String type = parts[0];
                    boolean isDone = parts[1].equals("1");
                    String name = parts[2];

                    Task task = null;
                    if (type.equals("T")) {
                        task = new ToDo(name);
                    } else if (type.equals("D") && parts.length >= 4) {
                        task = new Deadline(name, parts[3]);
                    } else if (type.equals("E") && parts.length >= 5) {
                        task = new Event(name, parts[3], parts[4]);
                    }

                    if (task != null) {
                        if (isDone) {
                            task.markAsDone();
                        }
                        tasks[loadedCount] = task;
                        loadedCount++;
                    }
                } catch (Exception ex) {
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            System.out.println("Error while loading tasks: " + e.getMessage());
        }
        return loadedCount;
    }

    /**
     * Saves the current list of tasks to the hard disk at ./data/duke.txt.
     * This method ensures the data directory exists before attempting to write.
     *
     * @param tasks      The array containing the current tasks.
     * @param numOfTasks The number of active tasks in the array.
     */
    private static void saveTasks(Task[] tasks, int numOfTasks) {
        try {
            File directory = new File("./data");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            FileWriter writer = new FileWriter("./data/duke.txt");
            for (int i = 0; i < numOfTasks; i++) {
                writer.write(tasks[i].toFileFormat() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving tasks.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while saving tasks.");
        }
    }
}

