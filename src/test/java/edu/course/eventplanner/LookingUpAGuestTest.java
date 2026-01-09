package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.service.GuestListManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LookingUpAGuestTest {

    private GuestListManager guestListManager;

    @BeforeEach
    void setup() {
        guestListManager = new GuestListManager();
    }

    @Test
    void testGetAllGuests() {
        Guest guest1 = new Guest("Alice", "family");
        Guest guest2 = new Guest("Bob", "friends");
        guestListManager.addGuest(guest1);
        guestListManager.addGuest(guest2);

        List<Guest> allGuests = guestListManager.getAllGuests();
        assertEquals(2, allGuests.size(), "Should return all guests");
        assertTrue(allGuests.contains(guest1));
        assertTrue(allGuests.contains(guest2));
    }
    @Test
    void lookupGuestCaseInsensitive() {
        Guest guest = new Guest("Alice", "Family");
        guestListManager.addGuest(guest);

        Guest found = guestListManager.findGuest("ALICE-FAMILY");
        assertNotNull(found, "Lookup should be case-insensitive");
        assertEquals(guest, found);
    }

    @Test
    void testLookingUpAGuestThatDoesNotExist() {
        Guest guest = guestListManager.findGuest("AliceSmith-family");
        assertNull(guest, "Looking up a non-existing guest should return null");
    }

    @Test
    void testLookingUpAGuestThatExists() {
        Guest newGuest = new Guest("AliceSmith", "family");
        guestListManager.addGuest(newGuest);

        Guest guest = guestListManager.findGuest("AliceSmith-family");
        assertNotNull(guest, "Looking up an existing guest should return the guest object");
        assertEquals("AliceSmith", guest.getName());
        assertEquals("family", guest.getGroupTag());
    }
    @Test
    void addNullGuestDoesNothing() {
        guestListManager.addGuest(null);
        assertTrue(guestListManager.getAllGuests().isEmpty(), "Adding null should not add any guest");
    }

    @Test
    void lookupNullKeyReturnsNull() {
        Guest guest = guestListManager.findGuest(null);
        assertNull(guest, "Looking up null key should return null");
    }
    @Test
    void addingDuplicateGuestReplacesOrIgnores() {
        Guest guest1 = new Guest("Alice", "family");
        Guest guest2 = new Guest("Alice", "family"); // same key
        guestListManager.addGuest(guest1);
        guestListManager.addGuest(guest2);

        List<Guest> allGuests = guestListManager.getAllGuests();
        assertEquals(1, allGuests.size(), "Duplicate guest should not create a new entry");
        assertEquals(guest2, allGuests.get(0), "Latest added guest replaces the previous one");
    }
}