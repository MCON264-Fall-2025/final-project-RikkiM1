package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.service.GuestListManager;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GuestListMangerTest {
    @Test
    void testAddGuestStoresInBothStructures() {
        // arrange
        GuestListManager<Guest> manager = new GuestListManager<>();
        Guest guest = new Guest("Aviva", "Family");

        // Act
        manager.addGuest(guest);

        //assert
        assertEquals(1, manager.size(), "Size should be 1");
        assertEquals(guest, manager.findGuest("Aviva"), "Should be found in Map via findGuest");
        assertTrue(manager.getAllGuests().contains(guest), "Should be present in the LinkedList");
    }

    @Test
    void testDuplicateNameIsNotAdded() {
        //arrange
        GuestListManager<Guest> manager = new GuestListManager<>();
        Guest guest1 = new Guest("Aviva", "Family");
        Guest guest2 = new Guest("Aviva", "Work"); // Same name, different group

        // act
        manager.addGuest(guest1);
        manager.addGuest(guest2);

        //assert
        assertEquals(1, manager.size(), "Should ignore duplicates based on the Name key in Map");
        assertEquals("Family", manager.findGuest("Aviva").getGroupTag(), "The original guest should remain");
    }

    @Test
    void testRemoveGuestSuccessfullySyncs() {
        // Arrange
        GuestListManager<Guest> manager = new GuestListManager<>();
        Guest guest = new Guest("Aviva", "Friends");
        manager.addGuest(guest);

        // Act
        boolean result = manager.removeGuest("Aviva");

        // Assert
        assertTrue(result, "Remove should return true for existing guest");
        assertEquals(0, manager.size(), "Size should be 0 after removal");
        assertNull(manager.findGuest("Bob"), "Guest should no longer be in the Map");
        assertFalse(manager.getAllGuests().contains(guest), "Guest should no longer be in the LinkedList");
    }

    @Test
    void testRemoveNonExistentGuestReturnsFalse() {
        // Arrange
        GuestListManager<Guest> manager = new GuestListManager<>();

        // Act
        boolean result = manager.removeGuest("Mikey");

        // Assert
        assertFalse(result, "Removing a non-existent guest should return false");
        assertEquals(0, manager.size());
    }

    @Test
    void testFindGuestReturnsCorrectObject() {
        // Arrange
        GuestListManager<Guest> manager = new GuestListManager<>();
        Guest guest = new Guest("Aviva", "Co-Worker");
        manager.addGuest(guest);

        // Act
        Guest found = manager.findGuest("Aviva");

        // Assert
        assertNotNull(found);
        assertEquals("Co-Worker", found.getGroupTag());
        assertEquals("Aviva", found.getName());
    }

    @Test
    void testOrderIsMaintainedWithAddLast() {
        // Arrange
        GuestListManager<Guest> manager = new GuestListManager<>();
        Guest first = new Guest("First", "Group");
        Guest second = new Guest("Second", "Group");

        // Act
        manager.addGuest(first);
        manager.addGuest(second);

        // Assert
        List<Guest> list = manager.getAllGuests();
        assertEquals("First", list.get(0).getName(), "The first guest added should be at index 0");
        assertEquals("Second", list.get(1).getName(), "The second guest added should be at index 1");
    }

}
