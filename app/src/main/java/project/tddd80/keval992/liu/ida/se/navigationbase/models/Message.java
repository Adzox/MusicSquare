package project.tddd80.keval992.liu.ida.se.navigationbase.models;

import java.io.Serializable;

/**
 * Model class representing a message sent from a user.
 */
public class Message implements Serializable {

    private MessageRoom messageRoom;
    private User sender;
    private String message;
    private String dateSent;

    public Message(User user, MessageRoom messageRoom, String message, String dateSent) {
        sender = user;
        this.message = message;
        this.messageRoom = messageRoom;
        this.dateSent = dateSent;
    }

    public User getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public MessageRoom getMessageRoom() {
        return messageRoom;
    }

    public String getDateSent() {
        return dateSent;
    }

    @Override
    public String toString() {
        return "" + ": " + message;
    }
}
