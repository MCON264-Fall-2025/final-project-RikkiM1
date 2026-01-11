package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;
import java.util.*;

public class VenueSelector {

    private final Map<Double, Venue> venueMap = new HashMap<>();

    public VenueSelector(List<Venue> venues) {
        //loops through all the values in the map
        // and assigns them all as a venue
        for (Venue v : venues) {
            venueMap.put(v.getCost(), v);
        }
    }


    public Venue selectVenue(double budget, int guestCount) {
        Venue selectedVenue = null;

        //loop through all the venues
        for (Venue venue : venueMap.values()) {
//check for if in budget and capacity
            if (venue.getCost() <= budget &&
                    venue.getCapacity() >= guestCount) {
//choose best option
                //this venue is cheaper than the one currently selected
                if (selectedVenue == null ||
                        venue.getCost() < selectedVenue.getCost() ||
                        (venue.getCost() == selectedVenue.getCost() &&
                                venue.getCapacity() < selectedVenue.getCapacity())) {

                    selectedVenue = venue;
                }
            }
        }

        return selectedVenue;
    }

}


