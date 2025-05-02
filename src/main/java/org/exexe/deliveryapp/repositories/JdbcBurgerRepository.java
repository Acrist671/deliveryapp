package org.exexe.deliveryapp.repositories;

import org.exexe.deliveryapp.models.Burger;
import org.exexe.deliveryapp.models.Ingredient;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcBurgerRepository implements BurgerRepository {
    private final JdbcIngredientRepository jdbcIngredientRepository;
    JdbcTemplate jdbcTemplate;

    public JdbcBurgerRepository(JdbcTemplate jdbcTemplate, JdbcIngredientRepository jdbcIngredientRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcIngredientRepository = jdbcIngredientRepository;
    }
    @Override
    public Optional<Burger> getBurger(int id) {
        String sql = "select * from burger where id = ?";
        List<Burger> burger = jdbcTemplate.query(sql, this::mapRowToBurger, id);
        return burger.isEmpty() ? Optional.empty() : Optional.of(burger.get(0));
    }

    @Override
    public List<Burger> getBurgersByOrderId(long orderId) {
        String sql = "select * from burger where burger_order_key = ?";
        return jdbcTemplate.query(sql, this::mapRowToBurger, orderId);
    }


    @Override
    @Transactional
    public long saveBurger(Burger burger, long orderId) {
        String sql = "INSERT INTO Burger (name, burger_order_key, ingredients, created_at) values (?,?,?,?)";
        String[] ingredients = burger.getIngredients().stream().map(Ingredient::getId).toArray(String[]::new);
        jdbcTemplate.update(sql, burger.getName(), orderId, ingredients, burger.getCreated_at());
        String sql2 = "select id from burger where name = ? and created_at = ?";
        try {
            return jdbcTemplate.queryForObject(sql2, this::mapRowToId,
                    burger.getName(), burger.getCreated_at());
        }
        catch (EmptyResultDataAccessException e){
            throw new IllegalStateException("id not found after saving burger", e);
        }
    }

    private long mapRowToId(ResultSet rs, int rowNum) throws SQLException {
        return rs.getLong("id");
    }

    private Burger mapRowToBurger(ResultSet resultSet, int i) throws SQLException {
        Burger burger = new Burger();
        burger.setId(resultSet.getLong("id"));
        burger.setName(resultSet.getString("name"));
        Array ingredients = resultSet.getArray("ingredients");
        String[] ingredientsId = (String[]) ingredients.getArray();
        List<Ingredient> ingredientList = Arrays.stream(ingredientsId).
                map(id -> jdbcIngredientRepository.getIngredient(id).orElse(null)).
                filter(Objects::nonNull).toList();
        burger.setIngredients(ingredientList);
        burger.setCreated_at(resultSet.getDate("created_at"));
        return burger;
    }
}
