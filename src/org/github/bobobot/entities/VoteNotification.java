package org.github.bobobot.entities;

public class VoteNotification implements Notification {
    public static enum VoteType {
        UPVOTE, DOWNVOTE
    }

    VoteType voteType;
}
