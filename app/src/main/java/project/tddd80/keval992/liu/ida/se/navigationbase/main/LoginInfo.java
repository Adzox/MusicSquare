package project.tddd80.keval992.liu.ida.se.navigationbase.main;

import android.app.Activity;

import java.io.Serializable;

import project.tddd80.keval992.liu.ida.se.navigationbase.network.PreferencesManager;

/**
 * Singleton class used to store login data.
 */
public class LoginInfo implements Serializable {

    private static final String USER_ID = "User ID";
    private static final String LOGGED_IN = "Logged in";
    private static final String ADVANCED_USER = "Advanced user";
    private static String registrationId;
    private static LoginInfo loginInfo = new LoginInfo(-1, false);
    ;
    private static boolean hasLoggedIn;
    private int userId;
    private boolean isAdvancedUser;

    private LoginInfo(int userId, boolean isAdvancedUser) {
        this.userId = userId;
        this.isAdvancedUser = isAdvancedUser;
    }

    public static void saveLogin(int userId, boolean isAdvancedUser) {
        loginInfo = new LoginInfo(userId, isAdvancedUser);
        hasLoggedIn = true;
    }

    public static void logout() {
        hasLoggedIn = false;
    }

    public static boolean hasLoggedIn() {
        return hasLoggedIn;
    }

    public static void saveRegistrationId(String registrationId) {
        LoginInfo.registrationId = registrationId;
    }

    public static int getUserId() {
        return loginInfo.userId;
    }

    public static boolean isAdvancedUser() {
        return loginInfo.isAdvancedUser;
    }

    public static String getRegistrationId() {
        return registrationId;
    }

    public static void saveToPreferences(Activity activity) {
        PreferencesManager.writeToPreferences(activity, USER_ID, "" + getUserId());
        PreferencesManager.writeToPreferences(activity, LOGGED_IN, "" + hasLoggedIn);
        PreferencesManager.writeToPreferences(activity, ADVANCED_USER, "" + isAdvancedUser());
    }

    public static void loadFromPreferences(Activity activity) {
        loginInfo.userId = Integer.parseInt(PreferencesManager.readFromPreferences(activity, USER_ID, "-1"));
        hasLoggedIn = Boolean.parseBoolean(PreferencesManager.readFromPreferences(activity, LOGGED_IN, "false"));
        loginInfo.isAdvancedUser = Boolean.parseBoolean(PreferencesManager.readFromPreferences(activity, ADVANCED_USER, "false"));
    }
}
