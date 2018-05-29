package db.models;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class TripEntityTest {

    //only testing toString-method
    //other methods are autogenerated

    @Test
    public void toStringTest() {
        TripEntity testTrip = new TripEntity(1234567890, "TestUser", "Ort1", "Ort2", 100f, LocalDate.of(2018, 1, 1));

        String expectedString = "(1234567890, TestUser, Ort1, Ort2, 100.0, 2018-01-01)";

        assertEquals(testTrip.toString(), expectedString);
    }
}