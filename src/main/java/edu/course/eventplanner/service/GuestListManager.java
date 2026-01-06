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
        //check if the guest already exists
        if (guestsMap.containsKey(guest.getName()))  {
            System.out.println("Guest already exists: " + guest.getName());
            return;
        }
        guests.add(guest);

        guestsMap.put(guest.getName(), guest);
    }

    public boolean removeGuest(String guestName) {
        // Look up the guest in the map

        if (guestsMap.containsKey(guestName.getName()) && guestsMap.containsValue(guestName))  {
            // Remove from the linked list
            guests.remove(guestName);
            // Remove from the map
            guestsMap.remove(guestName);
            return true; // Guest successfully removed
        }

        return false;
        }




    public Guest findGuest(String guestName) {
        //need to loop through all the guests and return what you are looking for
        return null;
    }

    public int getGuestCount() {
        return guests.size();
    }

    public List<Guest> getAllGuests() {
        return guests;
    }
}
