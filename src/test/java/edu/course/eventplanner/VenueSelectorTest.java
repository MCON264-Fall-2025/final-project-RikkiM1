package edu.course.eventplanner;

import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.VenueSelector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VenueSelectorTest {

    private List<Venue> venues;

    @BeforeEach
    void setup() {
        // Use the same venues your app generates
        venues = List.of(
                new Venue("Chynka", 1500, 40, 5, 8),
                new Venue("The Palace", 2500, 60, 8, 8),
                new Venue("El Caribe", 5000, 120, 15, 8)
        );
    }
    //checks that the methods in Venue are correct.

@Test
void getsNameCorrectly(){
    Venue venue = new Venue(
            "Test Venue",
            1500.0,
            50,
            5,
            10
    );
    assertEquals("Test Venue", venue.getName());
}
    @Test
    void getsCostCorrectly(){
        Venue venue = new Venue(
                "Test Venue",
                1500.0,
                50,
                5,
                10
        );
        assertEquals("1500", venue.getCost());
    }
    @Test
    void getsCapacityCorrectly(){
        Venue venue = new Venue(
                "Test Venue",
                1500.0,
                50,
                5,
                10
        );
        assertEquals("50", venue.getCapacity());
    }
    @Test
    void getsTablesCorrectly(){
        Venue venue = new Venue(
                "Test Venue",
                1500.0,
                50,
                5,
                10
        );
        assertEquals("5", venue.getTables());
    }
    @Test
    void getsSeatsPerTableCorrectly(){
        Venue venue = new Venue(
                "Test Venue",
                1500.0,
                50,
                5,
                10
        );
        assertEquals("10", venue.getSeatsPerTable());
    }
    //make sure the budget chosen is within budget
    @Test
    void selectAVenueWithinBudget() {
        double budget = 3000;
        int guests = 50;

        VenueSelector venueSelector = new VenueSelector(venues);
        Venue selectedVenue = venueSelector.selectVenue(budget, guests);

        assertNotNull(selectedVenue, "A venue should be selected within the budget and guest count");
        //check the venue and budget
        assertTrue(selectedVenue.getCost() <= budget, "Selected venue should be within the budget");
        assertTrue(selectedVenue.getCapacity() >= guests, "Selected venue should accommodate all guests");
    }

    @Test
    void selectAVenueWithTooLowBudget() {
        double budget = 100;
        int guests = 10;

        VenueSelector venueSelector = new VenueSelector(venues);
        Venue selectedVenue = venueSelector.selectVenue(budget, guests);
//no venue should be chosen
        assertNull(selectedVenue, "No venue should be selected if budget is too low");
    }

    @Test
    void selectAVenueWithTooManyGuests() {
        double budget = 10000;
        int guests = 1000;

        VenueSelector venueSelector = new VenueSelector(venues);
        Venue selectedVenue = venueSelector.selectVenue(budget, guests);
//no venue should be chosen because of capacity
        assertNull(selectedVenue, "No venue should be selected if guest count exceeds all capacities");
    }

    @Test
    void selectCheapestVenueWhenMultipleFit() {
        double budget = 3000;
        int guests = 40;

        VenueSelector venueSelector = new VenueSelector(venues);
        Venue selectedVenue = venueSelector.selectVenue(budget, guests);
//choosing the best venue
        assertNotNull(selectedVenue, "Venue should be selected");
        assertEquals("Chynka", selectedVenue.getName(), "Should pick the cheapest venue that fits");
    }

    @Test
    void selectVenueFromEmptyList() {
        VenueSelector venueSelector = new VenueSelector(List.of());
        Venue selectedVenue = venueSelector.selectVenue(1000, 10);
        assertNull(selectedVenue, "Selecting from an empty venue list should return null");
    }
}