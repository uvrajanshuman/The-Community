package com.example.thecommunityapi.service;

import com.example.thecommunityapi.dto.ProductDto;
import com.example.thecommunityapi.model.Product;
import com.example.thecommunityapi.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;

    public ProductDto save(ProductDto productDto){
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCreatedDate(Instant.now());
        product = productRepository.save(product);

        return new ProductDto(product.getProductId(), product.getName(), product.getDescription(),product.getPosts()!=null?product.getPosts().size():0);

    }

    public List<ProductDto> getAll(){
        List<ProductDto> list = new ArrayList<>();
        for(Product p: productRepository.findAll()){
            ProductDto temp = new ProductDto();
            temp.setId(p.getProductId());
            temp.setName(p.getName());
            temp.setDescription(p.getDescription());
            temp.setNoOfPosts(p.getPosts()!=null?p.getPosts().size():0);
            list.add(temp);
        }
        return list;
    }

    public ProductDto getProduct(Long id){
        Product product = productRepository.findById(id).get();
        return new ProductDto(product.getProductId(), product.getName(),
                product.getDescription(),product.getPosts()!=null?product.getPosts().size():0);
    }
}
