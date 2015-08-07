package project.tddd80.keval992.liu.ida.se.navigationbase.models;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.Serializable;

import project.tddd80.keval992.liu.ida.se.navigationbase.network.ImageLoader;

/**
 * Model class for representing the data returned when searching.
 * <p/>
 * Created by kevin on 2015-08-04.
 */
public class Search implements Serializable {

    private int id;
    private String kind;
    private String name;
    private String type;
    private String location;
    private String profilePath;
    private transient Bitmap profileImage;
    private final ImageLoader.ImageLoaderListener imageLoaderListener = new ImageLoader.ImageLoaderListener() {
        @Override
        public void getResultBitmap(Bitmap results) {
            profileImage = results;
        }
    };

    public Search(int id, String kind, String name, String type, String location, String profilePath) {
        this.id = id;
        this.kind = kind;
        this.name = name;
        this.type = type;
        this.location = location;
        this.profilePath = profilePath;
    }

    public int getId() {
        return id;
    }

    public String getKind() {
        return kind;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfileImage(ImageView imageView) {
        if (profileImage != null) {
            imageView.setImageBitmap(profileImage);
        } else if (profilePath != null) {
            ImageLoader imageLoader = new ImageLoader(imageView);
            imageLoader.setImageLoaderListener(imageLoaderListener);
            imageLoader.execute(profilePath);
        }
    }
}
