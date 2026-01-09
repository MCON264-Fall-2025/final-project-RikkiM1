# Event Planner Mini

This project demonstrates practical use of data structures:
linked lists, stacks, queues, maps, trees, sorting, and searching.

## What You Must Do
- Implement all TODO methods DONE
- Write JUnit 5 tests for core logic
- Pass instructor autograding tests
- Explain your design choices in this README

See Canvas assignment for full requirements.

Event Planner Project

I used AI to make my README look nice.

Project Overview

The Event Planner Project is designed to simplify event management, from guest tracking to seating arrangements and task management. It leverages efficient data structures and algorithms to handle guests, venues, seating, and tasks in a structured and performant manner.

Whether you’re planning a small party or a large conference, this system ensures optimal organization with minimal manual effort.

Key Features

Guest Management: Fast addition, removal, and lookup of guests.

Venue Selection: Easily choose the best venue based on budget and capacity.

Seating Planning: Fair and orderly table assignments based on guest groups.

Task Management: Efficiently track upcoming tasks and undo completed ones.

Performance Optimized: Carefully chosen data structures ensure fast operations.

Data Structures
GuestListManager

Structures: LinkedList<Guest> + Map<String, Guest>

Reasoning:

LinkedList allows O(1) insertions/removals at the ends, ideal for dynamically growing guest lists.

Grouping guests by tag is efficient because we can traverse only relevant segments of the list for seating.

Map<String, Guest> enables O(1) lookup by key = name-groupTag, crucial for quick updates or checks.

Big-O Summary:

Add guest: O(1)

Remove guest: O(1)

Lookup guest: O(1)

VenueSelector

Structure: List<Venue>

Reasoning:

The number of venues is small, so linear search is sufficient.

Iteration allows easy selection of the cheapest venue within budget.

Big-O Summary:

Select venue: O(n), n = number of venues

SeatingPlanner

Structures: Map<String, Queue<Guest>>, Map<Integer, List<Guest>>

Reasoning:

Map<String, Queue<Guest>> groups guests by groupTag for fair seating and quick lookup.

Queue ensures FIFO seating order within groups.

Map<Integer, List<Guest>> allows O(1) access to all guests at a specific table.

Big-O Summary:

Generate seating: O(g + t), g = total guests, t = total tables

TaskManager

Structures: Deque<Task> for upcoming tasks, Stack<Task> for completed tasks

Reasoning:

Deque ensures FIFO execution of tasks.

Stack allows undoing the most recent completed task (LIFO).

Big-O Summary:

Add task: O(1)

Execute next task: O(1)

Undo last task: O(1)

Algorithms & Big-O Table
Operation	Complexity	Notes
Add guest	O(1)	Fast lookup & insertion using Map
Lookup guest	O(1)	Direct Map access
Remove guest	O(1)	Map removal is efficient
Select venue	O(n)	Linear search in a small list
Generate seating	O(g + t)	Each guest is assigned to a table
Add task	O(1)	Deque insertion at front/back
Execute next task	O(1)	Poll from Deque front
Undo last task	O(1)	Pop from Stack and reinsert into Deque

Sorting Algorithm:
No sorting is required at the end. The data structures maintain logical order efficiently.

How It Works

Add Guests: Guests are stored in GuestListManager for fast lookup and iteration.

Select Venue: VenueSelector evaluates all venues and picks the best fit.

Assign Seating: SeatingPlanner uses FIFO queues to assign guests to tables fairly.

Manage Tasks: TaskManager tracks upcoming tasks and supports undoing completed ones.