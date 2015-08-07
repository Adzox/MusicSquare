package project.tddd80.keval992.liu.ida.se.navigationbase.models;

import java.io.Serializable;

/**
 * Model class representing a post in the application.
 */
public class Post implements Serializable {

    private final int id;
    private final String dateSent;
    private final String message;
    private final User sender;
    private final Page owner;
    private boolean liked;

    public Post(int id, String dateSent, User sender, Page owner, String message, boolean liked) {
        this.id = id;
        this.dateSent = dateSent;
        this.sender = sender;
        this.owner = owner;
        this.message = message;
        this.liked = liked;
    }

    public int getId() {
        return id;
    }

    public String getDateSent() {
        return dateSent;
    }

    public User getSender() {
        return sender;
    }

    public Page getOwner() {
        return owner;
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
