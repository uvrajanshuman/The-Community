package com.example.thecommunityapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {
    private Long postId;
    private String productName;
    private Long productId;
    private String postTitle;
    private String url;
    private String description;
}
