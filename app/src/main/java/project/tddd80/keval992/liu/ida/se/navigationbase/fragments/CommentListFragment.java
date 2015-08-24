package project.tddd80.keval992.liu.ida.se.navigationbase.fragments;

import android.os.Bundle;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.main.ResultsReceiver;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Comment;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Page;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Post;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.HttpRequestTask;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.JSONFactory;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.JSONParser;

/**
 * Simple fragment to display the post-comment fragment.
 *
 * Created by kevin on 2015-08-24.
 */
public class CommentListFragment extends ModelListFragment<Comment> {

    private Post post;

    public CommentListFragment() {
    }

    public static final CommentListFragment newInstance(Post post) {
        CommentListFragment commentListFragment = new CommentListFragment();
        commentListFragment.post = post;
        return commentListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadModels();
    }

    public final void loadModels() {
        ResultsReceiver.newSearch();
        fetchComments();
    }

    private void fetchComments() {
        final List<Comment> comments = new ArrayList<>();
        new HttpRequestTask("comments", getActivity()) {

            @Override
            protected void atPostExecute(JSONObject jsonObject) {
                try {
                    JSONParser.parseJSONObject(jsonObject);
                    List<Serializable> serializables = ResultsReceiver.getResults(Page.class);
                    if (serializables != null && !serializables.isEmpty()) {
                        for (Serializable serializable : serializables) {
                            comments.add((Comment) serializable);
                        }
                    }
                    setItems(comments);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(JSONFactory.createPostIdData(post.getId()));
    }

    @Override
    protected void itemClicked(View view, int position) {
        // GO TO USER PROFILE
    }
}
