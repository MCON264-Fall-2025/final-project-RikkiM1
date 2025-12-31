package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Venue;
import java.util.*;

public class VenueSelector {

    private final NavigableSet<Venue> venueTree;

    public VenueSelector(List<Venue> venues) {
        this.venueTree = new TreeSet<>(
                Comparator.comparingDouble(Venue::getCost)
                        .thenComparing(Venue::getCapacity, Comparator.reverseOrder())
                        .thenComparing(Venue::getName)
        );

        // Defensive copy to avoid immutable list failure
        for (Venue v : venues) {
            venueTree.add(v);
        }
    }

    public Venue selectVenue(double budget, int guestCount) {
        for (Venue v : venueTree) {
            if (v.getCost() <= budget && v.getCapacity() >= guestCount) {
                return v;
            }
        }
        return null;
    }
}