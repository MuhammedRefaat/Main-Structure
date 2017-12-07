package ae.netaq.ecards.views.custom_views;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import ae.netaq.ecards.R;

/**
 * Created by M.Refaat on 11/29/2017.
 */

public class ShareButton extends android.support.v7.widget.AppCompatImageButton implements View.OnClickListener {

    /* the holding instance of the image to be shared */
    ImageView toShare;

    public ShareButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        checkPermission(context);
        this.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bitmap mBitmap = ((BitmapDrawable) toShare.getDrawable()).getBitmap();
        String path = MediaStore.Images.Media.insertImage(this.getContext().getContentResolver(),
                mBitmap, getResources().getString(R.string.app_name), null);
        Uri uri = Uri.parse(path);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_text));
        this.getContext().startActivity(Intent.createChooser(share, getResources().getString(R.string.share_informer)));
    }

    /**
     * To set the instance of the image to be shared
     *
     * @param toShare the instance of the ImageView to be shared
     */
    public void setToShare(ImageView toShare) {
        this.toShare = toShare;
    }

    /**
     * To check the permission for "Write External Storage" to be able to retrieve images
     */
    public static void checkPermission(Context context) {
        // check for the External Storage allowance
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    100);
        }
    }

}
