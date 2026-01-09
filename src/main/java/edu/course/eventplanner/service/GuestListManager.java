package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;

import java.util.*;


public class GuestListManager {

    //master guest list
    LinkedList<Guest> guests = new LinkedList<>();
    //fast look up by name or ID
    Map<String, Guest> guestsByName = new HashMap<>();


    public void addGuest(Guest guest) {
        if (guest == null) return; // safely ignore null

        String key = (guest.getName() + "-" + guest.getGroupTag()).toLowerCase();

        // If guest already exists, do nothing
        if (guestsByName.containsKey(key)) {
            System.out.println("Guest already exists: " + guest.getName());
            return;
        }

        // Otherwise, add guest
        guests.add(guest);
        guestsByName.put(key, guest);
    }
    public boolean removeGuest(String guestKey) {
        if (guestKey == null || guestKey.isEmpty()) {
            return false; // safely return false instead of throwing an exception
        }
        String normalizedKey = guestKey.toLowerCase();

        // Case 1: full key provided (name-tag)
        if (normalizedKey.contains("-")) {
            Guest guest = guestsByName.get(normalizedKey);

            if (guest == null) {
                return false;
            }

            guests.remove(guest);
            guestsByName.remove(normalizedKey);
            return true;
        }

        // Case 2: name only provided (TEST CASE)
        for (Map.Entry<String, Guest> entry : guestsByName.entrySet()) {
            Guest guest = entry.getValue();

            if (guest.getName().equalsIgnoreCase(guestKey)) {
                guests.remove(guest);
                guestsByName.remove(entry.getKey());
                return true;
            }
        }

        return false;
    }
    public Guest findGuest(String guestName) {
        if (guestName == null || guestName.isEmpty()) {
            return null;
        }
        if (guestName.contains("-")) {
            return guestsByName.get(guestName.toLowerCase()); // normalize
        } else {
            for (Guest g : guests) {
                if (g.getName().equalsIgnoreCase(guestName)) { // ignore case
                    return g;
                }
            }
        }
        return null;
    }

    public int getGuestCount() {
        return guests.size();
    }

    public List<Guest> getAllGuests() {
        return guests;
    }
}
