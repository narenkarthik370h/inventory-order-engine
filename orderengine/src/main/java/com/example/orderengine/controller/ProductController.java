package com.example.orderengine.controller;

import com.example.orderengine.model.Product;
import com.example.orderengine.service.OrderService;
import com.example.orderengine.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class ProductController {
    @Autowired
    private ProductService service;
;
    @GetMapping("/")
    public String greet() {
        return "WELCOME TO ORDER ENGINE";
    }

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try{
            Product product1=service.addProduct(product);
            return new ResponseEntity<>(product1,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try{
            Product product2=service.getProductById(id);
            return new ResponseEntity<>(product2,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                                @RequestBody Product product) {

        service.updateProduct(id, product);
        return new ResponseEntity<>("Updated", HttpStatus.OK);

    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        if(service.getProductById(id)!=null){
            service.deleteProduct(id);
        }
        return new ResponseEntity<>("id not found",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/products/{id}/image")
    public ResponseEntity<String> addImageById(@PathVariable Long id,
                                               @RequestPart MultipartFile imageFile) {
        service.addImage(id,imageFile);
        return new ResponseEntity<>("image added",HttpStatus.CREATED);
    }

    @GetMapping("/products/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        byte[] image = service.getImage(id);
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG) // or PNG dynamically later
                .body(image);
    }

}

