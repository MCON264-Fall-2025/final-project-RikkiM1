package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;

import java.util.*;

public class SeatingPlanner {
    private final Venue venue;

    public SeatingPlanner(Venue venue) {
        this.venue = venue;
    }

    /**
     * Generates a seating chart mapping table numbers to the list of guests at that table.
     * Guests with the same groupTag are seated together when possible.
     */
    public Map<Integer, List<Guest>> generateSeating(List<Guest> guests) {
        Map<Integer, List<Guest>> tableAssignments = new HashMap<>();

        // 1. Group guests by groupTag
        Map<String, Queue<Guest>> groupQueues = new LinkedHashMap<>();
        for (Guest g : guests) {
            String groupTag = g.getGroupTag();
            if (!groupQueues.containsKey(groupTag)) {
                groupQueues.put(groupTag, new LinkedList<>());
            }
            groupQueues.get(groupTag).add(g);
        }

        int totalTables = venue.getTables();
        int seatsPerTable = venue.getSeatsPerTable();
        int tableNumber = 1;

        // 2. Assign guests to tables
        while (!groupQueues.isEmpty()) {
            // Make a copy of the keys to avoid ConcurrentModificationException
            List<String> groupKeys = new ArrayList<>(groupQueues.keySet());

            for (String groupTag : groupKeys) {
                Queue<Guest> queue = groupQueues.get(groupTag);
                List<Guest> table = tableAssignments.get(tableNumber);
                if (table == null) {
                    table = new ArrayList<>();
                    tableAssignments.put(tableNumber, table);
                }

                while (!queue.isEmpty() && table.size() < seatsPerTable) {
                    table.add(queue.poll());
                }

                // Remove group if all seated
                if (queue.isEmpty()) {
                    groupQueues.remove(groupTag);
                }

                // Move to next table if current table is full
                if (table.size() == seatsPerTable) {
                    tableNumber++;
                    if (tableNumber > totalTables) {
                        tableNumber = 1; // wrap around if more guests than seats
                    }
                }
            }
        }

        return tableAssignments;
    }
}