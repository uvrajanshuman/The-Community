package com.example.thecommunityapi.service;

import com.example.thecommunityapi.dto.CommentDto;
import com.example.thecommunityapi.model.Comment;
import com.example.thecommunityapi.model.Post;
import com.example.thecommunityapi.model.User;
import com.example.thecommunityapi.repository.CommentRepository;
import com.example.thecommunityapi.repository.PostRepository;
import com.example.thecommunityapi.repository.UserRepository;
import com.example.thecommunityapi.security.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    AuthService authService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;

    public void save(CommentDto commentDto) {
        System.out.println(commentDto);
        Post post = postRepository.findById(commentDto.getPostId()).get();
        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setPost(post);
        comment.setIsAnswer(commentDto.getIsAnswer());
        comment.setUser(authService.getCurrentUser());
        comment.setCreatedDate(Instant.now());
        commentRepository.save(comment);
    }

    public List<CommentDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).get();
        System.out.println(post);
        List<CommentDto> list = new ArrayList<>();
        for(Comment comment: commentRepository.findByPost(post)){
            CommentDto temp = new CommentDto();
            temp.setId(comment.getCommentId());
            temp.setText(comment.getText());
            temp.setPostId(comment.getPost().getPostId());
            temp.setUserName(comment.getUser().getUsername());
            temp.setCreatedDate(comment.getCreatedDate());
            temp.setAnswer(comment.getIsAnswer());
            list.add(temp);
        }
        return list;
    }

    public List<CommentDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName);
        System.out.println(user);
        List<CommentDto> list = new ArrayList<>();
        for(Comment comment: commentRepository.findAllByUser(user)){
            System.out.println(comment);
            CommentDto temp = new CommentDto();
            temp.setId(comment.getCommentId());
            temp.setText(comment.getText());
            temp.setPostId(comment.getPost().getPostId());
            temp.setUserName(comment.getUser().getUsername());
            temp.setCreatedDate(comment.getCreatedDate());
            temp.setAnswer(comment.getIsAnswer());
            list.add(temp);
        }
        return list;
    }

    public String markAsAnswer(Long commentId){
        Comment comment = commentRepository.findById(commentId).get();
        comment.setIsAnswer(true);
        commentRepository.save(comment);
        return "success";
    }

//    public boolean containsSwearWords(String comment) {
//        if (comment.contains("shit")) {
//            throw new SpringRedditException("Comments contains unacceptable language");
//        }
//        return false;
//    }
}
