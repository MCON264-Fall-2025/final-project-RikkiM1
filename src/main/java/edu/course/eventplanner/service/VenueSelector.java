package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Venue;
import java.util.*;

public class VenueSelector {
    private final List<Venue> venues;
    public VenueSelector(List<Venue> venues)
    {
        this.venues = venues;
    }
    public Venue selectVenue(double budget, int guestCount)
    {
        List<Venue> validVenues = new ArrayList<>();
//makw this a binary search tree
        // Step 1: filter venues
        for (Venue v : venues) {
            if (v.getCost() <= budget && v.getCapacity() >= guestCount) {
                validVenues.add(v);
            }
        }

        if (validVenues.isEmpty()) {
            return null;
        }
        return validVenues.get(0);
}}
