package org.exexe.deliveryapp.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.exexe.deliveryapp.models.Burger;
import org.exexe.deliveryapp.models.Order;
import org.exexe.deliveryapp.repositories.BurgerRepository;
import org.exexe.deliveryapp.repositories.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
@Slf4j
@SessionAttributes("order")
public class OrderController {
    private final OrderRepository orderRepository;
    private final BurgerRepository burgerRepository;

    public OrderController(OrderRepository orderRepository, BurgerRepository burgerRepository) {
        this.orderRepository = orderRepository;
        this.burgerRepository = burgerRepository;
    }

    @GetMapping("/current")
    public String orderForm(@ModelAttribute Order order, Model model) {
        model.addAttribute("order", order);
        return "orderForm";
    }

    @PostMapping
    public String orderSubmit(@Valid Order order, Errors errors, SessionStatus status) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        log.info("Order submitted: {}", order);
        order.setId(orderRepository.save(order));
        for (int i = 0; i < order.getBurgers().size(); i++) {
            order.getBurgers().get(i).setId(burgerRepository.
                    saveBurger(order.getBurgers().get(i), order.getId()));
        }
        log.info("Order saved: Order ID: {}, Burgers ID: {}", order.getId(),
                order.getBurgers().stream().map(Burger::getId).collect(Collectors.toList()));
        status.setComplete();
        return "redirect:/";
    }
}
