package project.tddd80.keval992.liu.ida.se.navigationbase.fragments;


import android.os.Bundle;
import android.view.View;

import project.tddd80.keval992.liu.ida.se.navigationbase.adapters.PostRecyclerViewAdapter;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Page;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Post;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class PostListFragment2 extends ModelListFragment<Post> {

    public static final int MODE_GLOBAL_NEWS = 0;
    public static final int MODE_FAVORITED_NEWS = 1;
    public static final int MODE_PAGE_POSTS = 2;
    private int mode;
    private Page page;

    public static final PostListFragment2 newInstanceGlobal() {
        PostListFragment2 postListFragment2 = new PostListFragment2();
        postListFragment2.setModelRecyclerViewAdapterClass(PostRecyclerViewAdapter.class);
        postListFragment2.mode = MODE_GLOBAL_NEWS;
        return postListFragment2;
    }

    public static final PostListFragment2 newInstanceFavorites() {
        PostListFragment2 postListFragment2 = new PostListFragment2();
        postListFragment2.setModelRecyclerViewAdapterClass(PostRecyclerViewAdapter.class);
        postListFragment2.mode = MODE_FAVORITED_NEWS;
        return postListFragment2;
    }

    public static final PostListFragment2 newInstance(Page page) {
        PostListFragment2 postListFragment2 = new PostListFragment2();
        postListFragment2.setModelRecyclerViewAdapterClass(PostRecyclerViewAdapter.class);
        postListFragment2.mode = MODE_PAGE_POSTS;
        postListFragment2.page = page;
        return postListFragment2;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getModels();
    }

    protected void getModels() {
        switch (mode) {
            default:
            case MODE_GLOBAL_NEWS:
            case MODE_FAVORITED_NEWS:
            case MODE_PAGE_POSTS:
        }
    }

    @Override
    protected void itemClicked(View view, int position) {
        // Go to post-comment fragment.
    }
}
