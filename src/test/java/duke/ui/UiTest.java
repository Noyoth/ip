package duke.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Ui} class.
 */
public class UiTest {

    private Ui ui;

    @BeforeEach
    public void setUp() {
        ui = new Ui();
    }

    @Test
    public void showMessages_varargsMultipleMessages_formatsCorrectly() {
        ui.showMessages("Line 1", "Line 2", "Line 3");
        assertEquals("Line 1\nLine 2\nLine 3", ui.getLastResponse());
    }

    @Test
    public void showMessages_varargsSingleMessage_formatsCorrectly() {
        ui.showMessages("Single message");
        assertEquals("Single message", ui.getLastResponse());
    }

    @Test
    public void showMessages_varargsNoMessage_emptyResponse() {
        ui.showMessages();
        assertEquals("", ui.getLastResponse());
    }
}
