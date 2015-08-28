package project.tddd80.keval992.liu.ida.se.navigationbase.network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Helper class for making uploads to server.
 * <p/>
 * Created by kevin on 2015-08-27.
 */
public class ImageUploader {

    private static final String URL_SERVER = "http://musicsquare-musicsquare.openshift.ida.liu.se/applications/rest-api/ver/1/upload";
    private static final String LINE_END = "\r\n", TWO_HYPHENS = "--", BOUNDARY = "*****";
    private static final int MAX_BUFFER_SIZE = 2 * 1024 * 1024;

    private Activity activity;
    private ProgressDialog progressDialog;

    public ImageUploader(final Activity activity, final String filename) {
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Sending image to server...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                progressDialog.show();
                doUpload(filename);
            }
        }).start();
    }

    public void doUpload(String filename) {
        HttpURLConnection conn = null;
        DataOutputStream os = null;
        int bytesRead, bytesAvailable, bufferSize, bytesUploaded = 0;
        byte[] buffer;
        String uploadname = filename.substring(23);

        try {
            FileInputStream fis = new FileInputStream(new File(filename));
            // URL initializing
            URL url = new URL(URL_SERVER);
            conn = (HttpURLConnection) url.openConnection();
            conn.setChunkedStreamingMode(MAX_BUFFER_SIZE);
            // POST settings
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data; BOUNDARY=" + BOUNDARY);
            conn.connect();
            // WRITING CONTENT DESCRIPTION
            os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(TWO_HYPHENS + BOUNDARY + LINE_END);
            os.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + uploadname + "\"" + LINE_END);
            os.writeBytes(LINE_END);
            // PREPARING THE UPLOAD
            bytesAvailable = fis.available();
            System.out.println("available: " + String.valueOf(bytesAvailable));
            bufferSize = Math.min(bytesAvailable, MAX_BUFFER_SIZE);
            buffer = new byte[bufferSize];
            // UPLOADING THE DATA
            bytesRead = fis.read(buffer, 0, bufferSize);
            bytesUploaded += bytesRead;
            while (bytesRead > 0) {
                os.write(buffer, 0, bufferSize);
                bytesAvailable = fis.available();
                bufferSize = Math.min(bytesAvailable, MAX_BUFFER_SIZE);
                buffer = new byte[bufferSize];
                bytesRead = fis.read(buffer, 0, bufferSize);
                bytesUploaded += bytesRead;
            }
            System.out.println("uploaded: " + String.valueOf(bytesUploaded));
            os.writeBytes(LINE_END);
            os.writeBytes(TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + LINE_END);
            conn.setConnectTimeout(2000);
            // CHECKING RESPONSE
            if (conn.getResponseCode() == 200) {
                Toast.makeText(activity, "Successfully uploaded image to server!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(activity, "Failed to upload image to server!", Toast.LENGTH_LONG).show();
            }
            fis.close();
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
        }
    }
}
