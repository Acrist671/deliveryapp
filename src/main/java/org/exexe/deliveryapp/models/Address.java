package org.exexe.deliveryapp.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {
    @NotBlank(message = "Delivery city is required")
    private String city;
    @NotBlank(message = "Delivery street is required")
    private String street;
    @NotBlank(message = "Delivery house is required")
    private String house;
    public Address() {}
    public Address (String city, String street, String house) {
        this.city = city;
        this.street = street;
        this.house = house;
    }
}
