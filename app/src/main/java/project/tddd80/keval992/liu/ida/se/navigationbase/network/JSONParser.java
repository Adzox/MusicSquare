package project.tddd80.keval992.liu.ida.se.navigationbase.network;

import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                parseJSONObject(jsonObject.getJSONObject(n));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void parseJSONObject(JSONObject jsonObject) throws JSONException {
        for (int i = 0; i < jsonObject.names().length(); i++) {
            switch (jsonObject.names().getString(i)) {
                case "result":
                    if (jsonObject.getJSONObject("result") != null) {
                        parseJSONObject(jsonObject.getJSONObject("result"));
                    } else if (jsonObject.getJSONArray("result") != null) {
                        parseJSONArray(jsonObject.getJSONArray("result"));
                    }
                    break;
                case "base_user":
                    parseBaseUser(jsonObject.getJSONObject("base_user"));
                    break;
                case "user":
                    parseUser(jsonObject.getJSONArray("user"));
                    break;
                case "page":
                    parsePage(jsonObject.getJSONArray("page"));
                    break;
                case "post":
                    parsePost(jsonObject.getJSONArray("post"));
                    break;
                case "comment":
                    parseComment(jsonObject.getJSONArray("comment"));
                    break;
                case "message":
                    parseMessage(jsonObject.getJSONObject("message"));
                    break;
                case "search":
                    parseSearch(jsonObject.getJSONObject("search"));
                    break;
                case "messageRoom":
                    parseMessageRoom(jsonObject.getJSONArray("messageRoom"));
                    break;
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

    private static void parseBaseUser(JSONObject baseUser) throws JSONException {
        new BaseUser(baseUser.getInt("id"), baseUser.getString("username"), baseUser.getString("name"));
    }

    private static void parseUser(JSONArray user) throws JSONException {
        JSONObject u = user.getJSONObject(0);
        parseBaseUser(user.getJSONObject(2));
        new User(u.getInt("user_id"), u.getString("location"), u.getString("information"), u.getString("profile_path"),
                parseGenre(user.getJSONObject(1)));
    }

    private static User getParseUser(JSONArray user) throws JSONException {
        JSONObject u = user.getJSONObject(0);
        parseBaseUser(user.getJSONObject(2));
        return new User(u.getInt("user_id"), u.getString("location"), u.getString("information"), u.getString("profile_path"),
                parseGenre(user.getJSONObject(1)));
    }

    private static void parsePage(JSONArray page) throws JSONException {
        JSONObject p = page.getJSONObject(0);
        List<User> users = parseMembers(page.getJSONArray(2));
        if (page.length() < 4) {
            new Page(p.getInt("id"), p.getString("type"), p.getString("name"), p.getString("location"),
                    p.getString("information"), p.getString("profilePath"), false, false,
                    parseGenre(page.getJSONObject(1)), users);
        } else {
            JSONObject pi = page.getJSONObject(3);
            new Page(p.getInt("id"), p.getString("type"), p.getString("name"), p.getString("location"),
                    p.getString("information"), p.getString("profilePath"), pi.getBoolean("member"),
                    pi.getBoolean("favorite"), parseGenre(page.getJSONObject(1)), users);
        }
    }

    private static void parsePost(JSONArray post) throws JSONException {
        JSONObject p = post.getJSONObject(0);
        parseUser(post.getJSONArray(1));
        parsePage(post.getJSONArray(2));
        if (post.length() < 4) {
            new Post(p.getInt("id"), p.getString("dateSent"), p.getInt("userId"), p.getInt("ownerId"),
                    p.getString("message"), false);
        } else {
            JSONObject l = post.getJSONObject(3);
            new Post(p.getInt("id"), p.getString("dateSent"), p.getInt("userId"), p.getInt("ownerId"),
                    p.getString("message"), l.getBoolean("like"));
        }
    }

    private static void parseComment(JSONArray comment) throws JSONException {
        JSONObject c = comment.getJSONObject(0);
        parseUser(comment.getJSONArray(1));
        new Comment(c.getInt("id"), c.getInt("postId"), c.getString("dateSent"), c.getInt("senderId"),
                c.getString("message"));
    }

    private static void parseMessage(JSONObject message) throws JSONException {
        new Message(message.getInt("userId"), message.getInt("roomId"), message.getString("message"),
                message.getString("dateSent"));
    }

    private static void parseSearch(JSONObject search) throws JSONException {
        Search searchObject = new Search(search.getInt("id"), search.getString("kind"), search.getString("name"),
                search.getString("type"), search.getString("location"), search.getString("profilePath"));
        ResultsReceiver.addResults(Search.class, searchObject);
    }

    private static void parseMessageRoom(JSONArray messageRoom) throws JSONException {
        JSONObject m = messageRoom.getJSONObject(0);
        List<User> users = parseMembers(messageRoom.getJSONArray(1));
        new MessageRoom(m.getInt("id"), m.getString("title"), users);
    }

    private static List<User> parseMembers(JSONArray users) throws JSONException {
        List<User> members = new LinkedList<>();
        for (int n = 0; n < users.length(); n++) {
            members.add(getParseUser(users.getJSONArray(n)));
        }
        return members;
    }
}
