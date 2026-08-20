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
        while (true) {
            String input = scanner.nextLine();
            String[] tasks = new String[100];

            switch (input) {
                case "bye":
                    System.out.println("____________________________________________________________");
                    System.out.println("Bye bye.");
                    System.out.println("____________________________________________________________");
                    break;
                case "list":
                    System.out.println("____________________________________________________________");
                    for (int i = 1; i <= tasks.length; i++) {
                        System.out.println(1 + ". " + tasks[i - 1]);
                    }
                    System.out.println("____________________________________________________________");
                default:
                    System.out.println("____________________________________________________________");
                    System.out.println(input);
                    System.out.println("____________________________________________________________");
            }
        }
    }
}
