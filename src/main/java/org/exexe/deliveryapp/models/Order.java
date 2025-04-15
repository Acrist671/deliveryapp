package org.exexe.deliveryapp.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Order implements Serializable {
    @NotBlank(message = "Username is required")
    private String userName;
    @Valid
    private Address address;
    @Valid
    private CardInfo cardInfo;
    private List<Burger> burgers = new ArrayList<Burger>();

    public void addBurger(Burger burger) {
        burgers.add(burger);
    }
}
