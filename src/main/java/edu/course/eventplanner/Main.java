package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.SeatingPlanner;
import edu.course.eventplanner.service.TaskManager;
import edu.course.eventplanner.service.VenueSelector;
import edu.course.eventplanner.util.Generators;

import java.util.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner kybd = new Scanner(System.in);

        TaskManager taskManager = new TaskManager();
        GuestListManager guestListManager = new GuestListManager();

        double budget;
        int guests;
        Venue selectedVenue = null;

        //ask user for their event budget
        System.out.println("Mazal Tov!!!\nWe are so excited that you chose us to coordinate your event!" + "\nfor starters...\n");
        System.out.println("What is your event budget?");
        budget = kybd.nextDouble();
        kybd.nextLine();

        //ask user for number of guests
        System.out.println("What is your number of guests?");
        guests = kybd.nextInt();
        kybd.nextLine();

        //generate venues list
        List<Venue> venues = Generators.generateVenues();

        while (true) {
            System.out.println("\nWhat tasks would you like to do:" + "\n1. Load sample data" + "\n2. Add guest" + "\n3. Remove guest" + "\n4. Select venue" + "\n5. Generate seating chart" + "\n6. Add preparation task" + "\n7. Execute next task" + "\n8. Undo last task" + "\n9. Print event summary\n");

            int Menu = kybd.nextInt();
            kybd.nextLine();

            switch (Menu) {
                case 1:
                    System.out.println("Load sample data");
                    // make a linked list to hold sample guests

System.out.println("Sample guests:");
                    for (Guest g : guestListManager.getAllGuests()) {
                        System.out.println(g);
                    }

                    List<Guest> sampleGuests = Generators.GenerateSampleGuests(5); // NEW METHOD
                    for (Guest g : sampleGuests) {
                        System.out.println(g);
                    }
                    // make a linked list to hold sample venues
                    List<Venue> sampleVenues = Generators.generateVenues();
                    System.out.println("\nSample venues:");
                    for (Venue v : sampleVenues) {
                        System.out.println(v.getName() + " - Cost: $" + v.getCost() + ", Capacity: " + v.getCapacity() + ", Tables: " + v.getTables() + ", Seats per table: " + v.getSeatsPerTable());
                    }
                    taskManager.addTask(new Task("Loaded sample data"));
                    break;
                case 2:
                    System.out.println("1. Generate guests\n2. Add manually");
                    int choice = kybd.nextInt();
                    kybd.nextLine(); // consume newline


                    if (choice == 1) {
                        System.out.println("How many guests to generate?");
                        int count = kybd.nextInt();
                        kybd.nextLine();

                        for (Guest g : Generators.GenerateGuests(count)) {
                            guestListManager.addGuest(g);
                        }

                        taskManager.addTask(new Task("Added " + count + " guests"));

                    } else if (choice == 2) {
                        System.out.print("Name: ");
                        String name = kybd.nextLine();
                        System.out.print("Group: ");
                        String tag = kybd.nextLine();

                        guestListManager.addGuest(new Guest(name, tag));
                        taskManager.addTask(new Task("Added guest: " + name + " (" + tag + ")"));
                    }
                    break;
                case 3:
                    System.out.println("Enter the name of the guest to remove:");
                    String removeName = kybd.nextLine();

                    System.out.println("Enter guest's tag to remove (family, friends, neighbors, coworkers):");
                    String removeTag = kybd.nextLine();
                    String key = removeName + "-" + removeTag;

                    // Attempt to remove the guest
                    boolean removed = guestListManager.removeGuest(key);

                    if (removed) {
                        System.out.println("Guest '" + removeName + "' has been removed.");
                        taskManager.addTask( new Task("Removed guest: " + removeName + " (" + removeTag + ")"));

                    } else {
                        System.out.println("Guest '" + removeName + "' was not found.");
                    }


                    for (Guest g : guestListManager.getAllGuests()) {
                        System.out.println(g.toString());
                    }
                    break;
                case 4:
                    System.out.println("Select venue");
                    //pass into the venuSelector all the generated venues from before
                    VenueSelector venueSelector = new VenueSelector(venues);

                    //now selectedenue will become the returned venue from the method.
                    selectedVenue = venueSelector.selectVenue(budget, guests);

                    if (selectedVenue == null) {
                        System.out.println("No venue fits your budget and guest count.");
                        //ask user for their event budegt
                        System.out.println("Lets try again.... maybe with a little more money and less peopel?" + "\nWhat is your event budget?");
                        budget = kybd.nextDouble();
                        kybd.nextLine();

                        //ask user for number of guests
                        System.out.println("What is your number of guests?");
                        guests = kybd.nextInt();
                        kybd.nextLine();
                        venues = Generators.generateVenues();
                        selectedVenue = venueSelector.selectVenue(budget, guests);
                        Task task = new Task("Selected venue: " + selectedVenue.getName());

                    } else {
                        System.out.println("Venue selected:");
                        System.out.println("Name: " + selectedVenue.getName());
                        System.out.println("Cost: $" + selectedVenue.getCost());
                        System.out.println("Capacity: " + selectedVenue.getCapacity());
                        System.out.println("Tables: " + selectedVenue.getTables());
                        System.out.println("Seats per table: " + selectedVenue.getSeatsPerTable());
                        taskManager.addTask(new Task ("selected venue: "+ selectedVenue.getName()));
                    }
                      break;
                case 5:
                    if(selectedVenue == null) {
                        System.out.println("You must select a venue first.");
                        break;
                    }
                    System.out.println("Generate seating chart");
                    SeatingPlanner seatingPlanner = new SeatingPlanner(selectedVenue);
                    Map<Integer, List<Guest>> seating = seatingPlanner.generateSeating(guestListManager.getAllGuests());
                    // print seating chart
                    for (int tableNum : seating.keySet()) {
                        System.out.println("Table " + tableNum + ":");
                        for (Guest g : seating.get(tableNum)) {
                            System.out.println("  " + g.getName() + " (" + g.getGroupTag() + ")");
                        }
                    }
                    taskManager.addTask(new Task("Seating chart generated"));
                    break;
                case 6:

                    System.out.print("Enter preparation task: ");
                    String desc = kybd.nextLine();
                    taskManager.addTask(new Task("Task added to queue."));
                    break;
                case 7:
                    System.out.println("Execute next task");
                    Task next = taskManager.executeNextTask();
                    if (next != null) {
                        System.out.println("Next task to complete:");
                        System.out.println(next.getDescription());
                    } else {
                        System.out.println("No upcoming tasks.");
                    }
                    break;
                case 8:
                    System.out.println("Undo last task");
                    Task last = taskManager.undoLastTask();
                    if (last != null) {
                        System.out.println("Undid task: " + last.getDescription());
                    } else {
                        System.out.println("No task to undo.");
                    }
                    break;
                case 9:
                    System.out.println("Print event summary");
                    System.out.println("Guests: " + guestListManager.getGuestCount());
                    System.out.println("Remaining tasks: " + taskManager.remainingTaskCount());


                    break;
            }

        }
    }
}
