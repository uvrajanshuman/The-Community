package com.example.thecommunityapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostResponseDto {
    private Long id;
    private String postName;
    private String url;
    private String description;
    private String userName;
    private String productName;
    private Long productId;
    private Integer voteCount;
    private Integer commentCount;
    private String duration;
    private boolean upVote;
    private boolean downVote;
    private Instant createdDate;

}
