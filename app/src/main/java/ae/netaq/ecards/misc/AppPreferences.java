package ae.netaq.ecards.misc;

import android.os.Environment;

/**
 * Created by attribe on 6/25/15.
 */
public class AppPreferences {

    // the directory for saving no-media files
    public static final String eCards_NOMEDIA = "eCards-nomedia";
    // the complete path where we keep no-media files of the app
    public static final String NO_MEDIA_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + eCards_NOMEDIA + "/";

}
