package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;

import java.util.*;


public class GuestListManager {

    //master guest list
    LinkedList<Guest> guests = new LinkedList<>();
    //fast look up by name or ID
    Map<String, Guest> guestsMap = new HashMap<>();

    //method to add guests
    public void addGuest(Guest guest) {
        //check if the guest already exists
        String key = guest.getName() + "-" + guest.getGroupTag();
        if (guestsMap.containsKey(key)) {
                System.out.println("Guest already exists: " + guest.getName());
                return;
            }

        guests.add(guest);

        guestsMap.put(key, guest);
    }

    public boolean removeGuest(String guestKey) {
        if (guestKey.contains("-")) {
            // remove by full key
            String[] parts = guestKey.split("-", 2); // split only on first dash
            String name = parts[0];
            String group = parts[1];
            String key = name + "-" + group;

            Guest guest = guestsMap.get(key);
            if (guest != null) {
                guests.remove(guest);
                guestsMap.remove(key);
                return true;
            }
        } else {
            // remove by name only
            for (Guest g : guests) {
                if (g.getName().equals(guestKey)) {
                    guests.remove(g);
                    String key = g.getName() + "-" + g.getGroupTag();
                    guestsMap.remove(key);
                    return true;
                }
            }
        }

        return false; // not found
    }


    public Guest findGuest(String guestName) {
        if (guestName.contains("-")) {
            return guestsMap.get(guestName);
        } else {
            // Return first guest matching the name
            for (Guest g : guests) {
                if (g.getName().equals(guestName)) {
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
