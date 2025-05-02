package org.exexe.deliveryapp.repositories;

import org.exexe.deliveryapp.models.Address;
import org.exexe.deliveryapp.models.CardInfo;
import org.exexe.deliveryapp.models.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    private final JdbcTemplate jdbcTemplate;
    private final JdbcBurgerRepository jdbcBurgerRepository;

    public JdbcOrderRepository(JdbcTemplate jdbcTemplate, JdbcBurgerRepository jdbcBurgerRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcBurgerRepository = jdbcBurgerRepository;
    }

    @Override
    public Optional<Order> getOrder(int id) {
        String sql = "select * from Burger_Order where id = ?";
        List<Order> result = jdbcTemplate.query(sql, this::mapRowtoOrder, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    @Transactional
    public long save(Order order) {
        String sql = "INSERT INTO Burger_Order (name, city, street, house, card_number," +
                "expire_date, card_cvv, placed_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Address address = order.getAddress();
        CardInfo cardInfo = order.getCardInfo();
        jdbcTemplate.update(sql, order.getUserName(), address.getCity(), address.getStreet(), address.getHouse(),
                cardInfo.getCardNumber(), cardInfo.getExpiryDate(), cardInfo.getCvv(), order.getPlaced_at());
        return getOrderId(cardInfo.getCardNumber(), order.getPlaced_at());
    }

    public long getOrderId(String cardNumber, Date placed_at) {
        String sql = "select id from Burger_Order where card_number = ? and placed_at = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRowtoOrderId, cardNumber, placed_at);
        } catch (EmptyResultDataAccessException e){
            throw new IllegalStateException("Order not found after insert", e);
        }
    }

    private long mapRowtoOrderId(ResultSet rs, int rowNum) throws SQLException {
        return rs.getLong("id");
    }

    private Order mapRowtoOrder(ResultSet resultSet, int i) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getLong("id"));
        order.setUserName(resultSet.getString("user_name"));
        order.setAddress(new Address(resultSet.getString("city"),
                resultSet.getString("street"), resultSet.getString("house")));
        order.setCardInfo(new CardInfo(resultSet.getString("card_number"),
                resultSet.getString("expire_date"),
                resultSet.getString("card_cvv")));
        order.addBurgers(jdbcBurgerRepository.getBurgersByOrderId(getOrderId
                (resultSet.getString("card_number"),
                resultSet.getDate("placed_at"))));
        order.setPlaced_at(resultSet.getTimestamp("placed_at"));
        return order;
    }
}
