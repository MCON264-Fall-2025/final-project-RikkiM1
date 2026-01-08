package edu.course.eventplanner;

import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.SeatingPlanner;
import edu.course.eventplanner.util.Generators;
import org.junit.jupiter.api.BeforeEach;


import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.service.GuestListManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SeatingGuestsByGroup {



    private Venue venue;
    private SeatingPlanner seatingPlanner;
    private List<Guest> guests;
    private GuestListManager guestListManager;

    @BeforeEach
    void setUp() {
        // set up venue for all tests
        venue = new Venue(
                "Test Venue",
                1000,   // cost
                20,     // capacity
                5,      // tables
                4       // seats per table
        );
        seatingPlanner = new SeatingPlanner(venue);
        guests = new ArrayList<>();
    }

    @Test
    void seatingEmptyGuets(){
        Map<Integer, List<Guest>> seating = seatingPlanner.generateSeating(guests);
        // The seating map should have no guests seated
        int totalSeated = 0;
        for (List<Guest> table : seating.values()) {
            totalSeated += table.size();
        }

        assertEquals(0, totalSeated, "There should be no guests seated");
    }
    @Test
    void AllGuestsAreSeatedExactlyOnce(){
        //ensure that there are only three guests seated
        guests.add(new Guest("Rikki", "family"));
        guests.add(new Guest("Lea", "family"));
        guests.add(new Guest("Aviva", "friends"));

        Map<Integer, List<Guest>> seating = seatingPlanner.generateSeating(guests);
        int totalSeated = 0;

        for (List<Guest> tableGuests : seating.values()) {
            totalSeated += tableGuests.size();
        }
        assertEquals(guests.size(), totalSeated, "All guests should be seated");
        assertEquals(guests.size(), totalSeated, "No guest should be seated more than once");
    }

    @Test
    void AllGuestsFromTheSameGroupSitTogether(){
        guests.add(new Guest("Rikki", "family"));
        guests.add(new Guest("Lea", "family"));
        guests.add(new Guest("Dovi", "family"));
        guests.add(new Guest("Aviva", "family"));
        guests.add(new Guest("Zahava", "friends"));
        Map<Integer, List<Guest>> seating = seatingPlanner.generateSeating(guests);

        boolean familyTog=false;
        boolean friendsTog=false;

        for (List<Guest> table : seating.values()) {
            boolean hasFamily = false;
            boolean hasFriends = false;

            //loop through all the guests and see if their are nay with the tag family or friends
            for (Guest g : table) {
                if (g.getGroupTag().equals("family")) {
                    hasFamily = true;
                }
                if (g.getGroupTag().equals("friends")) {
                    hasFriends = true;
                }
            }
//ensure that the loop before counted properly
            if (hasFamily && table.size() == 4) {
                familyTog = true;
            }
            if (hasFriends && table.size() == 1) {
                friendsTog = true;
            }
        }

        assertTrue(familyTog, "Family guests should be seated together");
        assertTrue(friendsTog, "Friends guests should be seated together");
    }

    @Test
    void seatingDoesNotExceedTableCapacity(){
        //add more guests than fit on a table
        guests.add(new Guest("Rikki", "family"));
        guests.add(new Guest("Lea", "family"));
        guests.add(new Guest("Aviva", "family"));
        guests.add(new Guest("Gabriella", "family"));
        guests.add(new Guest("Dovi", "family"));
        guests.add(new Guest("Ezra", "family"));

        //every time loop through the tables ensure they dont have more than capacity per the table
        Map<Integer, List<Guest>> seating = seatingPlanner.generateSeating(guests);
        for (List<Guest> table : seating.values()) {
            assertTrue(table.size() <= venue.getSeatsPerTable());
        }
    }
    }

