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
            switch (input) {
                case "bye":
                    System.out.println("____________________________________________________________");
                    System.out.println("Bye bye.");
                    System.out.println("____________________________________________________________");
                    break;
                case "list":
                    System.out.println("____________________________________________________________");
                    for (int i = 0; i < numOfTasks; i++) {
                        System.out.println(i + 1 + ". [" + tasks[i].getStatusIcon() + "] " + tasks[i]);
                    }
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
