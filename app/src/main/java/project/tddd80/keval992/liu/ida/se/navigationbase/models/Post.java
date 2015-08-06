package project.tddd80.keval992.liu.ida.se.navigationbase.models;

import java.io.Serializable;

/**
 * Model class representing a post in the application.
 */
public class Post implements Serializable {

    private static final ModelCache<Post> posts = new ModelCache<>(ModelCache.CACHE_SIZE);

    private final int id;
    private final String dateSent;
    private final int userId;  // SENDER ID
    private final int ownerId; // PAGE ID
    private final String message;
    private User sender;
    private Page owner;
    private boolean liked;

    public Post(int id, String dateSent, int userId, int ownerId, String message, boolean liked) {
        this.id = id;
        this.dateSent = dateSent;
        this.userId = userId;
        this.ownerId = ownerId;
        this.message = message;
        this.liked = liked;
        posts.put(id, this);
    }

    public static Post getReferenceTo(int postId) {
        return posts.get(postId);
    }

    public int getId() {
        return id;
    }

    public String getDateSent() {
        return dateSent;
    }

    public int getUserId() {
        return userId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getMessage() {
        return message;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
