package ae.netaq.ecards.controllers;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by M.Refaat on 11/23/2017.
 */

public class FileDownloader {

    public static void downloadFile(String fileUrl, File directory){
        try {

            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            int response = connection.getResponseCode();

            if(response == 200)
            {
                BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
                FileOutputStream fs = new FileOutputStream(directory);
                BufferedOutputStream out = new BufferedOutputStream(fs);
                byte [] buffer = new byte[16384];

                int len = 0;
                while ((len = in.read(buffer, 0, 16384)) != -1)
                    out.write(buffer, 0, len);

                out.flush();
                in.close();
                out.close();
            } else {
                connection.disconnect();
                return;
            }

            connection.disconnect();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
