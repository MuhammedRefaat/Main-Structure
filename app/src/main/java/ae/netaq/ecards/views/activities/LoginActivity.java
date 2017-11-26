package ae.netaq.ecards.views.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.InputStream;
import java.util.Arrays;

import ae.netaq.ecards.R;
import ae.netaq.ecards.misc.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by M.Refaat on 11/22/2017.
 */

public class LoginActivity extends Activity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "LoginActivity";

    @BindView(R.id.google_login)
    Button gmLogin;
    @BindView(R.id.fb_login)
    Button fbLogin;
    @BindView(R.id.login_button)
    LoginButton fbLoginButton;
    @BindView(R.id.sign_out)
    Button signOutButton;
    @BindView(R.id.profile_photo)
    ImageView profilePhoto;
    @BindView(R.id.name)
    TextView name;

    private static CallbackManager callbackManager;
    private static GoogleApiClient mGoogleApiClient;
    private static ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        fbLogin.setOnClickListener(this);
        gmLogin.setOnClickListener(this);

        // facebook login
        fbLoginButton.setReadPermissions("email");
        // Other app specific specialization

        // check if user already logined to FB
        callbackManager = CallbackManager.Factory.create();

        // Callback registration
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                updateUI(false);
                            }
                        });
            }
        });

    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            gmLogin.setVisibility(View.GONE);
            signOutButton.setVisibility(View.VISIBLE);
            Utils.startMainActivity(LoginActivity.this);
        } else {
            name.setText("signed out");
            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_camera);
            profilePhoto.setImageBitmap(icon);
            gmLogin.setVisibility(View.VISIBLE);
            signOutButton.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            /*showProgressDialog(LoginActivity.this, "Please Wait");
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog(LoginActivity.this, "signed in");
                    handleSignInResult(googleSignInResult);
                }
            });*/
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    /**
     * To display a progress dialog in case of waiting a server response
     *
     * @param message the message to be displayed
     */
    public static void showProgressDialog(Context context, String message) {
        try {
            progressDialog = ProgressDialog.show(context, message, message);
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
    public static void hideProgressDialog(Context context, String message) {
        try {
            if (progressDialog.isShowing())
                progressDialog.hide();
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * the OnClickListener for the clickable views inside this Activity.
     * Login Buttons for (Facebook, Gmail)
     *
     * @param view will be even Facebook button or Gmail button
     */
    @Override
    public void onClick(View view) {
        // to provide sprinkle encouragement
        final View v = view;
        v.setAlpha(0.3f);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                v.setAlpha(1f);
            }
        }, 300);

        // to work with the clicked Button
        if (view == fbLogin) {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        } else if (view == gmLogin) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            name.setText(acct.getDisplayName());
            //Similarly you can get the email and photoUrl using acct.getEmail() and  acct.getPhotoUrl()
            if(acct.getPhotoUrl() != null)
                new LoadProfileImage(profilePhoto).execute(acct.getPhotoUrl().toString());

            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * Background Async task to load user profile picture from url
     * */
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... uri) {
            String url = uri[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {

            if (result != null) {

                Bitmap resized = Bitmap.createScaledBitmap(result,200,200, true);
                bmImage.setImageBitmap(resized);

            }
        }
    }

}
