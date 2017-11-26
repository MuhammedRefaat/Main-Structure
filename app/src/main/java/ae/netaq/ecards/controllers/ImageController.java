package ae.netaq.ecards.controllers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Callback;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ae.netaq.ecards.eventBus.ImageLoaded;
import ae.netaq.ecards.misc.AppPreferences;

/**
 * Created by M.Refaat on 11/23/2017.
 */

public class ImageController {

    /**
     * To get the image, track its download, and store it on the device internal storage to be used directly onwards without networking
     *
     * @param context     the context of the current Activity that is trying to setImageResource for one of its views
     * @param targetImage the imageView inside the activity that is meant to has its background resource image setted
     * @param url         the url to get the corresponding url to get the image to be displayed im the imageView
     * @param imageLoadedIndication to send a BC for "image loading complete" or not
     * @return the corresponding Picasso Target after setting the Image
     */
    public static Target saveImage(final Context context, final ImageView targetImage, final String url, final boolean imageLoadedIndication) {

        Target target = new Target() {

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                targetImage.setImageBitmap(bitmap);
                                if(imageLoadedIndication) {
                                    // run the eventBus to hide the progressWheel & start the progressBar in case of story display
                                    EventBus.getDefault().post(new ImageLoaded(""));
                                }
                            }
                        });

                        String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
                        File folder = new File(extStorageDirectory, AppPreferences.eCards_NOMEDIA);
                        try {
                            folder.mkdir();
                            // to make sure the No Media folder will be un-viewable
                            new File(folder, ".nomedia").createNewFile();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        File file = new File(folder, url.split("/")[url.split("/").length - 1]);

                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            FileOutputStream out = new FileOutputStream(file);
                            bitmap.setHasAlpha(true);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 80, out);
                            out.flush();
                            out.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }

    /**
     * To get a stored image from the internal storage and return its corresponding bitmap
     *
     * @param url the url of that image to be able to get the stored image name
     * @return the Bitmap of the resulted image or "null" if there is no image stored under the resulted name
     */
    public static File getImage(String url) {
        File imgFile = new File(AppPreferences.NO_MEDIA_PATH + url.split("/")[url.split("/").length - 1]);
        return imgFile;
    }

    /**
     * To check the permission for "Write External Storage" to be able to store and retrieve images
     */
    public static void checkPermission(Context context) {
        // check for the External Storage allowance
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    100);
        }
    }

    /**
     * To load an Image from the network or the disk into corresponding ImageView,
     * or load a replacement image in case of failing to load the image.
     *  @param url        the url of the corresponding image to be loaded
     * @param context    the context of the current Activity or fragment
     * @param image      the imageView that is supposed to hold the retrieved image
     * @param replacerID the drawable id of the replacement image that is supposed to replace the retrieved image if it is failed to be loaded in any way
     * @param imageLoadedIndication to send a BC for "image loading complete" or not
     */
    public static void loadImage(String url, final Context context, final ImageView image, final int replacerID, final boolean imageLoadedIndication, boolean circleImage) {
        /**
         * The callback for loading an image using picasso
         */
        Callback imageLoadCallback = new Callback() {
            @Override
            public void onSuccess() {
                if(imageLoadedIndication) {
                    // run the eventBus to hide the progressWheel & start the progressBar in case of story display
                    EventBus.getDefault().post(new ImageLoaded(""));
                }
            }

            @Override
            public void onError() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    image.setImageDrawable(context.getDrawable(replacerID));
                }
            }
        };
        // Now, begin loading the image
        if (!TextUtils.isEmpty(url)) {
            try {
                File imgFile = ImageController.getImage(url);
                if (imgFile.exists()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if(circleImage)
                            Picasso.with(context).load(imgFile).error(context.getDrawable(replacerID)).placeholder(context.getDrawable(replacerID)).transform(new CircleTransformer()).into(image, imageLoadCallback);
                        else
                            Picasso.with(context).load(imgFile).error(context.getDrawable(replacerID)).placeholder(context.getDrawable(replacerID)).into(image, imageLoadCallback);
                    } else {
                        if(circleImage)
                            Picasso.with(context).load(imgFile).error(context.getResources().getDrawable(replacerID)).placeholder(context.getResources().getDrawable(replacerID)).transform(new CircleTransformer()).into(image, imageLoadCallback);
                        else
                            Picasso.with(context).load(imgFile).error(context.getResources().getDrawable(replacerID)).placeholder(context.getResources().getDrawable(replacerID)).into(image, imageLoadCallback);
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if(circleImage)
                            Picasso.with(context).load(url).error(context.getDrawable(replacerID)).placeholder(context.getDrawable(replacerID)).transform(new CircleTransformer()).into(ImageController.saveImage(context, image, url, imageLoadedIndication));
                        else
                            Picasso.with(context).load(url).error(context.getDrawable(replacerID)).placeholder(context.getDrawable(replacerID)).into(ImageController.saveImage(context, image, url, imageLoadedIndication));
                    } else {
                        if(circleImage)
                            Picasso.with(context).load(url).error(context.getResources().getDrawable(replacerID)).placeholder(context.getResources().getDrawable(replacerID)).transform(new CircleTransformer()).into(ImageController.saveImage(context, image, url, imageLoadedIndication));
                        else
                            Picasso.with(context).load(url).error(context.getResources().getDrawable(replacerID)).placeholder(context.getResources().getDrawable(replacerID)).into(ImageController.saveImage(context, image, url, imageLoadedIndication));
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                image.setImageResource(replacerID);
            }
        } else {
            image.setImageResource(replacerID);
        }
    }


    /**
     * initiating Picasso library to use it getting/displaying the images in the corresponding Activity or fragment
     */
    public static Picasso initPicasso(Context context) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        OkHttpDownloader mOkHttpDownloader = new OkHttpDownloader(mOkHttpClient);
        return new Picasso.Builder(context).downloader(mOkHttpDownloader).indicatorsEnabled(true).build();
    }

}
