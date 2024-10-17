package com.example.backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

import lombok.Data;
import lombok.Generated;

@Entity
@Data
public class ProductType {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;
    private String name;
    @OneToMany(
            mappedBy = "productType",
            cascade = {CascadeType.ALL}
    )
    @JsonIgnore
    @JsonManagedReference
    private List<Product> products;
}
