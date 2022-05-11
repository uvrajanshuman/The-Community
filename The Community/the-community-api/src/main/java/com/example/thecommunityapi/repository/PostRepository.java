package com.example.thecommunityapi.repository;

import com.example.thecommunityapi.model.Post;
import com.example.thecommunityapi.model.Product;
import com.example.thecommunityapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllByProduct(Product product);

    List<Post> findByUser(User user);

    //util queries
    List<Post> findByTitleContainingIgnoreCase(String title);



}
