package edu.course.eventplanner;

import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.VenueSelector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SelectingAVenueWithinBudget {

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

    @Test
    public void selectAVenueWithinBudget() {
        double budget = 3000;
        int guests = 50;

        VenueSelector venueSelector = new VenueSelector(venues);
        Venue selectedVenue = venueSelector.selectVenue(budget, guests);

        // Verify that a venue is selected
        assertNotNull(selectedVenue, "A venue should be selected within the budget and guest count");

        // Verify that the venue fits the budget
        assertTrue(selectedVenue.getCost() <= budget, "Selected venue should be within the budget");

        // Verify that the venue fits the guest count
        assertTrue(selectedVenue.getCapacity() >= guests, "Selected venue should accommodate all guests");
    }
    @Test
    public void selectAVenueWithTooLowBudget() {
        double budget = 100; // Too low for any venue
        int guests = 10;

        VenueSelector venueSelector = new VenueSelector(venues);
        Venue selectedVenue = venueSelector.selectVenue(budget, guests);

        assertNull(selectedVenue, "No venue should be selected if budget is too low");
    }
    @Test
    public void selectAVenueWithTooManyGuests() {
        double budget = 10000;
        int guests = 1000; // More than any venue capacity

        VenueSelector venueSelector = new VenueSelector(venues);
        Venue selectedVenue = venueSelector.selectVenue(budget, guests);

        assertNull(selectedVenue, "No venue should be selected if guest count exceeds all capacities");
    }
    @Test
    public void selectCheapestVenueWhenMultipleFit() {
        double budget = 3000;
        int guests = 40;

        VenueSelector venueSelector = new VenueSelector(venues);
        Venue selectedVenue = venueSelector.selectVenue(budget, guests);

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