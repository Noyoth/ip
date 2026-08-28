package duke;

import duke.command.Command;
import duke.exception.DukeException;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Represents the main entry point for the Duke chatbot application.
 */
public class Duke {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Initializes the Duke chatbot with the specified storage file path.
     *
     * @param filePath The path to the file used for loading and saving tasks.
     */
    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main execution loop of the Duke chatbot.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * The main entry point for the Duke application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new Duke("./data/duke.txt").run();
    }
}
