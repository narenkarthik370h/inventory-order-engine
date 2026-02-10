package com.example.orderengine.service;

import com.example.orderengine.model.*;
import com.example.orderengine.repo.OrderRepo;
import com.example.orderengine.repo.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;

    public OrderService(OrderRepo orderRepo, ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    @Transactional
    public void createOrder(List<OrderItemRequest> requests) {

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);

        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest req : requests) {

            Product product = productRepo.findById(req.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getQuantityAvailable() < req.getQuantity()) {
                throw new RuntimeException("Insufficient stock");
            }

            product.setQuantityAvailable(
                    product.getQuantityAvailable() - req.getQuantity()
            );

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(req.getQuantity());
            item.setPriceAtOrder(product.getPrice());
            item.setOrder(order);

            items.add(item);

            total = total.add(
                    product.getPrice().multiply(
                            BigDecimal.valueOf(req.getQuantity())
                    )
            );
        }

        order.setItems(items);
        order.setTotalAmount(total);

        orderRepo.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public void updateStatus(Long id) {
        Order order = getOrderById(id);
        order.setStatus(OrderStatus.COMPLETED);
        orderRepo.save(order);
    }
}
