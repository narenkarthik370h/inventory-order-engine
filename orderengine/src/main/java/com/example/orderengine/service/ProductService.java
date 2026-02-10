package com.example.orderengine.service;

import com.example.orderengine.model.Product;
import com.example.orderengine.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ProductService {
@Autowired
ProductRepo repo;

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product addProduct(Product product) {
        return repo.save(product);
    }

    public Product getProductById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }


    public Product updateProduct(Long id, Product product) {
        Product existing = getProductById(id);
        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setQuantityAvailable(product.getQuantityAvailable());

        return repo.save(existing);

    }

    public void deleteProduct(Long id) {
        Product existing = getProductById(id);
        repo.delete(existing);

    }


    public void addImage(Long id, MultipartFile imageFile) {

        Product product = getProductById(id);

        try {
            String uploadDir = "uploads/";
            Files.createDirectories(Paths.get(uploadDir));

            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);

            Files.write(filePath, imageFile.getBytes());

            product.setImageName(fileName);
            product.setImageType(imageFile.getContentType());
            product.setImagePath(filePath.toString());

            repo.save(product);

        } catch (IOException e) {
            throw new RuntimeException("Failed to store image");
        }
    }


    public byte[] getImage(Long id) {

        Product product = getProductById(id);

        if (product.getImagePath() == null) {
            throw new RuntimeException("Image not found");
        }

        try {
            return Files.readAllBytes(Paths.get(product.getImagePath()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image");
        }
    }

}
