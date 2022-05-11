package com.example.thecommunityapi.service;

import com.example.thecommunityapi.dto.PostResponseDto;
import com.example.thecommunityapi.dto.ProductDto;
import com.example.thecommunityapi.dto.SearchDto;
import com.example.thecommunityapi.dto.UtilDto;
import com.example.thecommunityapi.model.*;
import com.example.thecommunityapi.repository.*;
import com.example.thecommunityapi.security.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.thecommunityapi.model.VoteType.DOWNVOTE;
import static com.example.thecommunityapi.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class UtilService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final AuthService authService;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final VoteRepository voteRepository;

    public UtilDto getAllStats(){
        UtilDto utilDto = new UtilDto();
        utilDto.setQueriesCount(postRepository.count());
        utilDto.setAnswerCount(postRepository.count());
        utilDto.setVerifiedAnswersCount(commentRepository.countByIsAnswer(true));
        utilDto.setUserCount(userRepository.count());
        return utilDto;
    }

    public List<PostResponseDto> getPostsByProduct(String productName){
        Product product = productRepository.findByNameContainingIgnoreCase(productName).get(0);
        List<PostResponseDto> list = new ArrayList<>();
        for(Post post: postRepository.findAllByProduct(product)){
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

    public List<PostResponseDto> getPostsByUserName(String userName){
        User user =  userRepository.findByUsernameContainingIgnoreCase(userName);
        List<PostResponseDto> list = new ArrayList<>();
        for(Post post: postRepository.findByUser(user)){
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


    public List<PostResponseDto> getPostsByPostTitle(String productCode){
        List<PostResponseDto> list = new ArrayList<>();
            for(Post post: postRepository.findByTitleContainingIgnoreCase(productCode)){
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

    public List<PostResponseDto> search(SearchDto searchDto) {
        String text = searchDto.getSearchText().trim();
        List<PostResponseDto> list = new ArrayList<>();

        list.addAll(getPostsByProduct(text));
        if(!list.isEmpty()){
            return list;
        }
        list.addAll(getPostsByPostTitle(text));
        if(!list.isEmpty()){
            return list;
        }
        list.addAll(getPostsByUserName(text));

        return  list;
    }
}
