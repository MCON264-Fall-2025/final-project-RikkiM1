package edu.course.eventplanner;

import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.SeatingPlanner;
import org.junit.jupiter.api.BeforeEach;
import edu.course.eventplanner.model.Guest;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
public class SeatingGuestsByGroupTest {



    private Venue venue;
    private SeatingPlanner seatingPlanner;
    private List<Guest> guests;
    private GuestListManager guestListManager;

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
    void seatingEmptyGuets(){
        Map<Integer, List<Guest>> seating = seatingPlanner.generateSeating(guests);
        // The seating map should have no guests seated
        int totalSeated = 0;
        for (List<Guest> table : seating.values()) {
            totalSeated += table.size();
        }

        assertEquals(0, totalSeated, "There should be no guests seated");
    }
    @Test
    void AllGuestsAreSeatedExactlyOnce() {
        guests.add(new Guest("Rikki", "family"));
        guests.add(new Guest("Lea", "family"));
        guests.add(new Guest("Aviva", "friends"));

        Map<Integer, List<Guest>> seating = seatingPlanner.generateSeating(guests);

        HashSet<Guest> seatedGuests = new HashSet<>();
        for (List<Guest> table : seating.values()) {
            seatedGuests.addAll(table);
        }

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
    void seatingDoesNotExceedTableCapacity(){
        //add more guests than fit on a table
        guests.add(new Guest("Rikki", "family"));
        guests.add(new Guest("Lea", "family"));
        guests.add(new Guest("Aviva", "family"));
        guests.add(new Guest("Gabriella", "family"));
        guests.add(new Guest("Dovi", "family"));
        guests.add(new Guest("Ezra", "family"));

        //every time loop through the tables ensure they dont have more than capacity per the table
        Map<Integer, List<Guest>> seating = seatingPlanner.generateSeating(guests);
        for (List<Guest> table : seating.values()) {
            assertTrue(table.size() <= venue.getSeatsPerTable());
        }
    }
    }

