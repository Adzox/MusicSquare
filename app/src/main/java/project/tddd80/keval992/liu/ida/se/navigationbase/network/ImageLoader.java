package project.tddd80.keval992.liu.ida.se.navigationbase.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * AsyncTask used to load images from an url into an ImageView.
 * <p/>
 * A listener, {@link ImageLoaderListener} can be used to receive the Bitmap representing the image.
 * This listener must be set with the {@link ImageLoader#setImageLoaderListener(ImageLoaderListener)}
 */
public class ImageLoader extends AsyncTask<String, Integer, Bitmap> {

    private ImageView imageView;
    private ImageLoaderListener imageLoaderListener;

    /**
     * Creates a new ImageLoader which sets the resulting image to the ImageView.
     *
     * @param imageView The ImageView to set the loaded image on to.
     */
    public ImageLoader(ImageView imageView) {
        this.imageView = imageView;
    }

    /**
     * Loads images to ImageViews pairwise by creating a new ImageLoader for each pair and executing
     * it directly.
     * <p/>
     * Example usage is loading several images from a url to a list with each list item
     * containing an ImageView.
     *
     * @param pairs The pairs of ImageViews and Strings to use, where the ImageView displays the
     *              image and the string represents the path to the image.
     */
    public static void loadImagesToImageViews(Pair<ImageView, String>... pairs) {
        for (Pair<ImageView, String> pair : pairs) {
            new ImageLoader(pair.first).execute(pair.second);
        }
    }

    /**
     * Sets a listener to be called when the result image has been completely downloaded.
     *
     * @param imageLoaderListener The ImageLoaderListener to be called.
     */
    public void setImageLoaderListener(ImageLoaderListener imageLoaderListener) {
        this.imageLoaderListener = imageLoaderListener;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            conn.setDoInput(true);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestMethod("GET");
            conn.connect();
            InputStream is = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            if (imageView != null) {
                imageView.setImageBitmap(result);
            }
            if (imageLoaderListener != null) {
                imageLoaderListener.getResultBitmap(result);
            }
        }
        super.onPostExecute(result);
    }

    public interface ImageLoaderListener {

        void getResultBitmap(Bitmap results);
    }
}
