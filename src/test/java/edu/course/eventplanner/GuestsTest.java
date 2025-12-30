package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.service.GuestListManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GuestsTest {
    @Test
    void testAddGuest() {
        GuestListManager guestListManager = new GuestListManager();
        Guest g = new Guest("Rikki", "Mann");
        guestListManager.addGuest(g);

        //swicth this that not use the find method. make sure linked list and map were done correctly
        Guest found = guestListManager.findGuest("Rikki");
        assertNotNull(found, "Guest shoudl be found.");
        assertEquals("Rikki", found.getName(), "Guest name should be Rikki");
        assertEquals("family", found.getGroupTag(), "Guest should be family");
    }
}
