package org.exexe.deliveryapp.controllers;

import org.exexe.deliveryapp.models.Ingredient;
import org.springframework.core.convert.converter.Converter;

import java.util.HashMap;

public class IngredientConverter implements Converter<String, Ingredient> {
    private final HashMap<String, Ingredient> ingredients = new HashMap<>();

    public IngredientConverter() {
        ingredients.put("GRN", new Ingredient("GRN", "Grain", Ingredient.Type.WRAP));
        ingredients.put("RYE", new Ingredient("RYE", "Rye", Ingredient.Type.WRAP));
        ingredients.put("PRK", new Ingredient("PRK", "Pork", Ingredient.Type.PROTEIN));
        ingredients.put("BEF", new Ingredient("BEF", "Beef", Ingredient.Type.PROTEIN));
        ingredients.put("CHN", new Ingredient("CHN", "Chicken", Ingredient.Type.PROTEIN));
        ingredients.put("CHR", new Ingredient("CHR", "Cheddar", Ingredient.Type.CHEESE));
        ingredients.put("PSN", new Ingredient("PSN", "Parmesan", Ingredient.Type.CHEESE));
        ingredients.put("TMS", new Ingredient("TMS", "Tomatoes", Ingredient.Type.VEGETABLE));
        ingredients.put("CBG", new Ingredient("CBG", "Cabbage", Ingredient.Type.VEGETABLE));
        ingredients.put("ONI", new Ingredient("ONI", "Onion", Ingredient.Type.VEGETABLE));
        ingredients.put("CHS", new Ingredient("CHS", "Cheesy", Ingredient.Type.SAUCE));
        ingredients.put("KUP", new Ingredient("KUP", "Ketchup", Ingredient.Type.SAUCE));
    }

    @Override
    public Ingredient convert(String id) {
        return ingredients.get(id);
    }
}
