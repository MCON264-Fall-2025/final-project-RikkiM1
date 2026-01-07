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
        String key= guest.getName()+"-"+guest.getGroupTag();
        for(Guest g : guests){
        if (g.getName().equals(guest.getName()) && g.getGroupTag().equals(guest.getGroupTag())) {
            System.out.println("Guest already exists: " + guest.getName());
            return;
        }}
        guests.add(guest);

        guestsMap.put(key, guest);
    }

    public boolean removeGuest(String guestName) {
        String[] parts = guestName.split("-");

        String name = parts[0];      // "Guest21"
        String group = parts[1];     // "friends"

        // Look up the guest in the map
        String key = name + "-" + group;
        Guest guest= guestsMap.get(key);
        if(guest!=null) {
            // Remove from the linked list
            guests.remove(guest);
            // Remove from the map
            guestsMap.remove(key);
            return true; // Guest successfully removed
        }

        return false;
        }




    public Guest findGuest(String guestName) {
        return guestsMap.get(guestName);
    }
    public int getGuestCount() {
        return guests.size();
    }

    public List<Guest> getAllGuests() {
        return guests;
    }
}
