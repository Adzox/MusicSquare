package project.tddd80.keval992.liu.ida.se.navigationbase.models;

import java.io.Serializable;

/**
 * Model class representing a base user in the application
 */
public class BaseUser implements Serializable {

    private final static ModelCache<BaseUser> baseUsers = new ModelCache<>(ModelCache.CACHE_SIZE);

    private final int id;
    private String username;
    private String name;

    public BaseUser(int id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
        baseUsers.put(id, this);
    }

    public static BaseUser getReferenceTo(int userId) {
        return baseUsers.get(userId);
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
