package project.tddd80.keval992.liu.ida.se.navigationbase.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;
import project.tddd80.keval992.liu.ida.se.navigationbase.adapters.CardViewHolder;
import project.tddd80.keval992.liu.ida.se.navigationbase.adapters.CommentRecyclerViewAdapter;
import project.tddd80.keval992.liu.ida.se.navigationbase.main.ResultsReceiver;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.CardViewModel;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Comment;
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
    private EditText comment;

    public CommentListFragment() {
    }

    public static final CommentListFragment newInstance(Post post) {
        CommentListFragment commentListFragment = new CommentListFragment();
        commentListFragment.setModelRecyclerViewAdapterClass(CommentRecyclerViewAdapter.class);
        commentListFragment.post = post;
        return commentListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchComments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        FrameLayout topContainer = (FrameLayout) view.findViewById(R.id.topContainer);
        initTopContainer(topContainer, inflater, container);
        FrameLayout bottomContainer = (FrameLayout) view.findViewById(R.id.bottomContainer);
        initBottomContainer(bottomContainer, inflater, container);
        return view;
    }

    private void initTopContainer(FrameLayout frameLayout, LayoutInflater layoutInflater, ViewGroup viewGroup) {
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        View v = layoutInflater.inflate(R.layout.layout_card, linearLayout);
        CardViewHolder cardViewHolder = new CardViewHolder(v);
        cardViewHolder.setCardViewModel(new CardViewModel(post));
        ViewGroup cv = (ViewGroup) ((ViewGroup) layoutInflater.inflate(R.layout.like_button, linearLayout)).findViewById(R.id.empty_card);
        initCardView((ViewGroup) cv.findViewById(R.id.like_container));
        frameLayout.addView(linearLayout);
    }

    private void initCardView(ViewGroup viewGroup) {
        final TextView textView = (TextView) viewGroup.findViewById(R.id.like_text);
        fetchLikeCount(textView);
        final Button button = (Button) viewGroup.findViewById(R.id.like_button);
        initLikeButton(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLike(button, textView);
            }
        });
    }

    private void initLikeButton(Button button) {
        if (post.isLiked()) {
            button.setText("Liked!");
            button.setTextColor(getResources().getColor(R.color.toogleColor));
        } else {
            button.setText("Like");
            button.setTextColor(getResources().getColor(R.color.normalColor));
        }
    }

    private void changeLike(final Button button, final TextView textView) {
        String routing = "";
        if (post.isLiked()) {
            routing = "delete";
        } else {
            routing = "new";
        }
        new HttpRequestTask(routing) {
            @Override
            protected void atPostExecute(JSONObject jsonObject) {
                toggleLike(button);
                fetchLikeCount(textView);
            }
        }.execute(JSONFactory.createLike(post.getId()));
    }

    private void fetchLikeCount(final TextView textView) {
        new HttpRequestTask("liked_count") {
            @Override
            protected void atPostExecute(JSONObject jsonObject) {
                textView.setText("Likes: " + JSONParser.parseLikedCount(jsonObject, getActivity()));
            }
        }.execute(JSONFactory.createLike(post.getId()));
    }

    private void toggleLike(Button button) {
        if (post.isLiked()) {
            post.setLiked(false);
            button.setText("Like");
            button.setTextColor(getResources().getColor(R.color.normalColor));
        } else {
            post.setLiked(true);
            button.setText("Liked!");
            button.setTextColor(getResources().getColor(R.color.toogleColor));
        }
    }

    private void initBottomContainer(FrameLayout frameLayout, LayoutInflater layoutInflater, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.layout_text_button, null);
        comment = (EditText) v.findViewById(R.id.text_field);
        Button button = (Button) v.findViewById(R.id.send_button);
        button.setText("Send");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment();
            }
        });
        frameLayout.addView(v);
    }

    private void sendComment() {
        String text = comment.getText().toString();
        new HttpRequestTask("new") {
            @Override
            protected void atPostExecute(JSONObject jsonObject) {
                if (JSONParser.wasSuccessful(jsonObject)) {
                    fetchComments();
                } else {
                    Toast.makeText(getActivity(), JSONParser.extractError(jsonObject), Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(JSONFactory.createNewComment(text, post.getId()));
    }

    private void fetchComments() {
        ResultsReceiver.newSearch();
        final List<Comment> comments = new ArrayList<>();
        new HttpRequestTask("comments", getActivity()) {

            @Override
            protected void atPostExecute(JSONObject jsonObject) {
                try {
                    JSONParser.parseJSONObject(jsonObject);
                    List<Serializable> serializables = ResultsReceiver.getResults(Comment.class);
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
