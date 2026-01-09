package edu.course.eventplanner;


import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.service.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExecutingAndUndoingTasks {

    private TaskManager taskManager;

    @BeforeEach
    void setup() {
        taskManager = new TaskManager();
    }

    @Test
    void executeNextTaskOnEmptyManager() {
        Task task = taskManager.executeNextTask();
        assertNull(task, "Executing a task on an empty manager should return null");
        assertEquals(0, taskManager.remainingTaskCount(), "No tasks should remain");
    }
    @Test
    void addNullTaskThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> taskManager.addTask(null));
    }
    @Test
    void undoAndReexecuteMultipleTasks() {
        Task task1 = new Task("Task 1");
        Task task2 = new Task("Task 2");
        Task task3 = new Task("Task 3");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        taskManager.executeNextTask(); // task1
        taskManager.executeNextTask(); // task2
        taskManager.executeNextTask(); // task3

        // Undo all
        taskManager.undoLastTask(); // task3
        taskManager.undoLastTask(); // task2
        taskManager.undoLastTask(); // task1

        assertEquals(task1, taskManager.executeNextTask());
        assertEquals(task2, taskManager.executeNextTask());
        assertEquals(task3, taskManager.executeNextTask());
    }
    @Test
    void executeTasksInCorrectOrder() {
        Task task1 = new Task("Task 1");
        Task task2 = new Task("Task 2");
        Task task3 = new Task("Task 3");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        assertEquals(task1, taskManager.executeNextTask());
        assertEquals(task2, taskManager.executeNextTask());
        assertEquals(task3, taskManager.executeNextTask());
    }
    @Test
    void addAndExecuteSingleTask() {
        Task task = new Task("Sample Task");
        taskManager.addTask(task);

        assertEquals(1, taskManager.remainingTaskCount(), "Task should be added");

        Task executed = taskManager.executeNextTask();
        assertEquals(task, executed, "Executed task should match added task");
        assertEquals(0, taskManager.remainingTaskCount(), "No tasks should remain after execution");
    }

    @Test
    void undoLastTaskAfterExecution() {
        Task task1 = new Task("Task 1");
        Task task2 = new Task("Task 2");
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Task executed = taskManager.executeNextTask(); // execute Task 1
        assertEquals(task1, executed);

        Task undone = taskManager.undoLastTask();
        assertEquals(task1, undone, "Undo should return the last executed task");

        assertEquals(2, taskManager.remainingTaskCount(), "Task should be back in the upcoming queue");
    }

    @Test
    void undoWithoutExecuting() {
        Task task = new Task("Task 1");
        taskManager.addTask(task);

        Task undone = taskManager.undoLastTask();
        assertNull(undone, "Undo without any executed tasks should return null");
        assertEquals(1, taskManager.remainingTaskCount(), "Task should still be in the upcoming queue");
    }

    @Test
    void executeMultipleTasksAndUndoSome() {
        Task task1 = new Task("Task 1");
        Task task2 = new Task("Task 2");
        Task task3 = new Task("Task 3");

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        // Execute first two tasks
        taskManager.executeNextTask(); // Task 1
        taskManager.executeNextTask(); // Task 2

        assertEquals(1, taskManager.remainingTaskCount(), "One task should remain");

        // Undo last executed task (Task 2)
        Task undone = taskManager.undoLastTask();
        assertEquals(task2, undone, "Last executed task should be undone");

        assertEquals(2, taskManager.remainingTaskCount(), "Two tasks should now be in the queue");
    }
    @Test
    void undoMultipleTasks() {
        Task task1 = new Task("Task 1");
        Task task2 = new Task("Task 2");
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        taskManager.executeNextTask(); // Task 1
        taskManager.executeNextTask(); // Task 2

        Task undone1 = taskManager.undoLastTask();
        assertEquals(task2, undone1);

        Task undone2 = taskManager.undoLastTask();
        assertEquals(task1, undone2);

        Task undone3 = taskManager.undoLastTask();
        assertNull(undone3, "Undo beyond executed tasks should return null");

        assertEquals(2, taskManager.remainingTaskCount(), "All tasks should be back in the queue");
    }
    @Test
    void undoOnEmptyManager() {
        Task undone = taskManager.undoLastTask();
        assertNull(undone, "Undo on empty manager should return null");
    }
    @Test
    void executeAfterUndoMaintainsOrder() {
        Task task1 = new Task("Task 1");
        Task task2 = new Task("Task 2");
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        taskManager.executeNextTask(); // Task 1
        taskManager.undoLastTask();    // Task 1 back to queue

        Task next = taskManager.executeNextTask();
        assertEquals(task1, next, "Undo should place task at the front for next execution");
    }
}