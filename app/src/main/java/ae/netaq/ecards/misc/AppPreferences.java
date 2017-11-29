package ae.netaq.ecards.misc;

import android.os.Environment;

/**
 * Created by attribe on 6/25/15.
 */
public class AppPreferences {

    // The URL for contacting server using rest client
    public static final String URL = "";
    // the directory for saving no-media files
    public static final String eCards_NOMEDIA = "eCards-nomedia";
    // the complete path where we keep no-media files of the app
    public static final String NO_MEDIA_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + eCards_NOMEDIA + "/";
    // a preferences key
    public static String cardDisplay = "CARD_DISPLAY";
    public static String cardId = "CARD_ID";
}
