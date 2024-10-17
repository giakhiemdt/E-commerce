package com.example.backend.repository;

import com.example.backend.entity.Product;
import com.example.backend.entity.ProductDetail;
import com.example.backend.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {

    List<ProductType> findAll();

}
