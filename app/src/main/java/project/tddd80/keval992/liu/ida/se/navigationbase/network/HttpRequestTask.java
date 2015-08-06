package project.tddd80.keval992.liu.ida.se.navigationbase.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class for fetching data from server via either this AsyncTask or a Thread.
 * Does a post to the server with the input data and returns it via this class atPostExecute
 * method. Call runThreaded if you suspect that the network call will take more time (UNTESTED!).
 * <p/>
 * Created by kevin on 2015-05-08.
 * Last edited 2015-06-30.
 */
public abstract class HttpRequestTask extends AsyncTask<JSONObject, Void, JSONObject> {

    private static final String URI_TO_SERVER = "http://musicsquare-musicsquare.openshift.ida.liu.se";
    private static final String SERVICE_ROUTING = "/applications/rest-api/ver/1/";
    private final String routing;
    private ProgressDialog progressDialog;
    private final AtPostExecuteListener atPostExecuteListener = new AtPostExecuteListener() {
        @Override
        public void atPostExecute(JSONObject jsonObject) {
            postExecute(jsonObject);
        }
    };

    /**
     * Creates a new HttpRequestTask which will post data to the given routing of the standard
     * web address.
     *
     * @param routing The routing to the server function to fetch data from.
     */
    public HttpRequestTask(String routing) {
        this.routing = routing;
        progressDialog = null;
    }

    /**
     * Creates a new HttpRequestTask which will post data to the given routing of the standard
     * web address.
     * Shows a progressDialog while loading.
     *
     * @param routing The routing to the server function to fetch data from.
     */
    public HttpRequestTask(String routing, Context context) {
        this.routing = routing;
        progressDialog = new ProgressDialog(context);
    }

    /**
     * Performs a post to the server with the routing and the specified input json data.
     *
     * @param jsonObject json data to post to the specified routing.
     * @return The result of the post.
     * @throws java.io.IOException
     */
    public String post(JSONObject jsonObject) throws IOException {
        HttpURLConnection urlConnection = null;
        String result = "";
        try {
            URL url = new URL(URI_TO_SERVER + SERVICE_ROUTING + routing);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-type", "application/json");
            urlConnection.connect();
            OutputStreamWriter printOut = new OutputStreamWriter(urlConnection.getOutputStream());
            printOut.write(jsonObject.toString());
            printOut.flush();
            printOut.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println("########################################");
            System.out.println("ROUTING: " + routing);
            System.out.println("INDATA:  " + jsonObject.toString());
            System.out.println("OUTDATA: " + result);
            in.close();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }
        return result;
    }

    /**
     * Execute this request via thread instead of AsyncTask.
     * Can be used for requests which takes longer time to finish.
     *
     * @param params The parameters to send to the server.
     */
    public final void runThreaded(final JSONObject... params) {
        new Thread(new HttpRequestRunnable(atPostExecuteListener, params)).start();
        if (progressDialog != null) {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }
    }

    @Override
    protected void onPreExecute() {
        if (progressDialog != null) {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }
    }

    @Override
    protected JSONObject doInBackground(JSONObject... params) {
        try {
            return new JSONObject(post(params[0]));
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        postExecute(jsonObject);
    }

    /**
     * Method to avoid calling onPostExecute when running threaded.
     */
    private void postExecute(JSONObject jsonObject) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (jsonObject != null) atPostExecute(jsonObject);
    }

    /**
     * What to do with the JSONObject output from the HTTP request call.
     * Called when the HTTP request call has finished.
     *
     * @param jsonObject The response JSON data from the server.
     */
    protected abstract void atPostExecute(JSONObject jsonObject);

    /**
     * Inner interface listener used to run atPostExecute in this HttpRequestTask when the threaded
     * network call has finished.
     */
    private interface AtPostExecuteListener {

        void atPostExecute(JSONObject jsonObject);
    }

    /**
     * Inner Runnable class which keeps track of the data for doing a HTTP request with threads
     * instead of AsyncTasks.
     */
    private class HttpRequestRunnable implements Runnable {

        private JSONObject[] input;
        private AtPostExecuteListener atPostExecuteListener;

        HttpRequestRunnable(AtPostExecuteListener atPostExecuteListener, JSONObject... input) {
            this.input = input;
            this.atPostExecuteListener = atPostExecuteListener;
        }

        private void doInBackground(JSONObject... params) {
            try {
                atPostExecuteListener.atPostExecute(new JSONObject(post(params[0])));
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            atPostExecuteListener.atPostExecute(null);
        }

        @Override
        public void run() {
            doInBackground(input);
        }
    }
}
