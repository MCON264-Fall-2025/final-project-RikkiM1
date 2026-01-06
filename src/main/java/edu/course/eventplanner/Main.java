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
        GuestListManager guestListManager = new GuestListManager();
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
        //generate venues list
        List<Venue> venues = Generators.generateVenues();
        while(true) {
        System.out.println("\nWhat tasks would you like to do:\n" +
                "1. Load sample data\n" +
                "2. Add guest\n" +
                "3. Remove guest\n" +
                "4. Select venue\n" +
                "5. Generate seating chart\n" +
                "6. Add preparation task\n" +
                "7. Execute next task\n" +
                "8. Undo last task\n" +
                "9. Print event summary\n" +
                "10. Finished");
        int Menu = kybd.nextInt();
        kybd.nextLine();
        switch (Menu) {
            case 1:
                System.out.println("Load sample data");
                break;
            case 2:
                System.out.println("Add guest");

                Scanner scanner = new Scanner(System.in);
                System.out.println("Do you want to (1) Generate guests or (2) Add a guest manually?");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (choice == 1) {
                    System.out.println("How many guests to generate?");
                    int guestsCount = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    for (Guest g : Generators.GenerateGuests(guestsCount)) {
                        guestListManager.addGuest(g);
                    }

                } else if (choice == 2) {
                    System.out.println("Enter guest's first name:");
                    String firstName = scanner.nextLine();

                    System.out.println("Enter guest's tag (family, friends, neighbors, coworkers):");
                    String tag = scanner.nextLine();

                    Guest newGuest = new Guest(firstName, tag);
                    guestListManager.addGuest(newGuest);
                } else {
                    System.out.println("Invalid choice.");
                }

                System.out.println("Loaded " + guestListManager.getGuestCount() + " guests.");
for(Guest k: guestListManager.getAllGuests())
    System.out.println(k.toString());
                break;
            case 3:
                System.out.println("Enter the name of the guest to remove:");
                String removeName = kybd.nextLine();


                System.out.println("Enter guest's tag to remove (family, friends, neighbors, coworkers):");
                String removeTag = kybd.nextLine();
                // Attempt to remove the guest
                Guest newGuest1 = new Guest(removeName, removeTag);
                boolean removed = guestListManager.removeGuest(newGuest1);


                if (removed) {
                    System.out.println("Guest '" + removeName + "' has been removed.");
                } else {
                    System.out.println("Guest '" + removeName + "' was not found.");
                }
                for(Guest k: guestListManager.getAllGuests())
                    System.out.println(k.toString());
                break;

            case 4:
                System.out.println("Select venue");
                //pass into the venuSelector all the generated venues from before
                VenueSelector venueSelector = new VenueSelector(venues, budget, guests);
                Venue selectedVenue = selectVenue(venueSelector);

                if (selectedVenue == null) {
                    System.out.println("No venue fits your budget and guest count.");
                    //ask user for their event budegt
                    System.out.println("Lets try agian.... maybe with a little more money and less peopel?" +
                            "\nWhat is your event budget?");
                    budget = kybd.nextDouble();
                    kybd.nextLine();

                    //ask user for number of guests
                    System.out.println("What is your number of guests?");
                    guests = kybd.nextInt();
                    kybd.nextLine();
                    venues = Generators.generateVenues();
                } else {
                    System.out.println("Venue selected:");
                    System.out.println("Name: " + selectedVenue.getName());
                    System.out.println("Cost: $" + selectedVenue.getCost());
                    System.out.println("Capacity: " + selectedVenue.getCapacity());
                    System.out.println("Tables: " + selectedVenue.getTables());
                    System.out.println("Seats per table: " + selectedVenue.getSeatsPerTable());
                }
                break;
            case 5:
                System.out.println("Generate seating chart");
                break;
            case 6:
                System.out.println("Add preparation task");
                break;
            case 7:
                System.out.println("Execute next task");
                break;
            case 8:
                System.out.println("Undo last task");
                break;
            case 9:
                System.out.println("Print event summary");
        }

    }}
}
