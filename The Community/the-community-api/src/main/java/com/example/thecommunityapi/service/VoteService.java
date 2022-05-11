package com.example.thecommunityapi.service;

import com.example.thecommunityapi.dto.VoteDto;
import com.example.thecommunityapi.model.Post;
import com.example.thecommunityapi.model.Vote;
import com.example.thecommunityapi.repository.PostRepository;
import com.example.thecommunityapi.repository.VoteRepository;
import com.example.thecommunityapi.security.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.thecommunityapi.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId()).get();

        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new RuntimeException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        Vote vote = new Vote();
        vote.setVoteType(voteDto.getVoteType());
        vote.setPost(post);
        vote.setUser(authService.getCurrentUser());
        return  vote;
    }
}
