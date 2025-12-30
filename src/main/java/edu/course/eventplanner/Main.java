package edu.course.eventplanner;
import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.VenueSelector;
import edu.course.eventplanner.util.Generators;

import java.util.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner kybd = new Scanner(System.in);

        double budget;
        int guests;
//ask user for their event budegt
        System.out.println("What is your event budget?");
        budget = kybd.nextDouble();
        kybd.nextLine();

        //ask user for number of guests
        System.out.println("What is your number of guests?");
        guests = kybd.nextInt();
        kybd.nextLine();
        List<Venue> venues = Generators.generateVenues();
System.out.println("What tasks woudld you like to do: 1. Load sample data\n" +
        "2. Add guest\n" +
        "3. Remove guest\n" +
        "4. Select venue\n" +
        "5. Generate seating chart\n" +
        "6. Add preparation task\n" +
        "7. Execute next task\n" +
        "8. Undo last task\n" +
        "9. Print event summary");
int Menu=kybd.nextInt();
kybd.nextLine();
        switch(Menu){
            case 1: System.out.println("Load sample data");
            break;
            case 2:  System.out.println("Add guest");
               //create the lists
                GuestListManager guestListManager = new GuestListManager();
                for (Guest g : Generators.GenerateGuests(guests)) {
                    guestListManager.addGuest(g);
                }
                break;
            case 3:   System.out.println("Remove guest");
                break;
            case 4:     System.out.println("Select venue");
                break;
            case 5:     System.out.println("Generate seating chart");
                break;
            case 6:  System.out.println("Add preparation task");
                break;
            case 7:    System.out.println("Execute next task");
                break;
            case 8:  System.out.println("Undo last task");
                break;
            case 9:    System.out.println("Print event summary");
        }

    }
}
