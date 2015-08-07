package project.tddd80.keval992.liu.ida.se.navigationbase.models;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Model class representing a chat/message room.
 * <p/>
 * Created by kevin on 2015-08-03.
 */
public class MessageRoom implements Serializable {

    private static Map<Integer, MessageRoom> messageRooms = new LinkedHashMap<>();

    private final int id;
    private String title;
    private List<User> members;

    public MessageRoom(int id, String title, List<User> members) {
        this.id = id;
        this.title = title;
        this.members = members;
        messageRooms.put(id, this);
    }

    public static MessageRoom getMessageRoom(int id) {
        return messageRooms.get(id);
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
