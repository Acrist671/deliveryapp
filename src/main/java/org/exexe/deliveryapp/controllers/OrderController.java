package org.exexe.deliveryapp.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.exexe.deliveryapp.models.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/orders")
@Slf4j
@SessionAttributes("order")
public class OrderController {

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
        status.setComplete();
        return "redirect:/";
    }
}
