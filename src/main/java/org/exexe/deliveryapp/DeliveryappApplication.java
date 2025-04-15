package org.exexe.deliveryapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DeliveryappApplication {

    public static void main(String[] args) {
        System.setProperty("server.servlet.session.persistent", "false");
        SpringApplication.run(DeliveryappApplication.class, args);
    }

}
