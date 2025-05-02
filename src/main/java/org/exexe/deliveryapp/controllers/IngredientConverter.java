package org.exexe.deliveryapp.controllers;

import org.exexe.deliveryapp.models.Ingredient;
import org.exexe.deliveryapp.repositories.IngredientRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientConverter implements Converter<String, Ingredient> {
    private final IngredientRepository ingredientsRepository;

    public IngredientConverter(IngredientRepository ingredientRepository) {
        this.ingredientsRepository = ingredientRepository;
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientsRepository.getIngredient(id).orElse(null);
    }
}
