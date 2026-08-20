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
                    indexOfTask = Integer.parseInt(inputParts[1]) - 1;
                    tasks[indexOfTask].markAsDone();
                    System.out.println("____________________________________________________________");
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  [" + tasks[indexOfTask].getTaskIcon() + "][" + tasks[indexOfTask].getStatusIcon() + "] " + tasks[indexOfTask]);
                    System.out.println("____________________________________________________________");
                    continue;
                case "unmark":
                    indexOfTask = Integer.parseInt(inputParts[1]) - 1;
                    tasks[indexOfTask].markAsNotDone();
                    System.out.println("____________________________________________________________");
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  [" + tasks[indexOfTask].getTaskIcon() + "][" + tasks[indexOfTask].getStatusIcon() + "] " + tasks[indexOfTask]);
                    System.out.println("____________________________________________________________");
                    continue;
                case "todo":
                    String todoName = input.substring(5);
                    tasks[numOfTasks] = new ToDo(todoName);
                    numOfTasks++;
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  [" + tasks[numOfTasks - 1].getTaskIcon() + "][" + tasks[numOfTasks - 1].getStatusIcon() + "] " + tasks[numOfTasks - 1]);
                    System.out.println("Now you have " + numOfTasks + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                    continue;
                case "deadline":
                    String deadlineInput = input.substring(9);
                    String[] deadlineParts = deadlineInput.split(" /by ");
                    tasks[numOfTasks] = new Deadline(deadlineParts[0] + " ", deadlineParts[1]);
                    numOfTasks++;
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  [" + tasks[numOfTasks - 1].getTaskIcon() + "][" + tasks[numOfTasks - 1].getStatusIcon() + "] " + tasks[numOfTasks - 1]);
                    System.out.println("Now you have " + numOfTasks + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                    continue;
                case "event":
                    String eventInput = input.substring(6);
                    String[] eventParts = eventInput.split(" /from ");
                    String[] timeParts = eventParts[1].split(" /to ");
                    tasks[numOfTasks] = new Event(eventParts[0] + " ", timeParts[0], timeParts[1]);
                    numOfTasks++;
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  [" + tasks[numOfTasks - 1].getTaskIcon() + "][" + tasks[numOfTasks - 1].getStatusIcon() + "] " + tasks[numOfTasks - 1]);
                    System.out.println("Now you have " + numOfTasks + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                    continue;
                default:
                    tasks[numOfTasks] = new Task(input);
                    numOfTasks++;
                    System.out.println("____________________________________________________________");
                    System.out.println("added: " + input);
                    System.out.println("____________________________________________________________");
            }
        }
    }
}
