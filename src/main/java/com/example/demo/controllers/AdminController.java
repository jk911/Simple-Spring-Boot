package com.example.demo.controllers;

import com.example.demo.model.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@RestController
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private BranchService branchService;
    @Autowired
    private EventService eventService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TypeService typeService;
    private ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard-admin";
    }
    // Branch Endpoint
    @GetMapping("/branches")
    public String getAllBranches(Model model) {
        List<Branch> branches = branchService.getAllBranches();
        model.addAttribute("brs", branches);
        model.addAttribute("newBranch", new Branch());
        return "branches";
    }

    @GetMapping("/branches/{branchId}")
    public String getBranchDetail(@PathVariable String branchId, Model model) {
        Optional<Branch> branch = branchService.getBranchById(branchId);
        model.addAttribute("branch", branch);
        return "branch-detail";
    }

    @PostMapping("/branches")
    public String addBranch(@ModelAttribute Branch branch) {
        branch.setCreatedAt(LocalDateTime.now(zoneId));
        branchService.addBranch(branch);
        return "redirect:/admin/branches";
    }

    @GetMapping("/branches/delete/{branchId}")
    public String deleteBranch(@PathVariable String branchId) {
        branchService.deleteBranchById(branchId);
        return "redirect:/admin/branches";
    }

    // Event Endpoint
    @GetMapping("/events")
    public String getAllEvents(Model model) {
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        model.addAttribute("newEvent", new Event());
        return "events";
    }

    @GetMapping("/events/{eventId}")
    public String getEventDetail(@PathVariable String eventId, Model model) {
        Optional<Event> event = eventService.getEventById(eventId);
        model.addAttribute("event", event);
        return "event-detail-admin";
    }

    @PostMapping("/events/update/{eventId}")
    public String updateEvent(@PathVariable String eventId, @ModelAttribute Event event) {
        event.setId(eventId);
        event.setCreatedAt(LocalDateTime.now(zoneId));
        event.setOpenForTicket(true);
        Branch branch = branchService.getFirstBranchId();
        event.setBranch(branch);
        eventService.updateEventById(event);
        return "redirect:/admin/events";
    }
    @GetMapping("/events/delete/{eventId}")
    public String deleteEvent(@PathVariable String eventId) {
        eventService.deleteEventById(eventId);
        return "redirect:/admin/events";
    }
    @PostMapping("/events")
    public String addEvent(@ModelAttribute Event event) {
        event.setCreatedAt(LocalDateTime.now(zoneId));
        event.setOpenForTicket(true);
        Branch branch = branchService.getFirstBranchId();
        event.setBranch(branch);
        eventService.addEvent(event);
        return "redirect:/admin/events";
    }

    // Ticket Endpoint
    @GetMapping("/tickets")
    public String getAllTickets(Model model) {
        List<Ticket> tickets = ticketService.getAllTickets();
        model.addAttribute("tickets", tickets);
        return "tickets";
    }

    // Order Endpoint
    @GetMapping("/orders")
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders";
    }

    // Customer Endpoint
    @GetMapping("/customers")
    public String getAllCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("cus", customers);
        model.addAttribute("newCustomer", new Customer());
        return "customers";
    }

    @GetMapping("/customers/{customerId}")
    public String getCustomerDetail(@PathVariable String customerId, Model model) {
        Optional<Customer> customer = customerService.getCustomerById(customerId);
        model.addAttribute("customer", customer);
        return "customer-detail";
    }

    @GetMapping("/customers/delete/{customerId}")
    public String deleteCustomer(@PathVariable String customerId) {
        customerService.deleteCustomerById(customerId);
        return "redirect:/admin/customers";
    }

    @PostMapping("/customers")
    public String addCustomer(@ModelAttribute Customer customer) {
        Branch branch = branchService.getFirstBranchId();
        customer.setBranch(branch);
        customerService.addCustomer(customer);
        return "redirect:/admin/customers";
    }

    // Ticket Type Endpoint
    @GetMapping("/types")
    public String getAllTypes(Model model) {
        List<TicketType> types = typeService.getAllTypes();
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        model.addAttribute("types", types);
        model.addAttribute("newType", new TicketType());
        return "types";
    }

    @GetMapping("/types/delete/{typeId}")
    public String deleteType(@PathVariable String typeId) {
        typeService.deleteTypeById(typeId);
        return "redirect:/admin/types";
    }
    @PostMapping("/types")
    public String addType(@ModelAttribute TicketType type) {
        type.setCreatedAt(LocalDateTime.now(zoneId));
        typeService.addType(type);
        return "redirect:/admin/types";
    }
}