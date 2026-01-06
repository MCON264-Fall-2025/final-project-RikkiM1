package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;
import java.util.*;

public class VenueSelector {

   List<Venue> venues;
    Map <Double, Integer> venue = new HashMap<>();

    public VenueSelector(List<Venue> venues, double budget, int guestCount) {
        this.venues = venues;

    }

    public Venue selectVenue(Venue venues) {

            if (venue.containsKey(venues.getCost())) {
            }
    }

}