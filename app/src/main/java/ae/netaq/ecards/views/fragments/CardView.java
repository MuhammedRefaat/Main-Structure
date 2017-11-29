package ae.netaq.ecards.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import ae.netaq.ecards.R;
import ae.netaq.ecards.misc.AppPreferences;
import ae.netaq.ecards.views.custom_views.ShareButton;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by M.Refaat on 11/29/2017.
 */

public class CardView extends Fragment {

    @BindView(R.id.card_view)
    ImageView cardView;

    @BindView(R.id.card_customize_container)
    LinearLayout cardCustomizeLay;
    @BindView(R.id.card_edit_container)
    RelativeLayout cardViewLay;

    @BindView(R.id.card_share)
    ShareButton cardShare;

    @BindView(R.id.action_on_card)
    Button actionOnCard;

    private String cardId;
    private boolean cardCustomize = true;

    public CardView() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // get the main layout
        View view = inflater.inflate(R.layout.fragment_card_view, container, false);
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
        // first, get the card Id
        cardId = getArguments().getString(AppPreferences.cardId);
        // then, get what to display
        cardCustomize = getArguments().getBoolean(AppPreferences.cardDisplay);
        if (!cardCustomize)
            toggleView();
    }

    /**
     * To toggle the view display from customize card to edit card depending on the issuing fragment
     */
    private void toggleView() {
        cardCustomizeLay.setVisibility(View.GONE);
        cardViewLay.setVisibility(View.VISIBLE);
        actionOnCard.setText(getResources().getString(R.string.edit_card));
    }
}
