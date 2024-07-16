package com.railwayreservationsystem;

import java.util.*;
import java.util.stream.Collectors;

class Train {
    String trainName;
    String trainNumber;
    String source;
    String destination;
    int totalSeats;
    int availableSeats;

    public Train(String trainName, String trainNumber, String source, String destination, int totalSeats) {
        this.trainName = trainName;
        this.trainNumber = trainNumber;
        this.source = source;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }

    @Override
    public String toString() {
        return String.format("%-15s %-10s %-15s %-15s %-5d", trainName, trainNumber, source, destination, availableSeats);
    }
}

class Reservation {
    String passengerName;
    String trainNumber;
    int seatNumber;

    public Reservation(String passengerName, String trainNumber, int seatNumber) {
        this.passengerName = passengerName;
        this.trainNumber = trainNumber;
        this.seatNumber = seatNumber;
    }

    @Override
    public String toString() {
        return String.format("%-15s %-10s %-5d", passengerName, trainNumber, seatNumber);
    }
}

public class RailwayReservationSystem {
    static List<Train> trains = new ArrayList<>();
    static List<Reservation> reservations = new ArrayList<>();

    public static void main(String[] args) {
        initializeData();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nRailway Reservation System");
            System.out.println("1. View available trains");
            System.out.println("2. Book a ticket");
            System.out.println("3. Cancel a reservation");
            System.out.println("4. View reservation details");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewAvailableTrains();
                    break;
                case 2:
                    bookTicket(scanner);
                    break;
                case 3:
                    cancelReservation(scanner);
                    break;
                case 4:
                    viewReservationDetails(scanner);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void initializeData() {
        trains.add(new Train("Express1", "101", "CityA", "CityB", 100));
        trains.add(new Train("Express2", "102", "CityC", "CityD", 150));
        trains.add(new Train("Express3", "103", "CityE", "CityF", 200));
        // Add more trains as needed
    }

    static void viewAvailableTrains() {
        System.out.printf("%-15s %-10s %-15s %-15s %-5s\n", "Train Name", "Train No", "Source", "Destination", "Seats");
        System.out.println("--------------------------------------------------------------");
        for (Train train : trains) {
            System.out.println(train);
        }
    }

    static void bookTicket(Scanner scanner) {
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Enter Train Number: ");
        String trainNumber = scanner.nextLine();
        Train train = findTrainByNumber(trainNumber);

        if (train != null && train.availableSeats > 0) {
            System.out.print("Enter Passenger Name: ");
            String passengerName = scanner.nextLine();
            int seatNumber = train.totalSeats - train.availableSeats + 1;
            reservations.add(new Reservation(passengerName, trainNumber, seatNumber));
            train.availableSeats--;
            System.out.println("Ticket booked successfully. Seat Number: " + seatNumber);
        } else {
            System.out.println("Train not found or no seats available.");
        }
    }

    static void cancelReservation(Scanner scanner) {
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Enter Train Number: ");
        String trainNumber = scanner.nextLine();
        System.out.print("Enter Passenger Name: ");
        String passengerName = scanner.nextLine();
        Reservation reservation = findReservationByDetails(trainNumber, passengerName);

        if (reservation != null) {
            reservations.remove(reservation);
            Train train = findTrainByNumber(trainNumber);
            if (train != null) {
                train.availableSeats++;
                System.out.println("Reservation canceled successfully.");
            }
        } else {
            System.out.println("Reservation not found.");
        }
    }

    static void viewReservationDetails(Scanner scanner) {
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Enter Train Number: ");
        String trainNumber = scanner.nextLine();
        System.out.print("Enter Passenger Name: ");
        String passengerName = scanner.nextLine();
        Reservation reservation = findReservationByDetails(trainNumber, passengerName);

        if (reservation != null) {
            System.out.printf("%-15s %-10s %-5s\n", "Passenger Name", "Train No", "Seat No");
            System.out.println("--------------------------------------");
            System.out.println(reservation);
        } else {
            System.out.println("Reservation not found.");
        }
    }

    static Train findTrainByNumber(String trainNumber) {
        for (Train train : trains) {
            if (train.trainNumber.equalsIgnoreCase(trainNumber)) {
                return train;
            }
        }
        return null;
    }

    static Reservation findReservationByDetails(String trainNumber, String passengerName) {
        for (Reservation reservation : reservations) {
            if (reservation.trainNumber.equalsIgnoreCase(trainNumber) && reservation.passengerName.equalsIgnoreCase(passengerName)) {
                return reservation;
            }
        }
        return null;
    }
}

