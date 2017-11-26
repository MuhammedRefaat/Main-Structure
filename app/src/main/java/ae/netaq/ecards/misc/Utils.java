package ae.netaq.ecards.misc;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import ae.netaq.ecards.R;
import ae.netaq.ecards.views.activities.MainActivity;

/**
 * Created by attribe on 7/23/15.
 */
public class Utils {

    public static Uri getImageUri(String path) {
        return Uri.fromFile(new File(path));
    }

    public static int getDP(Context context, float pixels) {

        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
        int dp = 400; // have default value for safe side

        try {
            dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels, displaymetrics);
        } catch (Exception exc) {

            throw new RuntimeException("Pixel to Dp conversion issue");
        }


        return dp;
    }

    /**
     * To check the current App Locale
     *
     * @param context the current holding context
     * @return if the current locale is Arabic(true) or Not(false)
     */
    public static boolean isAPPLocaleArabic(Context context) {
        boolean isAppLocaleArabic = false;

        Resources resources = context.getResources();

        if (resources.getBoolean(R.bool.is_arabic_lang)) {
            isAppLocaleArabic = true;
        }

        return isAppLocaleArabic;

    }

    /**
     * To set the layout direction according to the current app locale
     *
     * @param context the current holding context
     * @param view    the corresponding view to have its direction set
     */
    public static void setViewAccordingToLocale(Context context, View view) {
        if (isAPPLocaleArabic(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        }
    }

    /**
     * To show a normal AlertDialog Message
     *
     * @param context the current context to create the Dialog From
     * @param message the message to be displayed
     */
    public static void showDialogAlert(final Context context, String message, final boolean reload) {
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(context);
        dialog.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (reload)
                    startMainActivity(context);
            }
        }).setMessage(message).create().show();
    }

    /**
     * This method is used to copy files between two locations with the same name
     *
     * @param inputPath  the current file path
     * @param inputFile  the file name
     * @param outputPath the desired path for the file to be copied to
     */
    public static void copyFile(String inputPath, String inputFile, String outputPath, String outputFile) {

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File(outputPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            in = new FileInputStream(inputPath + "/" + inputFile);
            out = new FileOutputStream(outputPath + "/" + outputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

        } catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }

    /**
     * To get the outPut directory file for a media item (image)
     *
     * @return the media item corresponding file
     */
    public static File getOutputMediaFile() {

        String mediaPath;
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                AppPreferences.eCards_NOMEDIA);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaPath = mediaStorageDir.getPath() + File.separator
                + "VID_" + timeStamp + ".mp4";

        mediaFile = new File(mediaPath);

        return mediaFile;
    }

    /**
     * To launch the MainActivity in student account login
     */
    public static void startMainActivity(Context context) {
        Intent showMainActivity = new Intent(context, MainActivity.class);
        showMainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(showMainActivity);
        ((Activity) context).overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
        ((Activity) context).finish();
    }

    /**
     * To get a certain colour using the colour resource id, taking into account the different implementation of different APIs
     *
     * @param context         the current context to get the colour
     * @param colorResourceId the colour resource id
     * @return the corresponding colour
     */
    public static int getColor(Context context, int colorResourceId) {
        int color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            color = context.getResources().getColor(colorResourceId, null);
        else
            color = context.getResources().getColor(colorResourceId);
        return color;
    }

    /**
     * Method which is responsible for deleting a file from sdcard
     *
     * @param fileURL the corresponding URL of the file to be deleted
     */
    public static void deleteFile(String fileURL) {
        try {
            File fileToDelete = new File(fileURL);
            fileToDelete.getCanonicalFile().delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
