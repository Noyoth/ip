package duke.place;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Place} class.
 */
public class PlaceTest {

    @Test
    public void constructor_withoutDetails_formatsCorrectly() {
        Place place = new Place("Sentosa");
        assertEquals("Sentosa", place.getName());
        assertEquals("", place.getDetails());
        assertEquals("Sentosa", place.toString());
        assertEquals("Sentosa | ", place.toFileFormat());
    }

    @Test
    public void constructor_withDetails_formatsCorrectly() {
        Place place = new Place("Jumbo Seafood", "Riverside Point, great chili crab");
        assertEquals("Jumbo Seafood", place.getName());
        assertEquals("Riverside Point, great chili crab", place.getDetails());
        assertEquals("Jumbo Seafood (details: Riverside Point, great chili crab)", place.toString());
        assertEquals("Jumbo Seafood | Riverside Point, great chili crab", place.toFileFormat());
    }

    @Test
    public void copy_createsEqualInstance() {
        Place place = new Place("Marina Bay Sands", "SkyPark");
        Place cloned = place.copy();
        assertEquals(place, cloned);
        assertEquals(place.hashCode(), cloned.hashCode());
    }

    @Test
    public void equals_differentObjects_returnsExpected() {
        Place place1 = new Place("Place A", "Details A");
        Place place2 = new Place("Place A", "Details A");
        Place place3 = new Place("Place B", "Details A");

        assertEquals(place1, place2);
        assertNotEquals(place1, place3);
        assertNotEquals(place1, "Not a Place");
    }
}
