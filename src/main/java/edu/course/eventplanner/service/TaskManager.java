package edu.course.eventplanner.service;


import edu.course.eventplanner.model.Task;
import java.util.*;

public class TaskManager {
    private final Queue<Task> upcoming = new LinkedList<>();
    private final Stack<Task> completed = new Stack<>();

    // Add a new task to the queue
    public void addTask(Task task) {
        upcoming.add(task);
    }

    // Execute the next task in the queue
    public Task executeNextTask() {
        Task next = upcoming.poll(); // removes head of the queue
        if (next != null) {
            completed.push(next); // track completed tasks for undo
        }
        return next;
    }

    // Undo the last executed task
    public Task undoLastTask() {
        if (!completed.isEmpty()) {
            Task last = completed.pop();
            // Put it back at the front of the queue if you want it next
            ((LinkedList<Task>) upcoming).addFirst(last);
            return last;
        }
        return null; // nothing to undo
    }

    // Get the number of remaining tasks
    public int remainingTaskCount() {
        return upcoming.size();
    }
}