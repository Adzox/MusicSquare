package project.tddd80.keval992.liu.ida.se.navigationbase.models;

import java.io.Serializable;

/**
 * Model class representing a comment in the application.
 */
public class Comment implements Serializable {

    private final static ModelCache<Comment> comments = new ModelCache<>(ModelCache.CACHE_SIZE);

    private final int id;
    private final int postId;
    private final String dateSent;
    private final int senderId; //user id
    private final String message;
    private Post post;
    private User sender;

    public Comment(int id, int pageId, String dateSent, int senderId, String message) {
        this.id = id;
        this.postId = pageId;
        this.dateSent = dateSent;
        this.senderId = senderId;
        this.message = message;
        comments.put(id, this);
    }

    public static Comment getReferenceTo(int id) {
        return comments.get(id);
    }

    public int getId() {
        return id;
    }

    public int getPostId() {
        return postId;
    }

    public String getDateSent() {
        return dateSent;
    }

    public int getSenderId() {
        return senderId;
    }

    public String getMessage() {
        return message;
    }
}
