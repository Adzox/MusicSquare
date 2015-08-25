package project.tddd80.keval992.liu.ida.se.navigationbase.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;
import project.tddd80.keval992.liu.ida.se.navigationbase.adapters.PostRecyclerViewAdapter;
import project.tddd80.keval992.liu.ida.se.navigationbase.main.LoginInfo;
import project.tddd80.keval992.liu.ida.se.navigationbase.main.ResultsReceiver;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Page;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Post;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.HttpRequestTask;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.JSONFactory;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.JSONParser;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class PostListFragment extends ModelListFragment<Post> {

    public static final int MODE_GLOBAL_NEWS = 0;
    public static final int MODE_FAVORITED_NEWS = 1;
    public static final int MODE_PAGE_POSTS = 2;
    private static final int NUMBER_OF_POSTS = 30;
    private int mode;
    private Page page;

    public static final PostListFragment newInstanceGlobal() {
        PostListFragment postListFragment = new PostListFragment();
        postListFragment.setModelRecyclerViewAdapterClass(PostRecyclerViewAdapter.class);
        postListFragment.mode = MODE_GLOBAL_NEWS;
        return postListFragment;
    }

    public static final PostListFragment newInstanceFavorites() {
        PostListFragment postListFragment = new PostListFragment();
        postListFragment.setModelRecyclerViewAdapterClass(PostRecyclerViewAdapter.class);
        postListFragment.mode = MODE_FAVORITED_NEWS;
        return postListFragment;
    }

    public static final PostListFragment newInstance(Page page) {
        PostListFragment postListFragment = new PostListFragment();
        postListFragment.setModelRecyclerViewAdapterClass(PostRecyclerViewAdapter.class);
        postListFragment.mode = MODE_PAGE_POSTS;
        postListFragment.page = page;
        return postListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getModels();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (mode == MODE_PAGE_POSTS && LoginInfo.hasLoggedIn() && page != null && page.isUserMember()) {
            FrameLayout topContainer = (FrameLayout) view.findViewById(R.id.topContainer);
            initTopContainer(topContainer, inflater, container);
        }
        return view;
    }

    private void initTopContainer(FrameLayout frameLayout, LayoutInflater inflater, ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.layout_text_button, null);
        final EditText editText = (EditText) v.findViewById(R.id.text_field);
        editText.setHint("Write a new post...");
        Button button = (Button) v.findViewById(R.id.send_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPost(editText.getText().toString());
            }
        });
        frameLayout.addView(v);
    }

    private void createPost(String message) {
        new HttpRequestTask("new") {
            @Override
            protected void atPostExecute(JSONObject jsonObject) {
                if (JSONParser.wasSuccessful(jsonObject)) {
                    getModels();
                } else {
                    Toast.makeText(getActivity(), JSONParser.extractError(jsonObject), Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(JSONFactory.createNewPost(message, page.getId()));
    }

    protected void getModels() {
        switch (mode) {
            default:
            case MODE_GLOBAL_NEWS:
                new HttpRequestTask("news") {

                    @Override
                    protected void atPostExecute(JSONObject jsonObject) {
                        onItemsReceived(jsonObject);
                    }
                }.execute(JSONFactory.createGlobalNewsData(NUMBER_OF_POSTS));
                break;
            case MODE_FAVORITED_NEWS:
                new HttpRequestTask("news") {

                    @Override
                    protected void atPostExecute(JSONObject jsonObject) {
                        onItemsReceived(jsonObject);
                    }
                }.execute(JSONFactory.createFavoriteNewsData(NUMBER_OF_POSTS));
                break;
            case MODE_PAGE_POSTS:
                new HttpRequestTask("posts") {

                    @Override
                    protected void atPostExecute(JSONObject jsonObject) {
                        onItemsReceived(jsonObject);
                    }
                }.execute(JSONFactory.createPagePostsData(page.getId()));
                break;
        }
    }

    private void onItemsReceived(JSONObject jsonObject) {
        ResultsReceiver.newSearch();
        try {
            JSONParser.parseJSONObject(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<Post> posts = new ArrayList<>();
        List<Serializable> serializables = ResultsReceiver.getResults(Post.class);
        if (serializables != null && !serializables.isEmpty()) {
            for (Serializable serializable : serializables) {
                posts.add((Post) serializable);
            }
        }
        setItems(posts);
        doneFetching();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        getModels();
    }
}
