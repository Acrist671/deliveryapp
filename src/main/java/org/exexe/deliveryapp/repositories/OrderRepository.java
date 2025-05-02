package org.exexe.deliveryapp.repositories;

import org.exexe.deliveryapp.models.Order;

import java.util.Optional;

public interface OrderRepository {
    Optional<Order> getOrder(int id);
    long save(Order order);
}
