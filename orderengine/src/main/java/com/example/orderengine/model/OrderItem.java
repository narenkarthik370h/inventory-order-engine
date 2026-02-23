package com.example.orderengine.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //referencing the product class key as foreign key here
    @ManyToOne
    private Product product;

    private Integer quantity;

    private BigDecimal priceAtOrder;

    //referencing the order class key as foreign key here
    //automatically identify primary key from the parent class @id is the primary key
    @ManyToOne
    private Order order;
}
