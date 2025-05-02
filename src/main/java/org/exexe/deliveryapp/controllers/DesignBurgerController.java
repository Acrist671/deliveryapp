package org.exexe.deliveryapp.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.exexe.deliveryapp.models.Burger;
import org.exexe.deliveryapp.models.Ingredient;
import org.exexe.deliveryapp.models.Order;
import org.exexe.deliveryapp.repositories.IngredientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignBurgerController {

    private final IngredientRepository ingredientRepository;

    public DesignBurgerController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @ModelAttribute
    public void AddIngredientsToModel(Model model) {
        Iterable<Ingredient> ingredients = ingredientRepository.getIngredients();
        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), FilterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "order")
    public Order BurgerOrder() {
        return new Order();
    }

    @ModelAttribute(name = "burger")
    public Burger Burger() {
        return new Burger();
    }

    @GetMapping
    public String ShowDesignForm() {
        return "design";
    }

    @PostMapping
    public String ProcessBurger(@Valid Burger burger, Errors errors, @ModelAttribute Order order) {
        if (errors.hasErrors()){
            return "design";
        }
        order.addBurger(burger);
        log.info("Processed Burger:{}", burger);
        return "redirect:/orders/current";
    }
    private Iterable<Ingredient> FilterByType(
            Iterable<Ingredient> ingredients, Ingredient.Type type) {
        return StreamSupport.stream(ingredients.spliterator(), false)
                .filter(ingredient -> ingredient.getType().equals(type))
                .collect(Collectors.toList());
    }
}
