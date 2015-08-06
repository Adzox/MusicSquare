package project.tddd80.keval992.liu.ida.se.navigationbase.network;

import project.tddd80.keval992.liu.ida.se.navigationbase.models.Message;

/**
 * Interface for receiving the message from gcm.
 */
public interface GcmMessageReciever {

    public void onResultReceived(Message message);
}
