package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;

import java.util.*;


public class GuestListManager {

    //master guest list
    LinkedList<Guest> guests = new LinkedList<>();
    //fast look up by name or ID
    Map<String, Guest> guestsByName = new HashMap<>();

    public void addGuest(Guest guest) {
        if (guest == null) return; // ignore null

        String key = (guest.getName() + "-" + guest.getGroupTag()).toLowerCase();

        // If the guest already exists, replace it
        if (guestsByName.containsKey(key)) {
            Guest oldGuest = guestsByName.get(key);
            int index = guests.indexOf(oldGuest);
            if (index != -1) {
                guests.set(index, guest); // replace
            }
            guestsByName.put(key, guest); // replace in the map
            System.out.println("Replaced existing guest: " + guest.getName());
            return;
        }

        // Otherwise, add guest
        guests.add(guest);
        guestsByName.put(key, guest);
    }

    public boolean removeGuest(String guestKey) {
        if (guestKey == null || guestKey.isEmpty()) {
            return false;
        }
        String normalKey = guestKey.toLowerCase();


        if (normalKey.contains("-")) {
            Guest guest = guestsByName.get(normalKey);

            if (guest == null) {
                return false;
            }

            guests.remove(guest);
            guestsByName.remove(normalKey);
            return true;
        }

        //loop through the map
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
        //if full name and key with - in middle
        if (guestName.contains("-")) {
            return guestsByName.get(guestName.toLowerCase());
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
