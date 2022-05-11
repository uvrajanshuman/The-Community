package com.example.thecommunityapi.repository;

import com.example.thecommunityapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findByUsernameContainingIgnoreCase(String username);
}
