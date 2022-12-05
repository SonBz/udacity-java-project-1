package service;

import models.Customer;
import models.IRoom;
import models.Reservation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.stream.Collectors;

public class ReservationService {

    private static ReservationService INSTANCE;
    private static final Collection<IRoom> rooms = new ArrayList<>();
    private static final Collection<Reservation> reservationCollection = new HashSet<>();

    private ReservationService() {
    }

    public static ReservationService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReservationService();
        }
        return INSTANCE;
    }


    public void addRoom(IRoom iRoom) {
        rooms.add(iRoom);
    }

    public IRoom getIRoom(String roomId) {
        return rooms.stream()
                .filter(r -> r.getRoomNumber().equals(roomId))
                .findFirst()
                .orElse(null);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservationCollection.add(reservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        var roomNumbers = reservationCollection.stream()
                .filter(r -> r.getCheckInDate().equals(checkInDate))
                .filter(r -> r.getCheckOutDate().equals(checkOutDate))
                .map(r -> r.getiRoom().getRoomNumber())
                .collect(Collectors.toList());
        return rooms.stream()
                .filter(r -> !roomNumbers.contains(r.getRoomNumber()))
                .collect(Collectors.toList());
    }

    public Collection<Reservation> getCustomerReservations(Customer customer) {
        return reservationCollection.stream()
                .filter(r -> r.getCustomer().getEmail().equals(customer.getEmail()))
                .collect(Collectors.toList());
    }

    public void printAllReservation() {
        reservationCollection.forEach(System.out::println);
    }

    public Collection<IRoom> getAllRooms() {
        return rooms;
    }

    Collection<IRoom> searchRooms() {
        return rooms;
    }

    private void showReservation() {
        reservationCollection.forEach(System.out::println);
    }
}
