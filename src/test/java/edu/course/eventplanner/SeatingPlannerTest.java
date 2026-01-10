package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.SeatingPlanner;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SeatingPlannerTest {
    @Test
    void generateSeating_emptyGuestList_returnsEmptySeating() {
        Venue venue = new Venue("Test Hall", 1000, 100, 10, 10);
        SeatingPlanner planner = new SeatingPlanner(venue);

        Map<Integer, List<Guest>> seating = planner.generateSeating(List.of());

        assertNotNull(seating);
        assertTrue(seating.isEmpty() || seating.values().stream().allMatch(List::isEmpty));
    }
    @Test
    void generateSeating_singleGuest_isSeated() {
        Venue venue = new Venue("Test Hall", 1000, 100, 10, 10);
        SeatingPlanner planner = new SeatingPlanner(venue);

        Map<Integer, List<Guest>> seating =
                planner.generateSeating(List.of(new Guest("Alice", "Family")));

        int totalSeated = seating.values().stream().mapToInt(List::size).sum();
        assertEquals(1, totalSeated);
    }
    @Test
    void generateSeating_doesNotExceedTableCapacity() {
        Venue venue = new Venue("Small Hall", 500, 50, 5, 10);
        SeatingPlanner planner = new SeatingPlanner(venue);

        List<Guest> guests = new ArrayList<>();
        for (int i = 0; i < 45; i++) {
            guests.add(new Guest("Guest" + i, "Group" + (i % 5)));
        }

        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        for (List<Guest> table : seating.values()) {
            assertTrue(table.size() <= venue.getSeatsPerTable());
        }
    }
    @Test
    void generateSeating_allGuestsSeatedExactlyOnce() {
        Venue venue = new Venue("Medium Hall", 1500, 150, 15, 10);
        SeatingPlanner planner = new SeatingPlanner(venue);

        List<Guest> guests = List.of(
                new Guest("Rikki", "family"),
                new Guest("Lea", "family"),
                new Guest("Aviva", "friends")
        );

        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        Set<Guest> seatedGuests = new HashSet<>();
        for (List<Guest> table : seating.values()) {
            seatedGuests.addAll(table);
        }

        assertEquals(guests.size(), seatedGuests.size());
        assertTrue(seatedGuests.containsAll(guests));
    }
    @Test
    void generateSeating_groupsSitTogether() {
        Venue venue = new Venue("Test Venue", 1000, 20, 5, 4);
        SeatingPlanner planner = new SeatingPlanner(venue);

        List<Guest> guests = List.of(
                new Guest("A", "family"),
                new Guest("B", "family"),
                new Guest("C", "family"),
                new Guest("D", "friends")
        );

        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        for (List<Guest> table : seating.values()) {
            if (table.isEmpty()) continue;

            String group = table.get(0).getGroupTag();
            for (Guest g : table) {
                assertEquals(group, g.getGroupTag());
            }
        }
    }@Test
    void generateSeating_doesNotUseMoreTablesThanAvailable() {
        Venue venue = new Venue("Small Hall", 500, 50, 5, 10);
        SeatingPlanner planner = new SeatingPlanner(venue);

        List<Guest> guests = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            guests.add(new Guest("Guest" + i, "Group1"));
        }

        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        assertTrue(seating.size() <= venue.getTables());
    }

}
