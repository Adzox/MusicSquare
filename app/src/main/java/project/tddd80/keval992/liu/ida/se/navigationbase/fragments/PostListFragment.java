package project.tddd80.keval992.liu.ida.se.navigationbase.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;
import project.tddd80.keval992.liu.ida.se.navigationbase.adapters.PostRecyclerViewAdapter;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Post;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostListFragment extends Fragment {

    private RecyclerView recyclerView;
    private int pagePostId = -1;
    private boolean favorites = false;
    private PostRecyclerViewAdapter postRecyclerViewAdapter;

    public PostListFragment() {
    }

    public static final PostListFragment loadPagePosts(int pageId) {
        PostListFragment postListFragment = new PostListFragment();
        postListFragment.pagePostId = pageId;
        return postListFragment;
    }

    public static final PostListFragment loadFavorites() {
        PostListFragment postListFragment = new PostListFragment();
        postListFragment.favorites = true;
        return postListFragment;
    }

    public static final PostListFragment loadGlobals() {
        return new PostListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.post_list_list);
        if (pagePostId > -1) {
            // LOAD PAGES POSTS
        } else if (favorites) {
            // LOAD FAVORITED RECENT POSTS
        } else {
            // LOAD GLOBAL RECENT POSTS
        }
        setUpRecyclerView(null);

        return view;
    }

    private void setUpRecyclerView(List<Post> posts) {
        postRecyclerViewAdapter = new PostRecyclerViewAdapter(posts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(postRecyclerViewAdapter);
    }
}
