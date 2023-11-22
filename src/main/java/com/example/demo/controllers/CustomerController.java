package com.example.demo.controllers;

import com.example.demo.model.*;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.Random;

//@RestController
@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private EventService eventService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private CustomerRepository customerRepository;
    private ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }
    // Register Endpoint
    @GetMapping("/register")
    public String registerCustomer(Model model) {
        model.addAttribute("newCustomer", new Customer());
        return "register-user";
    }

    @PostMapping("/register")
    public String addCustomer(@ModelAttribute Customer customer) {
        if (customerService.checkCustomerExist(customer)){
            Branch branch = branchService.getFirstBranchId();
            customer.setBranch(branch);
            customerService.addCustomer(customer);
            return "redirect:/customer/login";
        }
        else{
            return "redirect:/customer/register";
        }
    }

    // Login Endpoint
    @PostMapping("/login")
    public String loginCustomer(@ModelAttribute Customer customer, HttpSession session) {
        if (customerService.isValidCustomer(customer)) {
            return "redirect:/customer/login";
        } else {
            session.setAttribute("username", customer.getFullName());
            return "redirect:/customer/dashboard";
        }
    }

    @GetMapping("/login")
    public String loginUser(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            return "redirect:/customer/dashboard";
        }
        else {
            model.addAttribute("customer", new Customer());
            return "login-user";
        }
    }

    // Logout Endpoint
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("username");
        return "redirect:/customer/login";
    }

    // Dashboard Endpoint
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            model.addAttribute("username", username);
            return "dashboard-user";
        } else {
            return "redirect:/customer/login";
        }
    }

    // Ticket Endpoint
    @GetMapping("/tickets")
    public String getAllTickets(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            List<Ticket> tickets = ticketService.getAllTickets();
            model.addAttribute("tickets", tickets);
            return "tickets";
        } else {
            return "redirect:/customer/login";
        }
    }

    // Order Endpoint
    @GetMapping("/orders")
    public String getAllOrders(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            List<Order> orders = orderService.getAllOrders();
            model.addAttribute("orders", orders);
            return "orders";
        }
        return "redirect:/customer/login";
    }

    // Event Endpoint
    @GetMapping("/events")
    public String getAllEvents(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            List<Event> events = eventService.getAllEvents();
            model.addAttribute("events", events);
            return "events-user";
        }
        return "redirect:/customer/login";

    }

    @GetMapping("/events/{eventId}")
    public String getEventDetail(@PathVariable String eventId, Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            Optional<Event> event = eventService.getEventById(eventId);
            Event event2 = eventService.getById(eventId);
            List<TicketType> typeTicket = typeService.getByEventId(event2);
            model.addAttribute("event", event);
            model.addAttribute("typeTicket", typeTicket);
            return "event-detail-user";
        } else {
            return "redirect:/customer/login";
        }
    }

    @PostMapping("/events/{eventId}")
    public String orderTicket(@PathVariable String eventId, Model model, HttpSession session, @RequestParam("quantity") int quantity, @RequestParam("type") String type) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            Order order = new Order();
            model.addAttribute("eventId", eventId);
            for (int i = 0; i < quantity; i++) {
                // Add order into DB
                Branch branch = branchService.getFirstBranchId();
                order.setBranch(branch);
                order.setCreatedAt(LocalDateTime.now(zoneId));
                Customer customer = customerRepository.findByName(username);
                order.setCustomer(customer);
                order.setOrderStatus("YES");
                Optional<TicketType> t = typeService.findByName(type);
                BigDecimal q = BigDecimal.valueOf(quantity);
                order.setTotalPrice(t.get().getPrice().multiply(q));
                orderService.addOrder(order);
                // Add ticket into DB
                Event event = eventService.getById(eventId);
                Ticket ticket = new Ticket();
                ticket.setOrder(order);
                ticket.setCustomer(customer);
                ticket.setEvent(event);
                ticket.setBranch(branch);
                ticket.setTicketStatus("Pending");
                ticket.setPrice(t.get().getPrice());
                ticket.setTicketNumber(generateRandomString(10));
                ticketService.addTicket(ticket);
            }
            return "redirect:/customer/orders";
        } else {
            return "redirect:/customer/login";
        }
    }
}