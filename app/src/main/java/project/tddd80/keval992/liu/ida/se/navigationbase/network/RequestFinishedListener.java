package project.tddd80.keval992.liu.ida.se.navigationbase.network;

/**
 * Listener to be used in HttpRequestTasks for defining what to do after a server call has finished.
 * <p/>
 * Created by kevin on 2015-05-14.
 */
public interface RequestFinishedListener {

    public void whenRequestFinished();

    public class NullRequestFinishedListener implements RequestFinishedListener {

        @Override
        public void whenRequestFinished() {
        }
    }
}
