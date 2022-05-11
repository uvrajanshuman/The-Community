package com.example.thecommunityapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String text;
    private boolean isAnswer;
    private Instant createdDate;
    private Long postId;
    private String userName;
    public boolean getIsAnswer(){
        return this.isAnswer;
    }
}
