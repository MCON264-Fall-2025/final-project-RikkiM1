package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.service.GuestListManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuestListManagerTest {

    private GuestListManager guestListManager;

    @BeforeEach
    void setUp() {
        guestListManager = new GuestListManager();
    }

    @Test
    void addGuest_increasesCount() {
        guestListManager.addGuest(new Guest("Alice", "family"));
        assertEquals(1, guestListManager.getGuestCount());
    }

    @Test
    void addDuplicateGuest_doesNotIncreaseCount() {
        guestListManager.addGuest(new Guest("Alice", "family"));
        guestListManager.addGuest(new Guest("Alice", "family"));
        assertEquals(1, guestListManager.getGuestCount());
    }

    @Test
    void removeGuest_existingGuest_returnsTrue() {
        guestListManager.addGuest(new Guest("Alice", "family"));
        assertTrue(guestListManager.removeGuest("alice-family"));
    }

    @Test
    void removeGuest_nonExisting_returnsFalse() {
        assertFalse(guestListManager.removeGuest("ghost-family"));
    }

    @Test
    void findGuest_caseInsensitive() {
        guestListManager.addGuest(new Guest("Alice", "Family"));
        Guest found = guestListManager.findGuest("ALICE-FAMILY");
        assertNotNull(found);
    }

    @Test
    void addNullGuest_doesNothing() {
        guestListManager.addGuest(null);
        assertEquals(0, guestListManager.getGuestCount());
    }
}