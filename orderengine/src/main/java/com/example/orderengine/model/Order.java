package com.example.orderengine.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity //signal to send the data as the database table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")//annotation to keep the table name as orders or else by default table name is class name
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private BigDecimal totalAmount;

    // the line below tells orm that order is the parent of orderitem and
    //cascade tells that any effect that takes upon the parent class, the changes would be reflected on the child
    //order is parent and child has the freedom to use it as foreign key
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;
}
