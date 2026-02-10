package com.example.orderengine.repo;

import com.example.orderengine.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
    // this is like SELECT * FROM product
    //WHERE LOWER(name) LIKE LOWER('%input%');
    List<Product> findByQuantityAvailableGreaterThan(int quantity);
//    SELECT * FROM product
//    WHERE quantity_available > ?
    List<Product> findByQuantityAvailableEquals(int quantity);
//    SELECT * FROM product
//    WHERE quantity_available = ?

}
