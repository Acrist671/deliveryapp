package org.exexe.deliveryapp.models;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;

@Data
public class CardInfo implements Serializable{
    @Pattern(regexp = "^[0-9]{16}$",message = "Credit card is invalid")
    private String cardNumber;
    @Pattern(regexp = "^(0[1-9]|1[0-2])/([2-3][0-9])$", message = "Format should be MM/YY")
    private String expiryDate;
    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String cvv;
    public CardInfo() {}
    public CardInfo(String cardNumber, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }
}
