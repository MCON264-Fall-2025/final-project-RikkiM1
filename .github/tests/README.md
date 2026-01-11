# Instructor Autograding Tests

This directory contains instructor-provided integration tests that are **hidden from students**.

## How It Works

1. **These tests are NOT visible** to students in their starter code
2. **During GitHub Actions autograding**, the workflow copies these tests into the test directory
3. **Tests are run automatically** when students push their code

## Files

- `VenueSelectionTest.java` - Tests venue selection logic (10 points)
- `GuestManagementTest.java` - Tests guest list management with LinkedList/Map (10 points)
- `SeatingAlgorithmTest.java` - Tests seating algorithm implementation (10 points)
- `TaskWorkflowTest.java` - Tests task queue and undo stack (10 points)

## Updating Tests

To modify instructor tests:
1. Edit the files in this `.github/tests/` directory
2. Tests will automatically be used in the next autograding run
3. Students cannot see or modify these tests

## Total Points

- Autograding integration tests: **40 points** (10 per component)
- Student unit tests coverage: **15 points** (via JaCoCo)
- README documentation: **5 points** (via check_readme.py)

Event Planner Project
Overview

This project is an event planning system, including managing guests, selecting venues, seating guests, and executing tasks.
The system uses data structures and algorithms that work best.

Data Structures Used:


--LinkedList / Map<String, Guest>LinkedList 
in GuestListManager	which allows efficient addition of guests; 
Map provides O(1) lookup for guests by key (Name-GroupTag).
--List<Venue> 
in VenueSelector stores the available venues and allows linear iteration to find the cheapest suitable venue.
--Map<String, Queue<Guest>>, Queue, Map<Integer, List<Guest>>	
in SeatingPlanner groups guests by groupTag (Map + Queue) to seat together; 
Queue ensures fair seating order;
final Map assigns guests to table numbers.
--Queue<Task> and Stack<Task> in TaskManager for  tasks
Queue ensures tasks are executed in FIFO order; 
Stack allows undoing the last executed task efficiently.

Algorithms Used

GuestListManager	Algorithm: Map key lookup	Purpose: Finds guests in O(1) time by key.
VenueSelector	    Algorithm: Linear search	Purpose: Iterates through venues to select the cheapest that fits the budget and guest count.
SeatingPlanner	    Algorithm: Iterative group assignment	Purpose: Seats guests table by table, keeping groups together and wrapping around tables if necessary.
TaskManager	        Algorithm: Queue & Stack operations	    Purpose:Executes tasks in order (FIFO) and undoes the last executed task (LIFO).

Big-O Complexity

Finding a guest	O(1) using Map
Selecting a venue	O(n), n = number of venues (linear scan)
Generating seating	O(g + t), g = total guests, t = total tables (iterates over groups and fills tables)