package UI;

import api.HotelResource;
import models.Customer;
import models.IRoom;
import utils.DateCommon;
import utils.ScannerCommon;

import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {

    private final HotelResource hotelResource = HotelResource.getInstance();
    private Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        boolean keepRuning = true;
        Scanner scanner = new Scanner(System.in);

        while (keepRuning) {
            try {
                this.displayMenu();
                int selection = Integer.parseInt(scanner.nextLine());
                switch (selection) {
                    case 1:
                        this.findAndReserveARoom();
                        keepRuning = true;
                        break;
                    case 2:
                        System.out.println("Enter email: ");
                        String email = ScannerCommon.scannerEmail();
                        hotelResource.getCustomersReservations(email).forEach(System.out::println);
                        keepRuning = true;
                        break;
                    case 3:
                        this.createAccount();
                        keepRuning = true;
                        break;
                    case 4:
                        AdminMenu adminMenu = new AdminMenu();
                        adminMenu.showMenu();
                        keepRuning = false;
                        break;
                    case 5:
                        keepRuning = false;
                        break;
                }

            } catch (Exception ex) {
                System.out.println("\n Error: Invalid Input \n");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=============== Main menu SBZ ================");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("=================== SBZ ===================");
        System.out.println("Please select a number for the menu option");
    }

    private void findAndReserveARoom() {
        System.out.println("Enter check-in date (yyyy/MM/dd) example 2022/07/22: ");
        Date checkIn = ScannerCommon.scannerDate();
        System.out.println("Enter check-out date (yyyy/MM/dd) example 2022/07/22: ");
        Date checkOut = ScannerCommon.scannerDate();
        while (checkIn.compareTo(checkOut) > 0) {
            System.out.println("Error: check-out date < check-in date. Retype");
            System.out.println("Enter check-out date (yyyy/MM/dd) example 2022/07/22: ");
            checkOut = ScannerCommon.scannerDate();
        }
        var availableRooms = hotelResource.findARoom(checkIn, checkOut);
        if (availableRooms.isEmpty()) {
            var recommendedRooms = hotelResource.getAlternativeRooms(checkIn, checkOut);
            if (recommendedRooms.isEmpty()) {
                System.out.println("No find rooms");
                return;
            }
            System.out.println("Recommended rooms date: Check-In Date " + DateCommon.addPlusDay(checkIn) +
                    " -> Check-Out Date :" + DateCommon.addPlusDay(checkOut));
            recommendedRooms.forEach(System.out::println);
            return;
        }
        System.out.println("Available rooms:");
        availableRooms.forEach(System.out::println);
        this.bookRoom(checkIn, checkOut, availableRooms);

    }

    private void bookRoom(Date checkIn, Date checkOut, Collection<IRoom> availableRooms) {
        System.out.println("Select room number would you like to reserve: ");
        String roomNumber = scanner.nextLine();
        if (!availableRooms.stream().anyMatch(ar -> ar.getRoomNumber().equals(roomNumber))) {
            System.out.println("Error: room number not available. Retype");
            bookRoom(checkIn, checkOut, availableRooms);
            return;
        }
        var room = hotelResource.getRoom(roomNumber);
        if (room == null) {
            System.out.println("Does not exist room number ");
        }
        System.out.println("Enter your email: eg. name@domain.com");
        String email = ScannerCommon.scannerEmail();
        try {
            Customer customer = hotelResource.getCustomer(email);
            if (customer == null) {
                System.out.println("==== Customer does not exist, please create account ====");
                this.createAccount();
            }
            hotelResource.bookARoom(email, room, checkIn, checkOut);
            System.out.println("Booking is successful");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void createAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Email format: name@domain.com");
        String email = ScannerCommon.scannerEmail();
        System.out.println("Enter First Name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter Last Name:");
        String lastName = scanner.nextLine();

        try {
            hotelResource.createACustomer(email, firstName, lastName);
            System.out.println("Account created successfully!");
            System.out.println("Welcome to Hotel SBZ");

        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            createAccount();
        }
    }

}
