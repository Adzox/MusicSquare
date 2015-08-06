package project.tddd80.keval992.liu.ida.se.navigationbase.network;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;
import project.tddd80.keval992.liu.ida.se.navigationbase.main.LoginInfo;
import project.tddd80.keval992.liu.ida.se.navigationbase.main.MainActivity;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Message;

/**
 * Created by kevin on 2015-07-21.
 */
public class GcmMessageHandler extends GcmListenerService {

    public static final int MESSAGE_NOTIFICATION_ID = 435345;
    public static MainActivity myMainActivity;

    private GcmMessageReciever gcmMessageReciever;


    public void setGcmMessageReciever(GcmMessageReciever gcmMessageReciever) {
        this.gcmMessageReciever = gcmMessageReciever;
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        Message message = convertToMessage(data);
        if (isApplicationAlive() && LoginInfo.hasLoggedIn() && LoginInfo.isAdvancedUser()) {
            if (gcmMessageReciever != null) {
                gcmMessageReciever.onResultReceived(convertToMessage(data));
            } else {
                myMainActivity.getToolbar().setSubtitle(message.toString());
            }
        } else {
            createNotification("New message on MusicSqare!", message.toString());
        }
    }

    private boolean isApplicationAlive() {
        return myMainActivity != null;
    }

    private Message convertToMessage(Bundle data) {
        // Extract sender user ID and message, create new Message object with it. Return message
        // object.
        return null;
    }

    private void createNotification(String title, String body) {
        Context context = getBaseContext();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher).setContentTitle(title)
                .setContentText(body);
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
    }
}
