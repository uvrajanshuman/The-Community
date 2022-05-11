package com.example.thecommunityapi.repository;

import com.example.thecommunityapi.model.Post;
import com.example.thecommunityapi.model.User;
import com.example.thecommunityapi.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote,Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
