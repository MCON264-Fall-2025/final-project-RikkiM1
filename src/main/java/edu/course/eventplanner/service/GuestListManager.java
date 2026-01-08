package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;

import java.util.*;


public class GuestListManager {

    //master guest list
    LinkedList<Guest> guests = new LinkedList<>();
    //fast look up by name or ID
    Map<String, Guest> guestsByName = new HashMap<>();


    //method to add guests
    public void addGuest(Guest guest) {
        //check if the guest already exists

        String key = (guest.getName() + "-" + guest.getGroupTag()).toLowerCase();
        if (guestsByName.containsKey(key)) {
            System.out.println("Guest already exists: " + guest.getName());
            return;
        }

        guests.add(guest);

        guestsByName.put(key, guest);
    }

    public boolean removeGuest(String guestKey) {

        if (guestKey.contains("-")) {
            //split the guestkey into name and tag
            String[] parts = guestKey.split("-", 2); // split only on first dash
            String name = parts[0];
            String tag = parts[1];


            Guest guest = guestsByName.get(guestKey.toLowerCase());

            // remove by name and tag only
            if (guest == null) {
                return false;
            }
            if (guest.getName().equalsIgnoreCase(name)) {
                if (guest.getGroupTag().equalsIgnoreCase(tag)) {
                    guests.remove(guest);
                    guestsByName.remove(guestKey);
                    return true;
                }
            }
        }
            return false; // not found
        }


    public Guest findGuest(String guestName) {
        if (guestName.contains("-")) {
            return guestsByName.get(guestName);
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
