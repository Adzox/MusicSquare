package project.tddd80.keval992.liu.ida.se.navigationbase.fragments;


import java.util.LinkedList;
import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.adapters.PostRecyclerViewAdapter;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Post;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class PostListFragment2 extends ModelListFragment<Post> {

    public static final int MODE_GLOBAL_NEWS = 0;
    public static final int MODE_FAVORITED_NEWS = 1;
    public static final int MODE_PAGE_POSTS = 2;
    private int mode;
    private int pageId;

    public static final PostListFragment2 newInstance(int mode) {
        return newInstance(mode, -1);
    }

    public static final PostListFragment2 newInstance(int mode, int pageId) {
        PostListFragment2 postListFragment2 = new PostListFragment2();
        postListFragment2.setModelRecyclerViewAdapterClass(PostRecyclerViewAdapter.class);
        postListFragment2.mode = mode;
        postListFragment2.pageId = pageId;
        return postListFragment2;
    }

    @Override
    protected List<Post> getModels() {
        List<Post> posts = new LinkedList<>();
        switch (mode) {
            default:
            case MODE_GLOBAL_NEWS:
                return posts;
            case MODE_FAVORITED_NEWS:
                return posts;
            case MODE_PAGE_POSTS:
                return posts;
        }
    }
}
