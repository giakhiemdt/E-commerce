package com.example.backend.repository;

import com.example.backend.entity.Product;
import com.example.backend.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll();

    Optional<Product> findAllById(Long id);

    List<Product> findAllByName(String name);

    List<Product> findByNameContaining(String keyword);


}

