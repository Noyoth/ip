import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        String banner = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        String chatbotName = "TBC";
        System.out.println(banner);
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm " + chatbotName + ".");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int numOfTasks = 0;

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
                        tasks[numOfTasks] = new Deadline(deadlineParts[0].trim() + " ", deadlineParts[1].trim());
                        numOfTasks++;
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
                        tasks[numOfTasks] = new Event(eventParts[0].trim() + " ", timeParts[0].trim(), timeParts[1].trim());
                        numOfTasks++;
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
}
