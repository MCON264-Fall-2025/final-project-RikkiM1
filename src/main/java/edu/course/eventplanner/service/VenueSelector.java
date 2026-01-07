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

        for (Venue venue : venueMap.values()) {

            if (venue.getCost() <= budget &&
                    venue.getCapacity() >= guestCount) {

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


