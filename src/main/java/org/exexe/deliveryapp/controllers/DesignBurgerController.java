package org.exexe.deliveryapp.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.exexe.deliveryapp.models.Burger;
import org.exexe.deliveryapp.models.Ingredient;
import org.exexe.deliveryapp.models.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignBurgerController {

    @ModelAttribute
    public void AddIngredientsToModel(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("GRN", "Grain", Ingredient.Type.WRAP),
                new Ingredient("RYE", "Rye", Ingredient.Type.WRAP),
                new Ingredient("PRK", "Pork", Ingredient.Type.PROTEIN),
                new Ingredient("BEF", "Beef", Ingredient.Type.PROTEIN),
                new Ingredient("CHN", "Chicken", Ingredient.Type.PROTEIN),
                new Ingredient("CHR", "Cheddar", Ingredient.Type.CHEESE),
                new Ingredient("PSN", "Parmesan", Ingredient.Type.CHEESE),
                new Ingredient("TMS", "Tomatoes", Ingredient.Type.VEGETABLE),
                new Ingredient("CBG", "Cabbage", Ingredient.Type.VEGETABLE),
                new Ingredient("ONI", "Onion", Ingredient.Type.VEGETABLE),
                new Ingredient("CHS", "Cheesy", Ingredient.Type.SAUCE),
                new Ingredient("KUP", "Ketchup", Ingredient.Type.SAUCE)
        );
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
            List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients.
                stream().
                filter(ingredient -> ingredient.getType().equals(type))
                .collect(Collectors.toList());
    }
}
