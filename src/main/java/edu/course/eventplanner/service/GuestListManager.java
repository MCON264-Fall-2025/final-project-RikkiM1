package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;
import java.util.*;

public class GuestListManager<E> {
    private final LinkedList<E> guests = new LinkedList<>();
    private final Map<String, E> guestByName = new HashMap<>();
    private int size = 0;

    public void addGuest(E guest) {
        String name = (guest instanceof Guest) ? ((Guest) guest).getName() : guest.toString();

        // Prevent duplicates
        if (guestByName.containsKey(name)) {
            return;
        }

        //add to the linked list and to the map
        guests.addLast(guest);
        guestByName.put(name, guest);
        size++;
    }

    public boolean removeGuest(String guestName) {
        // 1. O(1) Lookup: Use the map to find the object instantly
        E toRemove = guestByName.get(guestName);

        if (toRemove == null) {
            return false;
        }

        // 2. Remove from both to keep them in sync
        guests.remove(toRemove);
        guestByName.remove(guestName);

        size--;
        return true;
    }

    public E findGuest(String guestName) {
        return guestByName.get(guestName);
    }

    public int size() {
        return size;
    }

    public List<E> getAllGuests() {
        return guests;
    }
}