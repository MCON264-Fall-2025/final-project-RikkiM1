package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.service.GuestListManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AddGuestListManagerUnitTest {

    private GuestListManager guestListManager;



    @BeforeEach
    void setUp() {
        guestListManager = new GuestListManager();
    }

    @Test
    void testNotNullGuestListManager() {
        assertNotNull(guestListManager);
    }

    @Test
    void testAddGuestToExistingGuest() {
        Guest guest = new Guest("Alice", "family");
        guestListManager.addGuest(guest);
        assertEquals(1, guestListManager.getGuestCount(), "Guest count should be 1");
        Guest firstGuest = guestListManager.getAllGuests().get(0);
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

        // Add guests one at a time
        guestListManager.addGuest(g1);
        guestListManager.addGuest(g2);
        guestListManager.addGuest(g3);

        // Check total count
        assertEquals(3, guestListManager.getGuestCount(), "Guest count should be 3");

        // Check order in the linked list
        assertEquals("Alice", guestListManager.getAllGuests().get(0).getName());
        assertEquals("Bob", guestListManager.getAllGuests().get(1).getName());
        assertEquals("Charlie", guestListManager.getAllGuests().get(2).getName());
    }



}
