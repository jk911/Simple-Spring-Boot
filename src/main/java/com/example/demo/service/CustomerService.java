package com.example.demo.service;

import com.example.demo.model.Branch;
import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import com.example.demo.model.Ticket;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private OrderService orderService;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(String customerId){
        return customerRepository.findById(customerId);
    }
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomerById(String customerID){
        customerRepository.deleteById(customerID);
    }

    public boolean isValidCustomer(Customer customer) {
        Optional<Customer> result = customerRepository.findByUsernameAndPassword(customer.getFullName(), customer.getPassword());
        return result.isEmpty();
    }

    public boolean checkCustomerExist(Customer customer){
        Optional<Customer> result = customerRepository.findByUsername(customer.getFullName());
        return result.isEmpty();
    }

    public boolean isValidCustomerOrder(Order order) {
        // Add logic to validate customer order
        return true;
    }

    public void buyTicket(Ticket ticket) {
        ticketService.buyTicket(ticket);
    }

    public void payOrder(Order order) {
        orderService.payOrder(order);
    }

}
