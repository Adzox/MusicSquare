package project.tddd80.keval992.liu.ida.se.navigationbase.network;

import org.json.JSONException;
import org.json.JSONObject;

import project.tddd80.keval992.liu.ida.se.navigationbase.main.LoginInfo;

/**
 * Util class for creating formats of JSONObjects to send to the server.
 * <p/>
 * Named factory since it creates JSON data ready to send from given parameters.
 * See https://en.wikipedia.org/Factory_(object-oriented_programming) for more informationrmation.
 * <p/>
 * Created by kevin on 2015-05-09.
 */
public final class JSONFactory {

    private JSONFactory() {
    }

    private static JSONObject createSendFormat(String type, JSONObject data) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", type);
            jsonObject.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject createLoginData(String username, String passwordword) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", passwordword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return createSendFormat("", jsonObject);
    }

    public static JSONObject createNewRegIdData(int userId, String regId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", userId);
            jsonObject.put("regId", regId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return createSendFormat("gcmRegister", jsonObject);
    }

    private static JSONObject createBaseRegisterData(String username, String name, String password) {
        JSONObject object = new JSONObject();
        try {
            object.put("username", username);
            object.put("name", name);
            object.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static JSONObject createRegisterData(String username, String name, String password) {
        return createSendFormat("base_users", createBaseRegisterData(username, name, password));
    }

    public static JSONObject createRegisterData(String username, String name, String password, String location, String information) {
        JSONObject object = new JSONObject();
        try {
            object.put("requesterId", LoginInfo.getUserId());
            object.put("information", information);
            object.put("location", location);
            object.put("userdata", createBaseRegisterData(username, name, password));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return createSendFormat("", object);
    }

    public static JSONObject createCreateNewPageData(String name, String type, String location, String information) {
        JSONObject object = new JSONObject();
        try {
            object.put("name", name);
            object.put("type", type);
            object.put("information", information);
            object.put("location", location);
            addRequesterData(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return createSendFormat("pages", object);
    }

    private static void addRequesterData(JSONObject jsonObject) throws JSONException {
        jsonObject.put("requesterId", LoginInfo.getUserId());
    }

    public static JSONObject createIdData() {
        JSONObject object = new JSONObject();
        try {
            object.put("userId", LoginInfo.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return createSendFormat("", object);
    }

    public static JSONObject createFavoriteData(int pageId) {
        JSONObject object = new JSONObject();
        try {
            object.put("userId", LoginInfo.getUserId());
            object.put("pageId", pageId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return createSendFormat("pageFavorited", object);
    }

    public static JSONObject createPostIdData(int postId) {
        JSONObject object = new JSONObject();
        try {
            object.put("postId", postId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return createSendFormat("", object);
    }

    public static JSONObject createGlobalNewsData(int numberOfPosts) {
        JSONObject object = new JSONObject();
        try {
            object.put("number", numberOfPosts);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return createSendFormat("", object);
    }

    public static JSONObject createFavoriteNewsData(int numberOfPosts) {
        JSONObject object = new JSONObject();
        try {
            object.put("number", numberOfPosts);
            object.put("id", LoginInfo.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return createSendFormat("", object);
    }

    public static JSONObject createNewComment(String text, int postId) {
        JSONObject object = new JSONObject();
        try {
            object.put("message", text);
            object.put("postId", postId);
            object.put("senderId", LoginInfo.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return createSendFormat("comments", object);
    }

    public static JSONObject createNewPost(String message, int pageId) {
        JSONObject object = new JSONObject();
        try {
            object.put("message", message);
            object.put("ownerId", pageId);
            object.put("userId", LoginInfo.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return createSendFormat("posts", object);
    }

    public static JSONObject createLike(int postId) {
        JSONObject object = new JSONObject();
        try {
            object.put("postId", postId);
            object.put("userId", LoginInfo.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return createSendFormat("likes", object);
    }
}
