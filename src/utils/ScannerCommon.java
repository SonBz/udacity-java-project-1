package utils;

import models.RoomType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerCommon {

    private static Scanner scanner = new Scanner(System.in);

    public static double scannerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException exp) {
            System.out.println("Invalid! Please, enter a valid double number.");
            return scannerDouble();
        }
    }


    public static RoomType scannerRoomType() {
        try {
            return RoomType.isRoomType(scanner.nextLine());
        } catch (IllegalArgumentException exp) {
            System.out.println("Invalid room type! Please, choose 1 for single bed or 2 for double bed:");
            return scannerRoomType();
        }
    }

    public static Date scannerDate() {
        try {
            return new SimpleDateFormat("yyyy/MM/dd").parse(scanner.nextLine());
        } catch (ParseException ex) {
            System.out.println("Error: Invalid date. Retype");
            scannerDate();
        }

        return null;
    }

    public static String scannerEmail() {
        String email = scanner.nextLine();
        Pattern pattern = Pattern.compile("^(.+)@(.+).com$");
        if (!pattern.matcher(email).matches()) {
            System.out.println("Error: Invalid email. Retype");
            scannerEmail();
        }
        return email;
    }
}
