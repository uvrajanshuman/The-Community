package com.example.thecommunityapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilDto {
    private Long userCount;
    private Long queriesCount;
    private Long verifiedAnswersCount;
    private Long answerCount;
}
