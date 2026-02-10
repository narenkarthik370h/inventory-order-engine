package com.example.orderengine.controller;

import com.example.orderengine.model.OrderItemRequest;
import com.example.orderengine.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class OrderController {
    @Autowired
    private OrderService service;

    @PostMapping("/orders")
    public ResponseEntity<String> createOrder(
            @RequestBody List<OrderItemRequest> orderRequest) {

        service.createOrder(orderRequest);
        return new ResponseEntity<>("Order created", HttpStatus.CREATED);
    }


    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOrderById(id));
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long id) {
        service.updateStatus(id);
        return ResponseEntity.ok("Order status updated");
    }
}

