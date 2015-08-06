package project.tddd80.keval992.liu.ida.se.navigationbase.network;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Util class for managing application preferences.
 * <p/>
 * Created by kevin on 2015-05-10.
 */
public final class PreferencesManager {

    private PreferencesManager() {
    }

    public static void writeToPreferences(Activity activity, String identifier, String data) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(identifier, data);
        editor.commit();
    }

    public static String readFromPreferences(Activity activity, String identifier, String defaultValue) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(identifier, defaultValue);
    }
}
