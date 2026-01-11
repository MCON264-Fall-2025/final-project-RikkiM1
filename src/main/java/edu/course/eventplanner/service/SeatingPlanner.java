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
    //Create a seating chart.
    //takes in a list of Guest objects.
    //Key = table number
    //Value = list of Guest objects at that table (List<Guest>)
    public Map<Integer, List<Guest>> generateSeating(List<Guest> guests) {

//Guests with the same groupTag are considered part of the same group

        Map<Integer, List<Guest>> tableAssignments = new HashMap<>();

        //groupQueues maps each groupTag to a Queue<Guest>
        //string is the groupTag and Queue is the list of guests, in a queue so retains FIFO order
        Map<String, Queue<Guest>> groupQueues = new LinkedHashMap<>();
        for (Guest g : guests) {
            //for each guest it gets its group tag
            String groupTag = g.getGroupTag();
            //checks if the map does not already have this groupTag as a key.
            if (!groupQueues.containsKey(groupTag)) {
                //Creates a new empty queue for that groupTag
                groupQueues.put(groupTag, new LinkedList<>());
            }
            //this line adds the guests to the queue of its tag.
            groupQueues.get(groupTag).add(g);
        }

        int totalTables = venue.getTables();
        int seatsPerTable = venue.getSeatsPerTable();
        int tableNumber = 1;

        //  Assign guests to tables
        while (!groupQueues.isEmpty()) {
            // Make a copy of the keys to avoid copy/modifications that we dont want to touch
            List<String> groupKeys = new ArrayList<>(groupQueues.keySet());

            for (String groupTag : groupKeys) {
                //make a FIFO structure to hold guests
                Queue<Guest> queue = groupQueues.get(groupTag);
                //make a linked list to hold the tables
                List<Guest> table = tableAssignments.get(tableNumber);
                //if the table is empty
                if (table == null) {
                    table = new ArrayList<>();
                    tableAssignments.put(tableNumber, table);
                }

                while (!queue.isEmpty() && table.size() < seatsPerTable) {
                    //still have more room so add guest to the table
                    table.add(queue.poll());
                }

                // Remove group if all seated, dont need to deal with them anymore
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