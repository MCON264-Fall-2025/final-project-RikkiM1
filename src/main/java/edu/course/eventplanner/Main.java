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

        for (Guest g : Generators.GenerateGuests(guests)) {
            guestListManager.addGuest(g);
        }
        System.out.println(guests + " guests generated");

        //generate venues list
        List<Venue> venues = Generators.generateVenues();

        while (true) {
            System.out.println("\nWhat tasks would you like to do:" + "\n1. Load sample data" + "\n2. Add guest" + "\n3. Remove guest" + "\n4. Select venue" + "\n5. Generate seating chart" + "\n6. Add preparation task" + "\n7. Execute next task" + "\n8. Undo last task" + "\n9. Print event summary\n" + "10. finish");

            int Menu = kybd.nextInt();
            kybd.nextLine();

            switch (Menu) {
                case 1:
                    loadSampleData(taskManager, guestListManager);
                    break;
                case 2:
                    addGuestManually(kybd, guestListManager);
                    break;
                case 3:
                    removeGuest(kybd, guestListManager);
                    break;
                case 4:
                    selectVenue(kybd, venues, budget, guests);

                    break;
                case 5:
                    generateSeating(selectedVenue, guestListManager);
                    break;
                case 6:

                    addPreparationTask(kybd, taskManager);

                    break;
                case 7:
                    addPreparationTask(kybd, taskManager);

                    break;
                case 8:
                    undoLastTask(taskManager);
                    break;
                case 9:
                    printEventSummary(guestListManager, taskManager);
                    break;
                case 10:
                    exitProgram();
            }

        }
    }

    public static void addGuestManually(Scanner kybd, GuestListManager guestListManager) {
        System.out.println("Add guests manually");

        System.out.print("Name: ");
        String name = kybd.nextLine();

        System.out.print("Group: ");
        String tag = kybd.nextLine();

        Guest guest = new Guest(name, tag);
        guestListManager.addGuest(guest);

        for (Guest g : guestListManager.getAllGuests()) {
            System.out.println(g);
        }

    }

    public static void removeGuest(Scanner kybd, GuestListManager guestListManager) {
        for (Guest j : guestListManager.getAllGuests()) {
            System.out.println(j);
        }

        System.out.println("Enter the name of the guest to remove:");
        String removeName = kybd.nextLine();

        System.out.println("Enter guest's tag to remove:");
        String removeTag = kybd.nextLine();

        String key = (removeName + "-" + removeTag).toLowerCase();
        boolean removed = guestListManager.removeGuest(key);

        if (removed) {
            System.out.println("Guest '" + removeName + "' has been removed.");
        } else {
            System.out.println("Guest '" + removeName + "' was not found.");
        }
    }

    private static void loadSampleData(TaskManager taskManager, GuestListManager guestListManager) {
        System.out.println("Load sample data");

        System.out.println("Sample guests:");
        for (Guest g : guestListManager.getAllGuests()) {
            System.out.println(g);
        }

        List<Guest> sampleGuests = Generators.GenerateSampleGuests(5);
        for (Guest g : sampleGuests) {
            System.out.println(g);
        }

        List<Venue> sampleVenues = Generators.generateVenues();
        System.out.println("\nSample venues:");
        for (Venue v : sampleVenues) {
            System.out.println(v.getName() + " - Cost: $" + v.getCost() + ", Capacity: " + v.getCapacity() + ", Tables: " + v.getTables() + ", Seats per table: " + v.getSeatsPerTable());
        }

        taskManager.addTask(new Task("Loaded sample data"));
    }

    private static Venue selectVenue(Scanner kybd, List<Venue> venues, double budget, int guests) {
        System.out.println("Select venue");

        VenueSelector venueSelector = new VenueSelector(venues);
        Venue selectedVenue = venueSelector.selectVenue(budget, guests);

        if (selectedVenue == null) {
            System.out.println("No venue fits your budget and guest count.");

            System.out.println("What is your event budget?");
            budget = kybd.nextDouble();
            kybd.nextLine();

            System.out.println("What is your number of guests?");
            guests = kybd.nextInt();
            kybd.nextLine();

            venues = Generators.generateVenues();
            selectedVenue = venueSelector.selectVenue(budget, guests);
        }

        System.out.println("Venue selected:");
        System.out.println("Name: " + selectedVenue.getName());
        System.out.println("Cost: $" + selectedVenue.getCost());
        System.out.println("Capacity: " + selectedVenue.getCapacity());
        System.out.println("Tables: " + selectedVenue.getTables());
        System.out.println("Seats per table: " + selectedVenue.getSeatsPerTable());

        return selectedVenue;
    }

    private static void generateSeating(Venue selectedVenue, GuestListManager guestListManager) {
        if (selectedVenue == null) {
            System.out.println("You must select a venue first.");
            return;
        }

        SeatingPlanner seatingPlanner = new SeatingPlanner(selectedVenue);
        Map<Integer, List<Guest>> seating = seatingPlanner.generateSeating(guestListManager.getAllGuests());

        for (int tableNum : seating.keySet()) {
            System.out.println("Table " + tableNum + ":");
            for (Guest g : seating.get(tableNum)) {
                System.out.println("  " + g.getName() + " (" + g.getGroupTag() + ")");
            }
        }
    }

    private static void addPreparationTask(Scanner kybd, TaskManager taskManager) {
        System.out.print("Enter preparation task: ");
        String desc = kybd.nextLine();
        taskManager.addTask(new Task(desc));
    }

    private static void executeNextTask(TaskManager taskManager) {
        System.out.println("Execute next task");

        Task next = taskManager.executeNextTask();
        if (next != null) {
            System.out.println("Next task to complete:");
            System.out.println(next.getDescription());
        } else {
            System.out.println("No upcoming tasks.");
        }
    }

    private static void undoLastTask(TaskManager taskManager) {
        System.out.println("Undo last task");

        Task last = taskManager.undoLastTask();
        if (last != null) {
            System.out.println("Undid task: " + last.getDescription());
        } else {
            System.out.println("No task to undo.");
        }
    }

    private static void printEventSummary(GuestListManager guestListManager, TaskManager taskManager) {
        System.out.println("Print event summary");
        System.out.println("Guests: " + guestListManager.getGuestCount());
        System.out.println("Remaining tasks: " + taskManager.remainingTaskCount());
    }

    private static void exitProgram() {
        System.out.println("MAZAL TOV AGAIN!\nWAS SO NICE TO WORK WITH YOU!");
        System.exit(0);
    }
}
