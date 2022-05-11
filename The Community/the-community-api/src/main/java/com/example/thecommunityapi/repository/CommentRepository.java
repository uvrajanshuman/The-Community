package com.example.thecommunityapi.repository;

import com.example.thecommunityapi.model.Comment;
import com.example.thecommunityapi.model.Post;
import com.example.thecommunityapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);

    Long countByIsAnswer(Boolean isAnswer);
}
