package ae.netaq.ecards.networking.rest_client.api_data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammed Refaat on 2/7/2017.
 */

public class MainAPIdata {

    @SerializedName("success")
    private int success;

    @SerializedName("message")
    private String message;

    public int getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

}
