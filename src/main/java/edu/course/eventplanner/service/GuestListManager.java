package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;

import java.util.*;


public class GuestListManager {

    //master guest list
    LinkedList<Guest> guests = new LinkedList<>();
    //fast look up by name or ID
    Map<String, Guest> guestsMap = new HashMap<>();

    //method to add guests
    public void addGuest(Guest guest) {
        if (guestsMap.containsKey(guest.getName())) {
            System.out.println("Guest already exists: " + guest.getName());
            return;
        }
        guests.add(guest);

        guestsMap.put(guest.getName(), guest);
    }

    public boolean removeGuest(String guestName) {
        // Look up the guest in the map
        Guest guest = guestsMap.get(guestName);
        if (guest == null) {
            return false; // Guest not found
        }

        // Remove from the linked list
        guests.remove(guest);

        // Remove from the map
        guestsMap.remove(guestName);

        return true; // Guest successfully removed
    }
    public Guest findGuest(String guestName) {
        //need to loop through all the gueets and return what you are looking for
        return null;
    }

    public int getGuestCount() {
        return guests.size();
    }

    public List<Guest> getAllGuests() {
        return guests;
    }
}
