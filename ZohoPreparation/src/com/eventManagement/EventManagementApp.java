package com.eventManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Event {
    String eventName;
    String date;
    String location;
    String organizer;
    List<String> attendees;

    public Event(String eventName, String date, String location, String organizer) {
        this.eventName = eventName;
        this.date = date;
        this.location = location;
        this.organizer = organizer;
        this.attendees = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("%-20s %-15s %-20s %-15s %s", eventName, date, location, organizer, String.join(", ", attendees));
    }
}

public class EventManagementApp {
    static List<Event> events = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nEvent Management System");
            System.out.println("1. Create a new Event");
            System.out.println("2. View all Events");
            System.out.println("3. Update an Event");
            System.out.println("4. Delete an Event");
            System.out.println("5. Add Attendee to an Event");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createEvent(scanner);
                    break;
                case 2:
                    viewAllEvents();
                    break;
                case 3:
                    updateEvent(scanner);
                    break;
                case 4:
                    deleteEvent(scanner);
                    break;
                case 5:
                    addAttendee(scanner);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void createEvent(Scanner scanner) {
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Enter Event Name: ");
        String eventName = scanner.nextLine();
        System.out.print("Enter Event Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter Event Location: ");
        String location = scanner.nextLine();
        System.out.print("Enter Event Organizer: ");
        String organizer = scanner.nextLine();

        events.add(new Event(eventName, date, location, organizer));
        System.out.println("Event created successfully.");
    }

    static void viewAllEvents() {
        if (events.isEmpty()) {
            System.out.println("No events found.");
        } else {
            System.out.printf("%-20s %-15s %-20s %-15s %s\n", "Event Name", "Date", "Location", "Organizer", "Attendees");
            System.out.println("-------------------------------------------------------------------------------");
            for (Event event : events) {
                System.out.println(event);
            }
        }
    }

    static void updateEvent(Scanner scanner) {
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Enter Event Name to update: ");
        String eventName = scanner.nextLine();
        Event event = findEventByName(eventName);

        if (event != null) {
            System.out.print("Enter new Event Date (YYYY-MM-DD): ");
            event.date = scanner.nextLine();
            System.out.print("Enter new Event Location: ");
            event.location = scanner.nextLine();
            System.out.print("Enter new Event Organizer: ");
            event.organizer = scanner.nextLine();
            System.out.println("Event updated successfully.");
        } else {
            System.out.println("Event not found.");
        }
    }

    static void deleteEvent(Scanner scanner) {
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Enter Event Name to delete: ");
        String eventName = scanner.nextLine();
        Event event = findEventByName(eventName);

        if (event != null) {
            events.remove(event);
            System.out.println("Event deleted successfully.");
        } else {
            System.out.println("Event not found.");
        }
    }

    static void addAttendee(Scanner scanner) {
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Enter Event Name to add attendee: ");
        String eventName = scanner.nextLine();
        Event event = findEventByName(eventName);

        if (event != null) {
            System.out.print("Enter Attendee Name: ");
            String attendeeName = scanner.nextLine();
            event.attendees.add(attendeeName);
            System.out.println("Attendee added successfully.");
        } else {
            System.out.println("Event not found.");
        }
    }

    static Event findEventByName(String eventName) {
        for (Event event : events) {
            if (event.eventName.equalsIgnoreCase(eventName)) {
                return event;
            }
        }
        return null;
    }
}

