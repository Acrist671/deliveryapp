package org.exexe.deliveryapp.repositories;


import org.exexe.deliveryapp.models.Ingredient;

import java.util.Optional;

public interface IngredientRepository {
    Iterable<Ingredient> getIngredients();

    Optional<Ingredient> getIngredient(String id);

    Ingredient saveIngredient(Ingredient ingredient);
}
