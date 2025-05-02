package org.exexe.deliveryapp.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Order implements Serializable {
    private long id;
    @NotBlank(message = "Username is required")
    private String userName;
    @Valid
    private Address address = new Address();
    @Valid
    private CardInfo cardInfo = new CardInfo();
    private Date placed_at = new Date();
    private List<Burger> burgers = new ArrayList<Burger>();

    public void addBurger(Burger burger) {
        burgers.add(burger);
    }

    public void addBurgers(List<Burger> burgers) {this.burgers.addAll(burgers);}
}
