package api;

import models.Customer;
import models.IRoom;
import models.Room;
import models.RoomType;
import service.CustomerService;
import service.ReservationService;
import utils.ScannerCommon;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdminResource {

    private static AdminResource INSTANCE;
    private final CustomerService customerService;
    private final ReservationService reservationService;

    private AdminResource(CustomerService customerService,
                          ReservationService reservationService) {
        this.customerService = customerService;
        this.reservationService = reservationService;
    }

    public static AdminResource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AdminResource(CustomerService.getInstance(), ReservationService.getInstance());
        }
        return INSTANCE;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms) {
        rooms.forEach(reservationService::addRoom);
    }

    public void getAllRooms() {
        Collection<IRoom> rooms = reservationService.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            reservationService.getAllRooms().forEach(System.out::println);
        }
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        reservationService.printAllReservation();
    }

    public List<IRoom> createRoom(List<IRoom> iRooms) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter room number:");
        String roomNumber = scanner.nextLine();

        System.out.println("Enter price per night:");
        double roomPrice = ScannerCommon.scannerDouble();

        System.out.println("Enter room type: 1 for single bed, 2 for double bed:");
        RoomType roomType = ScannerCommon.scannerRoomType();

        Room room = new Room(roomNumber, roomPrice, roomType);
        iRooms.add(room);
        System.out.println("Room added successfully!");
        this.addAnotherRoom(iRooms);
        return iRooms;
    }

    private void addAnotherRoom(List<IRoom> iRooms) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Add another room? Y/N");
            String yesOrNoRoom = scanner.nextLine();
            while ((yesOrNoRoom.charAt(0) != 'Y' && yesOrNoRoom.charAt(0) != 'N') || yesOrNoRoom.length() != 1) {
                System.out.println("Please enter Y (Yes) or N (No)");
                yesOrNoRoom = scanner.nextLine();
            }

            if (yesOrNoRoom.charAt(0) == 'Y' || yesOrNoRoom.charAt(0) == 'y') {
                this.createRoom(iRooms);
            }
        } catch (StringIndexOutOfBoundsException ex) {
            addAnotherRoom(iRooms);
        }
    }

}
