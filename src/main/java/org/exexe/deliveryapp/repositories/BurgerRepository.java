package org.exexe.deliveryapp.repositories;

import org.exexe.deliveryapp.models.Burger;

import java.util.Optional;

public interface BurgerRepository {
    public Optional<Burger> getBurger(int id);
    public Iterable<Burger> getBurgersByOrderId(long orderId);
    public long saveBurger(Burger burger, long orderId);
}
