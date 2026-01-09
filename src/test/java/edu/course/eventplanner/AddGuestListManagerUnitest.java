package edu.course.eventplanner;


import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.service.GuestListManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    @Test
    void testAddDuplicateGuestDoesNotIncreaseCount() {
        Guest guest = new Guest("Alice", "family");

        guestListManager.addGuest(guest);
        guestListManager.addGuest(guest); // duplicate

        assertEquals(1, guestListManager.getGuestCount(),
                "Duplicate guest should not be added");
    }
    @Test
    void testRemoveGuestByFullKey() {
        Guest guest = new Guest("Alice", "family");
        guestListManager.addGuest(guest);

        boolean removed = guestListManager.removeGuest("alice-family");

        assertEquals(true, removed);
        assertEquals(0, guestListManager.getGuestCount());
    }
    @Test
    void testRemoveGuestByFullKeyNotFound() {
        boolean removed = guestListManager.removeGuest("ghost-family");
        assertEquals(false, removed);
    }
    @Test
    void testRemoveGuestByNameOnly() {
        Guest guest = new Guest("Alice", "family");
        guestListManager.addGuest(guest);

        boolean removed = guestListManager.removeGuest("Alice");

        assertEquals(true, removed);
        assertEquals(0, guestListManager.getGuestCount());
    }
    @Test
    void testFindGuestByFullKey() {
        Guest guest = new Guest("Alice", "family");
        guestListManager.addGuest(guest);

        Guest found = guestListManager.findGuest("alice-family");

        assertNotNull(found);
        assertEquals("Alice", found.getName());
    }
    @Test
    void testFindGuestNotFound() {
        Guest found = guestListManager.findGuest("Nobody");
        assertEquals(null, found);
    }
    @Test
    void testFindGuestByNameOnly() {
        Guest guest = new Guest("Alice", "family");
        guestListManager.addGuest(guest);

        Guest found = guestListManager.findGuest("Alice");

        assertNotNull(found);
        assertEquals("Alice", found.getName());
    }
    @Test
    void testRemoveGuestByNameOnlyNotFound() {
        Guest guest = new Guest("Alice", "family");
        guestListManager.addGuest(guest);

        boolean removed = guestListManager.removeGuest("Bob");

        assertEquals(false, removed);
        assertEquals(1, guestListManager.getGuestCount());
    }
    
    @Test
    void testFindGuestCaseInsensitive() {
        Guest guest = new Guest("Alice", "Family");
        guestListManager.addGuest(guest);

        Guest found = guestListManager.findGuest("ALICE-FAMILY");
        assertNotNull(found);
        assertEquals("Alice", found.getName());
    }
    @Test
    void testRemoveGuestWithSameNameDifferentType() {
        Guest g1 = new Guest("Alice", "family");
        Guest g2 = new Guest("Alice", "friend");
        guestListManager.addGuest(g1);
        guestListManager.addGuest(g2);

        boolean removed = guestListManager.removeGuest("Alice-family");
        assertEquals(true, removed);
        assertEquals(1, guestListManager.getGuestCount());

        // The other Alice should still exist
        assertNotNull(guestListManager.findGuest("Alice-friend"));
    }
    @Test
    void testAddManyGuests() {
        for (int i = 1; i <= 100; i++) {
            guestListManager.addGuest(new Guest("Guest" + i, "group" + i));
        }
        assertEquals(100, guestListManager.getGuestCount());
    }
}
