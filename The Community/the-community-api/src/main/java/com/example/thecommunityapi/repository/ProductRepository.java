package com.example.thecommunityapi.repository;

import com.example.thecommunityapi.model.Post;
import com.example.thecommunityapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByName(String name);

    List<Product> findByNameContainingIgnoreCase(String title);
}
