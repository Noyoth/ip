package duke.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import duke.place.Place;
import duke.place.PlaceList;

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

    @Test
    public void showMessages_nullMessages_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> ui.showMessages((String[]) null));
    }

    @Test
    public void showUndoSuccess_outputsExpectedConfirmation() {
        ui.showUndoSuccess(2);
        assertEquals("Got it. I've undone the previous command.\nNow you have 2 tasks in the list.",
                ui.getLastResponse());
    }

    @Test
    public void showPlaceAdded_formatsCorrectly() {
        Place place = new Place("Sentosa", "Resort island");
        ui.showPlaceAdded(place, 1);
        String expected = "Got it. I've added this place:\n  Sentosa (details: Resort island)\n"
                + "Now you have 1 places in the list.";
        assertEquals(expected, ui.getLastResponse());
    }

    @Test
    public void showPlaceDeleted_formatsCorrectly() {
        Place place = new Place("Sentosa", "Resort island");
        ui.showPlaceDeleted(place, 0);
        String expected = "Noted. I've removed this place:\n  Sentosa (details: Resort island)\n"
                + "Now you have 0 places in the list.";
        assertEquals(expected, ui.getLastResponse());
    }

    @Test
    public void showPlacesList_emptyList_showsEmptyMessage() {
        ui.showPlacesList(new PlaceList());
        assertEquals("There are no places in your list.", ui.getLastResponse());
    }

    @Test
    public void showPlacesList_withPlaces_formatsNumberedList() {
        PlaceList places = new PlaceList(new Place("Place A"), new Place("Place B", "Details"));
        ui.showPlacesList(places);
        assertEquals("Here are the places in your list:\n1. Place A\n2. Place B (details: Details)",
                ui.getLastResponse());
    }

    @Test
    public void showFoundPlaces_emptyAndNonEmpty_formatsCorrectly() {
        ui.showFoundPlaces(new PlaceList());
        assertEquals("There are no matching places found.", ui.getLastResponse());

        PlaceList places = new PlaceList(new Place("Marina Bay Sands"));
        ui.showFoundPlaces(places);
        assertEquals("Here are the matching places in your list:\n1. Marina Bay Sands",
                ui.getLastResponse());
    }
}
