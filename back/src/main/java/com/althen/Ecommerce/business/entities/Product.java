package com.althen.Ecommerce.business.entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code", length = 9, unique = true)
    private String code;
    @Column(name = "Name", length = 50)
    private String name;
    @Column(name = "Description", length = 255)
    private String description;
    @Column(name = "Image")
    private String image;
    @Column(name = "Category")
    private String category;
    @Column(name = "Price")
    private BigDecimal price;
    @Column(name = "Quantity")
    private int quantity;
    @Column(name = "InternalReference")
    private String internalReference;
    @Column(name = "ShellId", length = 50)
    private String shellId;
    @Column(name = "InventoryStatus")
    private String inventoryStatus;
    @Column(name = "Rating")
    private double rating;
}


