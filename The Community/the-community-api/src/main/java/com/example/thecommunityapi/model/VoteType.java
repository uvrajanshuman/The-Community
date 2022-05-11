package com.example.thecommunityapi.model;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(1);
    private int voteType;

    //1--upvote
    //0--downvote
    VoteType(int voteType) {
        this.voteType = voteType;
    }

    public int getVoteType() {
        return voteType;
    }
}
