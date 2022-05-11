package com.example.thecommunityapi.service;

import com.example.thecommunityapi.dto.PostRequestDto;
import com.example.thecommunityapi.dto.PostResponseDto;
import com.example.thecommunityapi.model.*;
import com.example.thecommunityapi.repository.*;
import com.example.thecommunityapi.security.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.thecommunityapi.model.VoteType.DOWNVOTE;
import static com.example.thecommunityapi.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class PostService {
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final AuthService authService;
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final VoteRepository voteRepository;

    public void save(PostRequestDto postRequest){
        Product product = productRepository.findById(postRequest.getProductId()).get();
        User user = authService.getCurrentUser();
        Post post = new Post();
        post.setProduct(product);
        post.setUser(user);
        post.setUrl(postRequest.getUrl());
        post.setVoteCount(0);
        post.setTitle(postRequest.getPostTitle());
        post.setDescription(postRequest.getDescription());
        post.setCreatedDate(Instant.now());
        postRepository.save(post);
    }

    public PostResponseDto getPost(Long id){
        Post post = postRepository.findById(id).get();
        PostResponseDto response = new PostResponseDto();
        response.setId(post.getPostId());
        response.setPostName(post.getTitle());
        response.setDescription(post.getDescription());
        response.setUrl(post.getUrl());
        response.setVoteCount(post.getVoteCount());
        response.setCommentCount(commentCount(post));
        response.setProductId(post.getProduct().getProductId());
        response.setProductName(post.getProduct().getName());
        response.setUserName(post.getUser().getUsername());
        response.setCreatedDate(post.getCreatedDate());
        response.setUpVote(isPostUpVoted(post));
        response.setDownVote(isPostDownVoted(post));
        return response;
    }

    public List<PostResponseDto> getAllPosts(){
        List<PostResponseDto> list = new ArrayList<>();
        for(Post post: postRepository.findAll()){
            PostResponseDto response = new PostResponseDto();
            response.setId(post.getPostId());
            response.setPostName(post.getTitle());
            response.setDescription(post.getDescription());
            response.setUrl(post.getUrl());
            response.setProductId(post.getProduct().getProductId());
            response.setProductName(post.getProduct().getName());
            response.setUserName(post.getUser().getUsername());
            response.setVoteCount(post.getVoteCount());
            response.setCommentCount(commentCount(post));
            response.setUpVote(isPostUpVoted(post));
            response.setDownVote(isPostDownVoted(post));
            response.setCreatedDate(post.getCreatedDate());
            list.add(response);
        }
        return list;
    }

    public List<PostResponseDto> getPostsByProduct(Long productId){
        Product product = productRepository.findById(productId).get();
        List<Post> posts = postRepository.findAllByProduct(product);
        List<PostResponseDto> list = new ArrayList<>();
        for(Post post: posts){
            PostResponseDto response = new PostResponseDto();
            response.setId(post.getPostId());
            response.setPostName(post.getTitle());
            response.setDescription(post.getDescription());
            response.setUrl(post.getUrl());
            response.setProductName(post.getProduct().getName());
            response.setProductId(post.getProduct().getProductId());
            response.setUserName(post.getUser().getUsername());
            response.setVoteCount(post.getVoteCount());
            response.setCommentCount(commentCount(post));
            response.setUpVote(isPostUpVoted(post));
            response.setDownVote(isPostDownVoted(post));
            response.setCreatedDate(post.getCreatedDate());
            list.add(response);
        }
        return list;
    }

    public List<PostResponseDto> getPostsByUsername(String username){
        User user = userRepository.findByUsername(username);
        List<PostResponseDto> list = new ArrayList<>();
        for(Post post : postRepository.findByUser(user)){
            PostResponseDto response = new PostResponseDto();
        response.setId(post.getPostId());
        response.setPostName(post.getTitle());
        response.setDescription(post.getDescription());
        response.setUrl(post.getUrl());
        response.setProductId(post.getProduct().getProductId());
        response.setProductName(post.getProduct().getName());
        response.setUserName(post.getUser().getUsername());
        response.setVoteCount(post.getVoteCount());
        response.setCommentCount(commentCount(post));
        response.setUpVote(isPostUpVoted(post));
        response.setDownVote(isPostDownVoted(post));
        response.setCreatedDate(post.getCreatedDate());
        list.add(response);
    }
        return list;
    }

    private Integer commentCount(Post post){
        return commentRepository.findByPost(post).size();
    }

//    public Object deletePost(Long id) {
//        Post post = postRepository.findById(id).get();
//        PostResponseDto response = new PostResponseDto();
//        response.setId(post.getPostId());
//        response.setPostName(post.getTitle());
//        response.setDescription(post.getDescription());
//        response.setUrl(post.getUrl());
//        response.setProductId(post.getProduct().getProductId());
//        response.setProductName(post.getProduct().getName());
//        response.setUserName(post.getUser().getUsername());
//        return response;
//    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                    authService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }
}
