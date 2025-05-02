package org.exexe.deliveryapp.repositories;

import org.exexe.deliveryapp.models.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {
    private final JdbcTemplate jdbcTemplate;

    JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Ingredient> getIngredients() {
        String sql = "SELECT * FROM public.ingredient";
        return jdbcTemplate.query(sql, this::mapRowToIngredient);
    }

    @Override
    public Optional<Ingredient> getIngredient(String id) {
        String sql = "SELECT * FROM public.ingredient where id = ?";
        List<Ingredient> results = jdbcTemplate.query(sql, this::mapRowToIngredient, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Ingredient saveIngredient(Ingredient ingredient) {
        String sql = "INSERT INTO PUBLIC.ingredient (id, name, type) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, ingredient.getId(), ingredient.getName(), ingredient.getType());
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet row, int rowNum) throws SQLException {
        return new Ingredient(row.getString("id"), row.getString("name"),
                Ingredient.Type.valueOf(row.getString("type")));
    }
}
