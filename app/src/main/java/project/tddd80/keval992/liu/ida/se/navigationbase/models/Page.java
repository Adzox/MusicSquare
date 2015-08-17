package project.tddd80.keval992.liu.ida.se.navigationbase.models;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.network.ImageLoader;

/**
 * Model class representing a page (group) in the application.
 */
public class Page implements Serializable {

    private final int id;
    private String type;
    private String name;
    private String location;
    private String information;
    private String profilePath;
    private List<String> genres;
    private List<User> members;
    private transient Bitmap profileImage;
    private final ImageLoader.ImageLoaderListener imageLoaderListener = new ImageLoader.ImageLoaderListener() {
        @Override
        public void getResultBitmap(Bitmap results) {
            profileImage = results;
        }
    };
    private boolean isUserMember;
    private boolean hasUserFavorited;

    public Page(int id, String type, String name, String location, String information, String profilePath,
                boolean isUserMember, boolean hasUserFavorited, List<String> genres, List<User> members) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.location = location;
        this.information = information;
        this.profilePath = profilePath;
        this.isUserMember = isUserMember;
        this.hasUserFavorited = hasUserFavorited;
        this.genres = genres;
        this.members = members;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public void setProfileImage(ImageView imageView) {
        if (profileImage != null) {
            imageView.setImageBitmap(profileImage);
        } else if (profilePath != null && profilePath != "null") {
            ImageLoader imageLoader = new ImageLoader(imageView);
            imageLoader.setImageLoaderListener(imageLoaderListener);
            imageLoader.execute(profilePath);
        }
    }

    public boolean isUserMember() {
        return isUserMember;
    }

    public void setUserMember(boolean isUserMember) {
        this.isUserMember = isUserMember;
    }

    public boolean isHasUserFavorited() {
        return hasUserFavorited;
    }

    public void setHasUserFavorited(boolean hasUserFavorited) {
        this.hasUserFavorited = hasUserFavorited;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<User> getMembers() {
        return members;
    }
}
