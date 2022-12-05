package UI;

import api.AdminResource;
import models.IRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    private final AdminResource adminResource = AdminResource.getInstance();

    public void showMenu() {
        boolean keepRuning = true;
        Scanner scanner = new Scanner(System.in);

        while (keepRuning) {
            try {
                this.displayMenu();
                int selection = Integer.parseInt(scanner.nextLine());
                switch (selection) {
                    case 1:
                        adminResource.getAllCustomers().forEach(System.out::println);
                        keepRuning = true;
                        break;
                    case 2:
                        adminResource.getAllRooms();
                        keepRuning = true;
                        break;
                    case 3:
                        adminResource.displayAllReservations();
                        keepRuning = true;
                        break;
                    case 4:
                        List<IRoom> createRoom = adminResource.createRoom(new ArrayList<>());
                        adminResource.addRoom(createRoom);
                        keepRuning = true;
                        break;
                    case 5:
                        MainMenu mainMenu = new MainMenu();
                        mainMenu.showMenu();
                        keepRuning = false;
                        break;
                    case 6:
                        keepRuning = false;
                        break;
                }

            } catch (Exception ex) {
                System.out.println("\n Error: Invalid Input \n");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=============== Admin menu SBZ ================");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
        System.out.println("================= SBZ ==================");
        System.out.println("Please select a number for the menu option");
    }


}
