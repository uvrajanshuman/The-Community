package com.example.thecommunityapi.controller;


import com.example.thecommunityapi.dto.ProductDto;
import com.example.thecommunityapi.security.AuthService;
import com.example.thecommunityapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.save(productDto));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllSubreddits() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getSubreddit(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getProduct(id));
    }
}
