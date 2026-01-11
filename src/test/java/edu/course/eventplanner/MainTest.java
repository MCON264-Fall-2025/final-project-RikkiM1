package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        // Simulate adding a guest
        Scanner scannerAdd = new Scanner("Alice\nfamily\n");
        Main.addGuestManually(scannerAdd, guestListManager);
        assertEquals(1, guestListManager.getGuestCount());

        // Simulate removing the same guest
        Scanner scannerRemove = new Scanner("Alice\nfamily\n");
        Main.removeGuest(scannerRemove, guestListManager);
        assertEquals(0, guestListManager.getGuestCount());
    }

    @Test
    void testExecuteAndUndoTask() {
        Task t1 = new Task("Task 1");
        Task t2 = new Task("Task 2");

        taskManager.addTask(t1);
        taskManager.addTask(t2);

        // Execute next task (should remove t1 from pending)
        Task executed = taskManager.executeNextTask();
        assertEquals(t1, executed);

        // Only t2 should remain
        assertEquals(1, taskManager.remainingTaskCount());

        // Undo last executed task (t1 back to pending)
        Task undone = taskManager.undoLastTask();
        assertEquals(t1, undone);

        // Now both t1 and t2 are pending
        assertEquals(2, taskManager.remainingTaskCount());
    }

    @Test
    void testAddPreparationTask() {
        // Simulate entering preparation task
        Scanner scanner = new Scanner("Prepare flowers\n");
        Main.addPreparationTask(scanner, taskManager);

        // Check task is added
        assertEquals(1, taskManager.remainingTaskCount());
        assertEquals("Prepare flowers", taskManager.executeNextTask().getDescription());
    }

    @Test
    void testPrintEventSummary() {
        // Just ensure no exception is thrown
        Main.printEventSummary(guestListManager, taskManager);
    }
}