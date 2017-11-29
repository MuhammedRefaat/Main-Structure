package ae.netaq.ecards.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ae.netaq.ecards.R;
import ae.netaq.ecards.misc.AppPreferences;
import ae.netaq.ecards.views.custom_views.ShareButton;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by M.Refaat on 11/29/2017.
 */

public class CustomizedCard extends Fragment {

    @BindView(R.id.card_view)
    ImageView cardView;
    @BindView(R.id.card_share)
    ShareButton cardShare;

    private String cardId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // get the main layout
        View view = inflater.inflate(R.layout.fragment_customized_card, container, false);
        // get the Bundle Data
        getTheData();
        // bind the views
        ButterKnife.bind(this, view);
        //assign Share Button ImageView instance
        cardShare.setToShare(cardView);
        return view;
    }

    /**
     * To get the data sent within the fragment Bundle
     */
    public void getTheData() {
        // get the card Id
        cardId = getArguments().getString(AppPreferences.cardId);
    }
}
