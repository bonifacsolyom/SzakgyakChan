package org.github.bobobot.entities;

public class VoteNotification implements Notification {
    public static enum voteTypes {
        UPVOTE, DOWNVOTE
    }

    voteTypes voteType;
}
