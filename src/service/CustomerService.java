package service;

import models.Customer;

import java.util.ArrayList;
import java.util.Collection;

public class CustomerService {

    private static CustomerService INSTANCE;
    private final Collection<Customer> customers = new ArrayList<>();

    private CustomerService() {
    }

    public static CustomerService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CustomerService();
        }
        return INSTANCE;
    }

    public void addCustomer(String email, String firstName, String lastName) {
        customers.add(new Customer(firstName, lastName, email));
    }

    public Customer getCustomer(String customerEmail) {
        return customers.stream()
                .filter(c -> c.getEmail().equals(customerEmail))
                .findFirst()
                .orElse(null);
    }

    public Collection<Customer> getAllCustomers() {
        return customers;
    }
}
