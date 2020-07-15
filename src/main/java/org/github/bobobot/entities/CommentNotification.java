package org.github.bobobot.entities;

import java.util.Objects;

public class CommentNotification extends Notification {
    String replyContent;

    public CommentNotification(int ID, String replyContent, boolean read) {
        this.ID = ID;
        this.replyContent = replyContent;
        this.read = read;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentNotification that = (CommentNotification) o;
        return replyContent.equals(that.replyContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(replyContent);
    }
}
