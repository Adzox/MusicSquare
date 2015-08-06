package project.tddd80.keval992.liu.ida.se.navigationbase.models;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Model used for abstraction between actual model and how it is displayed.
 * Useful when displaying searches, where search results can consist of different models.
 * <p/>
 * To get actual type that this model is holding, use the isX() methods to validate and the getX()
 * method to fetch model, where X is either User, Page, Post or Comment. Can also use the getModel()
 * method to get the model.
 */
public class CardViewModel implements Serializable {

    public String title;
    public String subtitle;
    public String content;
    public String extra;
    private User savedUser;
    private Page savedPage;
    private User user;
    private Page page;
    private Comment comment;
    private Post post;
    private Search search;

    public CardViewModel(User user) {
        title = BaseUser.getReferenceTo(user.getUserId()).getName();
        subtitle = "";
        content = "";
        extra = user.getLocation();
        this.savedUser = user;
        this.user = user;
    }

    public CardViewModel(Page page) {
        title = page.getName();
        subtitle = page.getType();
        content = "";
        extra = page.getLocation();
        this.savedPage = page;
        this.page = page;
    }

    public CardViewModel(Post post) {
        User user = User.getReferenceTo(post.getUserId());
        title = BaseUser.getReferenceTo(user.getUserId()).getName();
        subtitle = "in " + Page.getReferenceTo(post.getOwnerId()).getName();
        content = post.getMessage();
        extra = post.getDateSent();
        this.savedUser = user;
        this.post = post;
    }

    public CardViewModel(Comment comment) {
        title = BaseUser.getReferenceTo(comment.getSenderId()).getName();
        subtitle = "in " + Page.getReferenceTo(comment.getPostId()).getName();
        content = comment.getMessage();
        extra = comment.getDateSent();
        this.comment = comment;
    }

    public CardViewModel(Search search) {
        title = search.getName();
        subtitle = search.getKind();
        content = search.getType();
        extra = search.getLocation();
        this.search = search;
    }

    public void setImageView(ImageView imageView) {
        if (savedUser != null) {
            savedUser.setProfileImage(imageView);
        } else if (savedPage != null) {
            savedPage.setProfileImage(imageView);
        }
    }

    public User getUser() {
        return user;
    }

    public boolean isUser() {
        return user != null;
    }

    public Page getPage() {
        return page;
    }

    public boolean isPage() {
        return page != null;
    }

    public Post getPost() {
        return post;
    }

    public boolean isPost() {
        return post != null;
    }

    public Comment getComment() {
        return comment;
    }

    public boolean isComment() {
        return comment != null;
    }

    /**
     * Returns the model this object is representing.
     * Use the isX() methods to validate, where X is either User, Page, Post or Comment.
     *
     * @return Either a User-, Page-, Post- or Comment-object.
     */
    public Serializable getModel() {
        if (user != null) {
            return user;
        } else if (page != null) {
            return page;
        } else if (post != null) {
            return post;
        } else if (comment != null) {
            return comment;
        } else if (search != null) {
            return search;
        } else {
            return null;
        }
    }
}
