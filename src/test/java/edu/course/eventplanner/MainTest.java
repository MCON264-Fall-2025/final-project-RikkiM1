package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.TaskManager;
import edu.course.eventplanner.util.Generators;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    GuestListManager guestListManager;
    TaskManager taskManager;

    @BeforeEach
    void setup() {
        guestListManager = new GuestListManager();
        taskManager = new TaskManager();
    }

    @Test
    void testAddAndRemoveGuest() {
        // Add guest using Main helper
        Scanner addScanner = new Scanner("Alice\nfamily\n");
        Main.addGuestManually(addScanner, guestListManager);
        assertEquals(1, guestListManager.getGuestCount());

        // Remove guest using Main helper
        Scanner removeScanner = new Scanner("Alice\nfamily\n");
        Main.removeGuest(removeScanner, guestListManager);
        assertEquals(0, guestListManager.getGuestCount());
    }

    @Test
    void testAddPreparationTask() {
        // Add task using Main helper
        Scanner scanner = new Scanner("Decorate hall\n");
        Main.addPreparationTask(scanner, taskManager);
        assertEquals(1, taskManager.remainingTaskCount());
        assertEquals("Decorate hall", taskManager.executeNextTask().getDescription());
    }

    @Test
    void testExecuteAndUndoTask() {
        Task t1 = new Task("Task 1");
        Task t2 = new Task("Task 2");

        taskManager.addTask(t1);
        taskManager.addTask(t2);

        // Execute next task
        Task executed = taskManager.executeNextTask();
        assertEquals(t1, executed);
        assertEquals(1, taskManager.remainingTaskCount());

        // Undo last task
        Task undone = taskManager.undoLastTask();
        assertEquals(t1, undone);
        assertEquals(2, taskManager.remainingTaskCount());
    }
    @Test
    void testRemoveNonExistentGuest() {
        Scanner scanner = new Scanner("Bob\nfriends\n");
        Main.removeGuest(scanner, guestListManager); // No guests yet
        assertEquals(0, guestListManager.getGuestCount());
    }
    @Test
    void testExecuteNextAndUndoViaMain() {
        taskManager.addTask(new Task("Task A"));
        taskManager.addTask(new Task("Task B"));

        // Execute next task via Main
        Main.executeNextTask(taskManager); // prints "Task A"
        assertEquals(1, taskManager.remainingTaskCount());

        // Undo last task via Main
        Main.undoLastTask(taskManager); // prints "Task A"
        assertEquals(2, taskManager.remainingTaskCount());
    }
    @Test
    void testGenerateSeatingWithNullVenue() {
        Main.generateSeating(null, guestListManager); // Should just print warning, not crash
    }
    @Test
    void testLoadSampleData() {
        Main.loadSampleData(taskManager, guestListManager);
        assertEquals(1, taskManager.remainingTaskCount());
        assertEquals("Loaded sample data", taskManager.executeNextTask().getDescription());
    }

    @Test
    void testSelectVenue() {
        List<Venue> venues = Generators.generateVenues();

        // Use Main's selectVenue helper; Scanner can be empty because Main handles null automatically
        Venue selected = Main.selectVenue(new Scanner(""), venues, 10000, 5);
        assertNotNull(selected);
        assertTrue(selected.getCapacity() >= 5);
    }

    @Test
    void testGenerateSeating() {
        // Generate guests manually like main()
        List<Guest> guests = Generators.GenerateGuests(6);
        for (Guest g : guests) {
            guestListManager.addGuest(g);
        }

        List<Venue> venues = Generators.generateVenues();
        Venue selected = Main.selectVenue(new Scanner(""), venues, 10000, 6);
        assertNotNull(selected);

        // Call Main's generateSeating (prints seating)
        Main.generateSeating(selected, guestListManager);

        // Check venue has enough seats
        int totalSeats = selected.getTables() * selected.getSeatsPerTable();
        assertTrue(totalSeats >= 6);
    }

    @Test
    void testPrintEventSummary() {
        Main.printEventSummary(guestListManager, taskManager);
    }
}