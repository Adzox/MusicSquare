package project.tddd80.keval992.liu.ida.se.navigationbase.models;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.network.ImageLoader;

/**
 * Model class representing an advanced user in the application.
 */
public class User implements Serializable {

    private BaseUser baseUser;
    private String location;
    private String information;
    private String profilePath;
    private List<String> genres;
    private transient Bitmap profileImage;
    private final ImageLoader.ImageLoaderListener imageLoaderListener = new ImageLoader.ImageLoaderListener() {
        @Override
        public void getResultBitmap(Bitmap results) {
            profileImage = results;
        }
    };

    public User(BaseUser baseUser, String location, String information, String profilePath, List<String> genres) {
        this.baseUser = baseUser;
        this.location = location;
        this.information = information;
        this.profilePath = profilePath;
        this.genres = genres;
    }

    public BaseUser getBaseUser() {
        return baseUser;
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

    public List<String> getGenres() {
        return genres;
    }

    public void setProfileImage(ImageView imageView) {
        if (profileImage != null) {
            imageView.setImageBitmap(profileImage);
        } else if (profilePath != null && profilePath != "") {
            ImageLoader imageLoader = new ImageLoader(imageView);
            imageLoader.setImageLoaderListener(imageLoaderListener);
            imageLoader.execute(profilePath);
        }
    }
}
