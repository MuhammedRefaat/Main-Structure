package ae.netaq.ecards.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import ae.netaq.ecards.R;
import ae.netaq.ecards.adapters.CardsListingAdapter;
import ae.netaq.ecards.controllers.CardsAdapterController;
import ae.netaq.ecards.database.dataModel.Cards;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by M.Refaat on 11/23/2017.
 */

public class MyCardsFragment extends Fragment {

    @BindView(R.id.no_data)
    RelativeLayout noData;
    @BindView(R.id.cards_recycler_view)
    RecyclerView cardsRecyclerView;

    CardsListingAdapter cardsListingAdapter;
    List<Cards> cards = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // initiating the layout
        View view = inflater.inflate(R.layout.fragment_my_cards, container, false);
        // binding the views
        ButterKnife.bind(this, view);
        // To set all of the required data/views for the fragment
        settingTheFragmentCredentials();
        // returning the resulted view
        return view;
    }

    /**
     * To set all of the required data/views initially required to be displayed in the fragment
     */
    private void settingTheFragmentCredentials() {
        // first, get the cards
        cards = CardsAdapterController.
                gettingTheRequiredCards(false);

        // then, remove "NoCards" indication if there is any cards to be displayed
        if (cards.size() > 0)
            noData.setVisibility(View.GONE);

        // finally, display the cards
        cardsListingAdapter = CardsAdapterController.
                settingTheCardsAdapter(getActivity(), cardsRecyclerView, cards);
    }

}
