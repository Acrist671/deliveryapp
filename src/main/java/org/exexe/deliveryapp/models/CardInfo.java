package org.exexe.deliveryapp.models;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.io.Serializable;

@Data
public class CardInfo implements Serializable{
    @Pattern(regexp = "^[0-9]{16}$",message = "Credit card is invalid")
    private String cardNumber;
    @Pattern(regexp = "^(0[1-9]|1[0-2])/([0-1][0-9]|[8-9][0-9]|2[0-4])$", message = "Format should be MM/YY")
    private String expiryDate;
    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String cvv;
}
