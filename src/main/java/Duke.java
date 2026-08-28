import java.util.Scanner;

public class Duke {
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
            String input = scanner.nextLine();
            String[] inputParts = input.split(" ");
            String command = inputParts[0];
            int indexOfTask;

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
                            System.out.println(i + 1 + ". [" + tasks[i].getTaskIcon() + "][" + tasks[i].getStatusIcon() + "] " + tasks[i]);
                        }
                        System.out.println("____________________________________________________________");
                        continue;
                    case "mark":
                        if (inputParts.length < 2) throw new DukeException("OOPS!!! The task number cannot be empty.");
                        indexOfTask = Integer.parseInt(inputParts[1]) - 1;
                        if (indexOfTask < 0 || indexOfTask >= numOfTasks) throw new DukeException("OOPS!!! The task number is invalid.");
                        tasks[indexOfTask].markAsDone();
                        saveTasks(tasks, numOfTasks);
                        System.out.println("____________________________________________________________");
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  [" + tasks[indexOfTask].getTaskIcon() + "][" + tasks[indexOfTask].getStatusIcon() + "] " + tasks[indexOfTask]);
                        System.out.println("____________________________________________________________");
                        continue;
                    case "unmark":
                        if (inputParts.length < 2) throw new DukeException("OOPS!!! The task number cannot be empty.");
                        indexOfTask = Integer.parseInt(inputParts[1]) - 1;
                        if (indexOfTask < 0 || indexOfTask >= numOfTasks) throw new DukeException("OOPS!!! The task number is invalid.");
                        tasks[indexOfTask].markAsNotDone();
                        saveTasks(tasks, numOfTasks);
                        System.out.println("____________________________________________________________");
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  [" + tasks[indexOfTask].getTaskIcon() + "][" + tasks[indexOfTask].getStatusIcon() + "] " + tasks[indexOfTask]);
                        System.out.println("____________________________________________________________");
                        continue;
                    case "delete":
                        if (inputParts.length < 2) throw new DukeException("OOPS!!! The task number cannot be empty.");
                        indexOfTask = Integer.parseInt(inputParts[1]) - 1;
                        if (indexOfTask < 0 || indexOfTask >= numOfTasks) throw new DukeException("OOPS!!! The task number is invalid.");
                        Task removedTask = tasks[indexOfTask];
                        for (int i = indexOfTask; i < numOfTasks - 1; i++) {
                            tasks[i] = tasks[i + 1];
                        }
                        tasks[numOfTasks - 1] = null;
                        numOfTasks--;
                        saveTasks(tasks, numOfTasks);
                        System.out.println("____________________________________________________________");
                        System.out.println("Noted. I've removed this task:");
                        System.out.println("  [" + removedTask.getTaskIcon() + "][" + removedTask.getStatusIcon() + "] " + removedTask);
                        System.out.println("Now you have " + numOfTasks + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                        continue;
                    case "todo":
                        if (input.trim().equals("todo")) {
                            throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
                        }
                        String todoName = input.substring(5).trim();
                        tasks[numOfTasks] = new ToDo(todoName);
                        numOfTasks++;
                        saveTasks(tasks, numOfTasks);
                        System.out.println("____________________________________________________________");
                        System.out.println("Got it. I've added this task:");
                        System.out.println("  [" + tasks[numOfTasks - 1].getTaskIcon() + "][" + tasks[numOfTasks - 1].getStatusIcon() + "] " + tasks[numOfTasks - 1]);
                        System.out.println("Now you have " + numOfTasks + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                        continue;
                    case "deadline":
                        if (input.trim().equals("deadline")) {
                            throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
                        }
                        String deadlineInput = input.substring(9).trim();
                        String[] deadlineParts = deadlineInput.split(" /by ");
                        if (deadlineParts.length < 2) throw new DukeException("OOPS!!! The /by time of a deadline cannot be empty.");
                        tasks[numOfTasks] = new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim());
                        numOfTasks++;
                        saveTasks(tasks, numOfTasks);
                        System.out.println("____________________________________________________________");
                        System.out.println("Got it. I've added this task:");
                        System.out.println("  [" + tasks[numOfTasks - 1].getTaskIcon() + "][" + tasks[numOfTasks - 1].getStatusIcon() + "] " + tasks[numOfTasks - 1]);
                        System.out.println("Now you have " + numOfTasks + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                        continue;
                    case "event":
                        if (input.trim().equals("event")) {
                            throw new DukeException("OOPS!!! The description of an event cannot be empty.");
                        }
                        String eventInput = input.substring(6).trim();
                        String[] eventParts = eventInput.split(" /from ");
                        if (eventParts.length < 2) throw new DukeException("OOPS!!! The /from time of an event cannot be empty.");
                        String[] timeParts = eventParts[1].trim().split(" /to ");
                        if (timeParts.length < 2) throw new DukeException("OOPS!!! The /to time of an event cannot be empty.");
                        tasks[numOfTasks] = new Event(eventParts[0].trim(), timeParts[0].trim(), timeParts[1].trim());
                        numOfTasks++;
                        saveTasks(tasks, numOfTasks);
                        System.out.println("____________________________________________________________");
                        System.out.println("Got it. I've added this task:");
                        System.out.println("  [" + tasks[numOfTasks - 1].getTaskIcon() + "][" + tasks[numOfTasks - 1].getStatusIcon() + "] " + tasks[numOfTasks - 1]);
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
            java.io.File file = new java.io.File("./data/duke.txt");
            if (!file.exists()) {
                return 0;
            }
            java.util.Scanner fileScanner = new java.util.Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\s*\\|\\s*");
                if (parts.length < 3) continue;
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String name = parts[2];

                Task task = null;
                if (type.equals("T")) {
                    task = new ToDo(name);
                } else if (type.equals("D") && parts.length >= 4) {
                    task = new Deadline(name, parts[3]);
                } else if (type.equals("E") && parts.length >= 4) {
                    String[] timeParts = parts[3].split("-", 2);
                    if (timeParts.length >= 2) {
                        task = new Event(name, timeParts[0], timeParts[1]);
                    }
                }

                if (task != null) {
                    if (isDone) {
                        task.markAsDone();
                    }
                    tasks[loadedCount] = task;
                    loadedCount++;
                }
            }
            fileScanner.close();
        } catch (java.io.FileNotFoundException e) {
            
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
            java.io.File directory = new java.io.File("./data");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            java.io.FileWriter writer = new java.io.FileWriter("./data/duke.txt");
            for (int i = 0; i < numOfTasks; i++) {
                writer.write(tasks[i].toFileFormat() + "\n");
            }
            writer.close();
        } catch (java.io.IOException e) {
            System.out.println("An error occurred while saving tasks.");
        }
    }

}
