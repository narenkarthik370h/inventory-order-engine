package com.example.orderengine.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    private String description;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal price;
    @Min(0)
    private Integer quantityAvailable;
    private String imageType;
    private String imageName;
    private String imagePath;


    @Transient
    public boolean isInStock() {
        return quantityAvailable != null && quantityAvailable > 0;
    }
}
