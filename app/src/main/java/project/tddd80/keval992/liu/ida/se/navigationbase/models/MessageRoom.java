package project.tddd80.keval992.liu.ida.se.navigationbase.models;

import java.io.Serializable;
import java.util.List;

/**
 * Model class representing a chat/message room.
 * <p/>
 * Created by kevin on 2015-08-03.
 */
public class MessageRoom implements Serializable {

    private final int id;
    private String title;
    private List<User> members;

    public MessageRoom(int id, String title, List<User> members) {
        this.id = id;
        this.title = title;
        this.members = members;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<User> getMembers() {
        return members;
    }
}
