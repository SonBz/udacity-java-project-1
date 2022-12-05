package api;

import models.Customer;
import models.IRoom;
import models.Reservation;
import service.CustomerService;
import service.ReservationService;
import utils.DateCommon;

import java.util.Collection;
import java.util.Date;

public class HotelResource {

    private static HotelResource INSTANCE;
    private final ReservationService reservationService;
    private final CustomerService customerService;

    public HotelResource(ReservationService reservationService, CustomerService customerService) {
        this.reservationService = reservationService;
        this.customerService = customerService;
    }

    public static HotelResource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HotelResource(ReservationService.getInstance(), CustomerService.getInstance());
        }
        return INSTANCE;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getIRoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date CheckOutDate) {
        return reservationService.reserveARoom(this.getCustomer(customerEmail), room, checkInDate, CheckOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        return reservationService.getCustomerReservations(new Customer(customerEmail));
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }

    public Collection<IRoom> getAlternativeRooms(Date checkIn, Date checkOut) {
        return findARoom(DateCommon.addPlusDay(checkIn), DateCommon.addPlusDay(checkOut));
    }


}
