package com.ticketreservationsystem;

import java.util.*;

class Reservation {
    String pnr;
    String passengerName;
    String source;
    String destination;
    int seatNumber;

    public Reservation(String pnr, String passengerName, String source, String destination, int seatNumber) {
        this.pnr = pnr;
        this.passengerName = passengerName;
        this.source = source;
        this.destination = destination;
        this.seatNumber = seatNumber;
    }

    @Override
    public String toString() {
        return String.format("PNR: %s, Name: %s, From: %s, To: %s, Seat No: %d", pnr, passengerName, source, destination, seatNumber);
    }
}

public class TicketReservationSystem {
    static List<Reservation> reservations = new ArrayList<>();
    static Queue<Reservation> waitingList = new LinkedList<>();
    static int totalSeats = 50;
    static int nextSeatNumber = 1;
    static Map<Integer, List<String>> seatOccupancy = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nTicket Reservation System");
            System.out.println("1. Book a ticket");
            System.out.println("2. Cancel a reservation");
            System.out.println("3. View PNR details");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    bookTicket(scanner);
                    break;
                case 2:
                    cancelReservation(scanner);
                    break;
                case 3:
                    viewPnrDetails(scanner);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void bookTicket(Scanner scanner) {
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Enter Passenger Name: ");
        String passengerName = scanner.nextLine();
        System.out.print("Enter Source Station (A/B/C/D/E/F): ");
        String source = scanner.nextLine().toUpperCase();
        System.out.print("Enter Destination Station (A/B/C/D/E/F): ");
        String destination = scanner.nextLine().toUpperCase();

        if (source.equals(destination) || "ABCDEF".indexOf(source) > "ABCDEF".indexOf(destination)) {
            System.out.println("Invalid source and destination. Please try again.");
            return;
        }

        String pnr = generatePnr();
        Reservation newReservation = new Reservation(pnr, passengerName, source, destination, nextSeatNumber);

        if (nextSeatNumber <= totalSeats) {
            reservations.add(newReservation);
            updateSeatOccupancy(newReservation);
            nextSeatNumber++;
            System.out.println("Ticket booked successfully. PNR: " + pnr);
        } else {
            waitingList.add(newReservation);
            System.out.println("No available seats. Added to waiting list. PNR: " + pnr);
        }
    }

    static void cancelReservation(Scanner scanner) {
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Enter PNR to cancel: ");
        String pnr = scanner.nextLine();

        Reservation reservation = findReservationByPnr(pnr);
        if (reservation != null) {
            reservations.remove(reservation);
            updateSeatOccupancy(reservation, false);
            System.out.println("Reservation cancelled successfully.");

            if (!waitingList.isEmpty()) {
                Reservation waitingReservation = waitingList.poll();
                waitingReservation.seatNumber = reservation.seatNumber;
                reservations.add(waitingReservation);
                updateSeatOccupancy(waitingReservation);
                System.out.println("Waiting list updated. PNR: " + waitingReservation.pnr);
            }
        } else {
            System.out.println("Reservation not found.");
        }
    }

    static void viewPnrDetails(Scanner scanner) {
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Enter PNR: ");
        String pnr = scanner.nextLine();

        Reservation reservation = findReservationByPnr(pnr);
        if (reservation != null) {
            System.out.println(reservation);
        } else {
            System.out.println("Reservation not found.");
        }
    }

    static String generatePnr() {
        return "PNR" + (reservations.size() + waitingList.size() + 1);
    }

    static Reservation findReservationByPnr(String pnr) {
        for (Reservation reservation : reservations) {
            if (reservation.pnr.equalsIgnoreCase(pnr)) {
                return reservation;
            }
        }
        return null;
    }

    static void updateSeatOccupancy(Reservation reservation) {
        updateSeatOccupancy(reservation, true);
    }

    static void updateSeatOccupancy(Reservation reservation, boolean isAdd) {
        for (char station = reservation.source.charAt(0); station < reservation.destination.charAt(0); station++) {
            int seatNumber = reservation.seatNumber;
            seatOccupancy.putIfAbsent(seatNumber, new ArrayList<>());
            if (isAdd) {
                seatOccupancy.get(seatNumber).add(String.valueOf(station));
            } else {
                seatOccupancy.get(seatNumber).remove(String.valueOf(station));
            }
        }
    }
}

