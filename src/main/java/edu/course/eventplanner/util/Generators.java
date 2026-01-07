package edu.course.eventplanner.util;

import edu.course.eventplanner.model.*;
import java.util.*;

public class Generators {
    private static int guestCounter=1;

    public static List<Guest> GenerateGuests(int n) {
        List<Guest> guests = new ArrayList<>();

        String[] groups = {"family","friends","neighbors","coworkers"};
        for(int i=0;i<n;i++){
            guests.add(new Guest("Guest"+guestCounter, groups[i%groups.length]));
            guestCounter++;
        }
        return guests;
    }
    public static List<Venue> generateVenues() {
        return List.of(
            new Venue("Chynka",1500,40,5,8),
            new Venue("The Palace",2500,60,8,8),
            new Venue("El Caribe",5000,120,15,8)
        );
    }


}
