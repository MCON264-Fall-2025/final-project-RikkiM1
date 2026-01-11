package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.SeatingPlanner;
import edu.course.eventplanner.service.VenueSelector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SeatingPlannerTest {

    private Venue venue;
    private SeatingPlanner seatingPlanner;
    private List<Guest> guests;

    @BeforeEach
    void setUp() {
        // set up venue for all tests
        venue = new Venue(
                "Test Venue",
                1000,   // cost
                20,     // capacity
                5,      // tables
                4       // seats per table
        );
        seatingPlanner = new SeatingPlanner(venue);
        guests = new ArrayList<>();
    }


    @Test
    void seatingEmptyGuests() {
        Map<Integer, List<Guest>> seating = seatingPlanner.generateSeating(guests);

        int totalSeated = seating.values().stream()
                .mapToInt(List::size)
                .sum();

        assertEquals(0, totalSeated, "There should be no guests seated");
    }

    @Test
    void allGuestsAreSeatedExactlyOnce() {
        guests.add(new Guest("Rikki", "family"));
        guests.add(new Guest("Lea", "family"));
        guests.add(new Guest("Aviva", "friends"));

        Map<Integer, List<Guest>> seating = seatingPlanner.generateSeating(guests);

        Set<Guest> seatedGuests = new HashSet<>();
        seating.values().forEach(seatedGuests::addAll);

        assertEquals(guests.size(), seatedGuests.size(), "All guests should be seated exactly once");
        for (Guest g : guests) {
            assertTrue(seatedGuests.contains(g), "Guest " + g.getName() + " should be seated");
        }
    }


    @Test
    void allGuestsFromTheSameGroupSitTogether() {
        guests.add(new Guest("Rikki", "family"));
        guests.add(new Guest("Lea", "family"));
        guests.add(new Guest("Dovi", "family"));
        guests.add(new Guest("Aviva", "family"));
        guests.add(new Guest("Zahava", "friends"));

        Map<Integer, List<Guest>> seating = seatingPlanner.generateSeating(guests);

        for (List<Guest> table : seating.values()) {
            if (table.isEmpty()) continue;
            String groupTag = table.get(0).getGroupTag();
            for (Guest g : table) {
                assertEquals(groupTag, g.getGroupTag(), "Guests at the same table should have the same group tag");
            }
        }
    }

    @Test
    void seatingDoesNotExceedTableCapacity() {
        guests.add(new Guest("Rikki", "family"));
        guests.add(new Guest("Lea", "family"));
        guests.add(new Guest("Aviva", "family"));
        guests.add(new Guest("Gabriella", "family"));
        guests.add(new Guest("Dovi", "family"));
        guests.add(new Guest("Ezra", "family"));

        Map<Integer, List<Guest>> seating = seatingPlanner.generateSeating(guests);
        for (List<Guest> table : seating.values()) {
            assertTrue(table.size() <= venue.getSeatsPerTable(), "Table should not exceed its capacity");
        }
    }
    @Test
    void venueSelectorNoAvailableVenue() {
        List<Venue> venues = List.of(new Venue("Tiny", 1000, 5, 1, 2));
        VenueSelector selector = new VenueSelector(venues);
        Venue result = selector.selectVenue(50, 10); // too low budget, too many guests
        assertNull(result, "No venue should be selected if none fits");
    }
}