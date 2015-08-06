package project.tddd80.keval992.liu.ida.se.navigationbase.models;

import java.io.Serializable;

/**
 * Model class representing a message sent from a user.
 */
public class Message implements Serializable {

    private String senderName;
    private int roomId;
    private MessageRoom messageRoom;
    private User sender;
    private String message;
    private String dateSent;

    public Message(int userId, int roomId, String message, String dateSent) {
        sender = User.getReferenceTo(userId);
        senderName = BaseUser.getReferenceTo(userId).getName();
        this.message = message;
        this.roomId = roomId;
        this.dateSent = dateSent;
    }

    public User getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderName() {
        return senderName;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getDateSent() {
        return dateSent;
    }

    @Override
    public String toString() {
        return senderName + ": " + message;
    }
}
