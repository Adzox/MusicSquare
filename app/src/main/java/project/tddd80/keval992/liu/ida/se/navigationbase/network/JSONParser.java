package project.tddd80.keval992.liu.ida.se.navigationbase.network;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.main.LoginInfo;
import project.tddd80.keval992.liu.ida.se.navigationbase.main.ResultsReceiver;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.BaseUser;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Comment;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Message;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.MessageRoom;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Page;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Post;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Search;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.User;

/**
 * Util class for parsing JSONObject from the server to respective data.
 * <p/>
 * Created by kevin on 2015-05-08.
 */
public final class JSONParser {

    private static final String LOG_TAG = JSONParser.class.getSimpleName();

    private JSONParser() {
    }

    public static boolean wasSuccessful(JSONObject jsonObject) {
        return jsonObject != null && !jsonObject.has("error");
    }

    public static String extractError(JSONObject jsonObject) {
        try {
            if (jsonObject != null) {
                if (jsonObject.has("error")) {
                    return jsonObject.getString("error");
                }
            } else {
                return "Could not send data to server! Try again!";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int extractLoginData(FragmentActivity activity, JSONObject jsonObject) {
        if (jsonObject != null) {
            try {
                if (jsonObject.has("error")) {
                    String error = jsonObject.getString("error");
                    Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();
                } else if (jsonObject.has("result")) {
                    boolean advanced = false;
                    if (jsonObject.has("other")) {
                        advanced = jsonObject.getBoolean("other");
                    }
                    LoginInfo.saveLogin(jsonObject.getInt("result"), advanced);
                    return jsonObject.getInt("result");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public static void parseJSONArray(JSONArray jsonObject) {
        try {

            for (int n = 0; n < jsonObject.length(); n++) {
                Log.i(LOG_TAG, "PARSING JSON ARRAY no. " + n);
                parseJSONObject(jsonObject.getJSONObject(n));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void parseJSONObject(JSONObject jsonObject) throws JSONException {
        for (int i = 0; i < jsonObject.names().length(); i++) {
            Serializable ser = null;
            switch (jsonObject.names().getString(i)) {
                case "result":
                    if (jsonObject.optJSONObject("result") != null) {
                        Log.i(LOG_TAG, "Parsing results as JSONObject");
                        parseJSONObject(jsonObject.getJSONObject("result"));
                    } else if (jsonObject.optJSONArray("result") != null) {
                        Log.i(LOG_TAG, "Parsing results as JSONArray");
                        parseJSONArray(jsonObject.getJSONArray("result"));
                    }
                    break;
                case "base_user":
                    Log.i(LOG_TAG, "Parsing base_user");
                    ser = (Serializable) parseBaseUser(jsonObject.getJSONObject("base_user"));
                    break;
                case "user":
                    Log.i(LOG_TAG, "Parsing user");
                    ser = (Serializable) parseUser(jsonObject.getJSONArray("user"));
                    break;
                case "page":
                    Log.i(LOG_TAG, "Parsing page");
                    ser = (Serializable) parsePage(jsonObject.getJSONArray("page"));
                    break;
                case "post":
                    Log.i(LOG_TAG, "Parsing post");
                    ser = (Serializable) parsePost(jsonObject.getJSONArray("post"));
                    break;
                case "comment":
                    Log.i(LOG_TAG, "Parsing comment");
                    ser = (Serializable) parseComment(jsonObject.getJSONArray("comment"));
                    break;
                case "message":
                    Log.i(LOG_TAG, "Parsing message");
                    ser = (Serializable) parseMessage(jsonObject.getJSONArray("message"));
                    break;
                case "search":
                    Log.i(LOG_TAG, "Parsing search");
                    ser = (Serializable) parseSearch(jsonObject.getJSONObject("search"));
                    break;
                case "messageRoom":
                    Log.i(LOG_TAG, "Parsing messageRoom");
                    ser = (Serializable) parseMessageRoom(jsonObject.getJSONArray("messageRoom"));
                    break;
            }
            if (ser != null) {
                ResultsReceiver.addResults(ser.getClass(), ser);
            }
        }
    }

    private static List<String> parseGenre(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("genres");
        List<String> strings = new LinkedList<>();
        for (int n = 0; n < jsonArray.length(); n++) {
            strings.add(jsonArray.getString(n));
        }
        return strings;
    }

    private static BaseUser parseBaseUser(JSONObject baseUser) throws JSONException {
        baseUser = baseUser.getJSONObject("base_user");
        return new BaseUser(baseUser.getInt("id"), baseUser.getString("username"), baseUser.getString("name"));
    }

    private static User parseUser(JSONArray user) throws JSONException {
        JSONObject u = user.getJSONObject(0).getJSONObject("user");
        return new User(parseBaseUser(user.getJSONObject(2)), u.getString("location"), u.getString("information"), u.getString("profile_path"),
                parseGenre(user.getJSONObject(1)));
    }

    private static Page parsePage(JSONArray page) throws JSONException {
        JSONObject p = page.getJSONObject(0).getJSONObject("page");
        List<User> users = parseMembers(page.getJSONObject(2).getJSONArray("members"));
        if (page.length() < 4) {
            return new Page(p.getInt("id"), p.getString("type"), p.getString("name"), p.getString("location"),
                    p.getString("information"), p.getString("profilePath"), false, false,
                    parseGenre(page.getJSONObject(1)), users);
        } else {
            JSONObject pi = page.getJSONObject(3).getJSONObject("page info");
            return new Page(p.getInt("id"), p.getString("type"), p.getString("name"), p.getString("location"),
                    p.getString("information"), p.getString("profilePath"), pi.getBoolean("member"),
                    pi.getBoolean("favorite"), parseGenre(page.getJSONObject(1)), users);
        }
    }

    // IF PAGE IN MEMORY WHILE LOOKING AT POSTS, DON'T SEND IT! SAVE IT AND ACCESS WITH ID!
    private static Post parsePost(JSONArray post) throws JSONException {
        JSONObject p = post.getJSONObject(0);
        if (post.length() < 4) {
            return new Post(p.getInt("id"), p.getString("dateSent"), parseUser(post.getJSONArray(1)),
                    parsePage(post.getJSONArray(2)), p.getString("message"), false);
        } else {
            JSONObject l = post.getJSONObject(3);
            return new Post(p.getInt("id"), p.getString("dateSent"), parseUser(post.getJSONArray(1)),
                    parsePage(post.getJSONArray(2)), p.getString("message"), l.getBoolean("like"));
        }
    }

    private static Comment parseComment(JSONArray comment) throws JSONException {
        JSONObject c = comment.getJSONObject(0);
        return new Comment(c.getInt("id"), Post.getPost(c.getInt("postId")), c.getString("dateSent"),
                parseUser(comment.getJSONArray(1)), c.getString("message"));
    }

    private static Message parseMessage(JSONArray message) throws JSONException {
        JSONObject m = message.getJSONObject(0);
        MessageRoom mr = MessageRoom.getMessageRoom(m.getInt("roomId"));
        return new Message(parseUser(message.getJSONArray(1)), mr, m.getString("message"), m.getString("dateSent"));
    }

    private static Search parseSearch(JSONObject search) throws JSONException {
        return new Search(search.getInt("id"), search.getString("kind"), search.getString("name"),
                search.getString("type"), search.getString("location"), search.getString("profilePath"));
    }

    private static MessageRoom parseMessageRoom(JSONArray messageRoom) throws JSONException {
        JSONObject m = messageRoom.getJSONObject(0);
        List<User> users = parseMembers(messageRoom.getJSONArray(1));
        return new MessageRoom(m.getInt("id"), m.getString("title"), users);
    }

    private static List<User> parseMembers(JSONArray users) throws JSONException {
        List<User> members = new LinkedList<>();
        for (int n = 0; n < users.length(); n++) {
            members.add(parseUser(users.getJSONObject(n).getJSONArray("user")));
        }
        return members;
    }
}
