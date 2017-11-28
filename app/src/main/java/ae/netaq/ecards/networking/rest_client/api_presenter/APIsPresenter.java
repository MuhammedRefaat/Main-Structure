package ae.netaq.ecards.networking.rest_client.api_presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import ae.netaq.ecards.R;
import ae.netaq.ecards.networking.rest_client.RetrofitClient;
import ae.netaq.ecards.networking.rest_client.api_interfaces.MainApiInterface;

/**
 * Created by Muhammed Refaat on 2/7/2017.
 *
 * This class is used to handle all the calls and responses to/from the server APIs
 */

public class APIsPresenter {

    protected static Context context;

    protected static MainApiInterface adapter;

    private static APIsPresenter instance = null;

    protected static ProgressDialog progressDialog;

    public static void initAPIsPresenter() {
        adapter = RetrofitClient.getAdapter();
    }

    private APIsPresenter() {
    }

    public static APIsPresenter getInstance(Context appContext) {
        APIsPresenter.context = appContext;
        if (instance == null) {
            instance = new APIsPresenter();
            RetrofitClient.buildHeaders();
            initAPIsPresenter();
        }
        return instance;
    }

    /**
     * To display a progress dialog in case of waiting a server response
     *
     * @param message the message to be displayed
     */
    public static void displayProgressDialog(String message) {
        try {
            progressDialog = ProgressDialog.show(APIsPresenter.context, message, APIsPresenter.context.getString(R.string.please_wait));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * The method used to close the ProgressDialog
     * in addition to display the corresponding result message as a Toast Message
     *
     * @param message the message to be shown in the Toast Message
     */
    public static void closeDialog(String message) {
        try {
            if (progressDialog.isShowing())
                progressDialog.hide();
            Toast.makeText(APIsPresenter.context, message, Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This is the method which is responsible for displaying informative dialog to the user to inform him about any condition
     *
     * @param message the message to be displayed in the informative dialog
     */
    public static void displayInformativeDialog(String message) {
        try {
            if (progressDialog.isShowing())
                progressDialog.hide();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        AlertDialog.Builder displayResult = new AlertDialog.Builder(APIsPresenter.context)
                .setMessage(message)
                .setNeutralButton(APIsPresenter.context.getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        });
        displayResult.show();
    }

}
