package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.service.GuestListManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GuestListManagerTest {

    private GuestListManager guestListManager;

    @BeforeEach
    void setUp() {
        guestListManager = new GuestListManager();
    }



    @Test
    void testAddGuestToExistingGuest() {
        Guest guest = new Guest("Alice", "family");
        guestListManager.addGuest(guest);
        assertEquals(1, guestListManager.getGuestCount(), "Guest count should be 1");
        Guest firstGuest = guestListManager.getAllGuests().getFirst();
        assertEquals("Alice", firstGuest.getName());
    }

    @Test
    void testAddOneGuestAtATime() {
        Guest guest = new Guest("Alice", "family");
        guestListManager.addGuest(guest);
        assertEquals(1, guestListManager.getGuestCount(), "Guest count should be 1");
        Guest firstGuest = guestListManager.getAllGuests().get(0);
        assertEquals("Alice", firstGuest.getName());
    }

    @Test
    void testAddMultipleGuestsOneAtATime() {
        Guest g1 = new Guest("Alice", "family");
        Guest g2 = new Guest("Bob", "friends");
        Guest g3 = new Guest("Charlie", "coworkers");

        guestListManager.addGuest(g1);
        guestListManager.addGuest(g2);
        guestListManager.addGuest(g3);

        assertEquals(3, guestListManager.getGuestCount(), "Guest count should be 3");
        assertEquals("Alice", guestListManager.getAllGuests().get(0).getName());
        assertEquals("Bob", guestListManager.getAllGuests().get(1).getName());
        assertEquals("Charlie", guestListManager.getAllGuests().get(2).getName());
    }

    @Test
    void testAddDuplicateGuestDoesNotIncreaseCount() {
        Guest guest = new Guest("Alice", "family");
        guestListManager.addGuest(guest);
        guestListManager.addGuest(guest); // duplicate
        assertEquals(1, guestListManager.getGuestCount(), "Duplicate guest should not be added");
    }

    @Test
    void addMultipleDuplicateGuests() {
        Guest g1 = new Guest("Charlie", "Coworkers");
        Guest g2 = new Guest("Charlie", "Coworkers");
        Guest g3 = new Guest("Charlie", "Coworkers");

        guestListManager.addGuest(g1);
        guestListManager.addGuest(g2);
        guestListManager.addGuest(g3);

        assertEquals(1, guestListManager.getGuestCount(), "Duplicate guests should not increase count");
        Guest stored = guestListManager.getAllGuests().get(0);
        assertEquals(g3, stored, "Latest added guest replaces previous duplicates");
    }

    @Test
    void addNullGuestDoesNothing() {
        guestListManager.addGuest(null);
        assertTrue(guestListManager.getAllGuests().isEmpty(), "Adding null should not add any guest");
    }

    @Test
    void testAddManyGuests() {
        for (int i = 1; i <= 20; i++) {
            guestListManager.addGuest(new Guest("Guest" + i, "group" + i));
        }
        assertEquals(20, guestListManager.getGuestCount());
    }

    // ----------------- Remove Guest Tests -----------------
    @Test
    void testRemoveGuestByFullKey() {
        Guest guest = new Guest("Alice", "family");
        guestListManager.addGuest(guest);
        boolean removed = guestListManager.removeGuest("alice-family");
        assertTrue(removed);
        assertEquals(0, guestListManager.getGuestCount());
    }

    @Test
    void removeGuestWithEmptyKeyReturnsFalse() {
        boolean removed = guestListManager.removeGuest("");
        assertFalse(removed);
    }

    @Test
    void removeGuestCaseNotSensitive() {
        Guest guest = new Guest("Bob", "Friends");
        guestListManager.addGuest(guest);
        boolean removed = guestListManager.removeGuest("bob-FRIENDS");
        assertTrue(removed);
        assertEquals(0, guestListManager.getGuestCount());
    }

    @Test
    void removeMultipleGuestsInSuccession() {
        Guest g1 = new Guest("Alice", "family");
        Guest g2 = new Guest("Bob", "friends");
        guestListManager.addGuest(g1);
        guestListManager.addGuest(g2);
        assertTrue(guestListManager.removeGuest("Alice-family"));
        assertEquals(1, guestListManager.getGuestCount());
        assertTrue(guestListManager.removeGuest("Bob-friends"));
        assertEquals(0, guestListManager.getGuestCount());
    }

    @Test
    void removeGuestFromEmptyGuestList() {
        boolean removed = guestListManager.removeGuest("RikkiMann-family");
        assertFalse(removed);
        assertEquals(0, guestListManager.getGuestCount());
    }

    @Test
    void removeGuestThatDoesntExist() {
        boolean removed = guestListManager.removeGuest("Bob-family");
        assertFalse(removed);
        assertEquals(0, guestListManager.getGuestCount());
    }

    @Test
    void removeGuestFromGuestList() {
        Guest guest = new Guest("RikkiMann", "family");
        guestListManager.addGuest(guest);
        boolean removed = guestListManager.removeGuest("RikkiMann-family");
        assertTrue(removed);
        assertEquals(0, guestListManager.getGuestCount());
        assertNull(guestListManager.findGuest("RikkiMann-family"));
    }

    @Test
    void testRemoveGuestWithSameNameDifferentType() {
        Guest g1 = new Guest("Alice", "family");
        Guest g2 = new Guest("Alice", "friend");
        guestListManager.addGuest(g1);
        guestListManager.addGuest(g2);

        boolean removed = guestListManager.removeGuest("Alice-family");
        assertTrue(removed);
        assertEquals(1, guestListManager.getGuestCount());
        assertNotNull(guestListManager.findGuest("Alice-friend"));
    }

    @Test
    void testFindGuestNotFound() {
        Guest found = guestListManager.findGuest("Nobody");
        assertNull(found);
    }


    @Test
    void lookupGuestCaseInsensitive() {
        Guest guest = new Guest("Alice", "Family");
        guestListManager.addGuest(guest);
        Guest found = guestListManager.findGuest("ALICE-FAMILY");
        assertNotNull(found);
        assertEquals(guest, found);
    }

}