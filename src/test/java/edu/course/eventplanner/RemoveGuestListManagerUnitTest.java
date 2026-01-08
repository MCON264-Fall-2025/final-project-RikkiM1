package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.service.GuestListManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RemoveGuestListManagerUnitTest {
    private GuestListManager guestListManager;
//No test for removing existing or non-existing guests yet.

    @BeforeEach
    void setUp() {
        guestListManager = new GuestListManager();
    }

    @Test
    void removeExistingGuest() {
        Guest guest = new Guest("Alice", "family");
        guestListManager.addGuest(guest);

        Guest copy = new Guest("Alice", "family");
        guestListManager.addGuest(copy);
        assertEquals(1, guestListManager.getGuestCount(), "Guest list should have one guest");
    }

    @Test
    void removeGuestFromEmptyGuestList() {
        // guestListManager is empty at this point
        boolean removed = guestListManager.removeGuest("RikkiMann-family");

        // Check that removeGuest() returns false
        assertFalse(removed, "Removing from empty list should return false");

        // Check that the guest count is still 0
        assertEquals(0, guestListManager.getGuestCount(), "Guest list should still be empty");
    }

    @Test
    void removeGuestFromGuestList() {

        Guest guest = new Guest("RikkiMann", "family");
        guestListManager.addGuest(guest);
        boolean removed = guestListManager.removeGuest("RikkiMann-family");

        assertTrue(removed, "Removing an existing guest should return true");
        assertEquals(0, guestListManager.getGuestCount(), "Guest list should be empty after removal");
        assertNull(guestListManager.findGuest("RikkiMann-family"), "Removed guest should no longer be findable");
    }

    @Test
    void removeGuestThatDoesntExist() {
        // Ensure the guest list is empty (or doesn't contain this guest)
        assertEquals(0, guestListManager.getGuestCount(), "Guest list should start empty");

        // Try to remove a guest that doesn't exist
        boolean removed = guestListManager.removeGuest("Bob-family");

        // Verify that removeGuest() returns false
        assertFalse(removed, "Removing a non-existing guest should return false");

        // Verify that the guest count is still 0
        assertEquals(0, guestListManager.getGuestCount(), "Guest list should remain empty");
    }
}
