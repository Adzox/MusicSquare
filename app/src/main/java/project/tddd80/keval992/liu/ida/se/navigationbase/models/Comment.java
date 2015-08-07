package project.tddd80.keval992.liu.ida.se.navigationbase.models;

import java.io.Serializable;

/**
 * Model class representing a comment in the application.
 */
public class Comment implements Serializable {

    private final int id;
    private final String dateSent;
    private final String message;
    private Post post;
    private User sender;

    public Comment(int id, Post page, String dateSent, User sender, String message) {
        this.id = id;
        this.post = page;
        this.dateSent = dateSent;
        this.sender = sender;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public String getDateSent() {
        return dateSent;
    }

    public User getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}
