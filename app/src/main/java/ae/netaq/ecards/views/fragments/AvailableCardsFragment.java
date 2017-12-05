package ae.netaq.ecards.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import ae.netaq.ecards.R;
import ae.netaq.ecards.adapters.CardsListingAdapter;
import ae.netaq.ecards.database.dataModel.Cards;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by M.Refaat on 11/23/2017.
 */

public class AvailableCardsFragment extends Fragment {

    @BindView(R.id.no_data)
    RelativeLayout noData;
    @BindView(R.id.cards_recycler_view)
    RecyclerView cardsRecyclerView;

    CardsListingAdapter cardsListingAdapter;

    List<Cards> cards = new ArrayList<>();
    private List<Cards> tingTheRequiredCards;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_available_cards, container, false);
        ButterKnife.bind(this, view);

        // first, get the cards
        gettingTheRequiredCards();
        // then, display the cards
        settingTheCardsAdapter();

        return view;

    }

    /**
     * To get the corresponding cards to be displayed in the RecyclerView list
     *
     * @return the corresponding cards
     */
    public void gettingTheRequiredCards() {
        // filling Dummy cards
        Cards card = new Cards();
        for (int i = 0; i < 11; i++)
            cards.add(card);
        // remove NoData if there is any cards
        if (cards.size() > 0)
            noData.setVisibility(View.GONE);
    }

    /**
     * To set the whole operation of the cards list adapter
     */
    private void settingTheCardsAdapter() {
        cardsListingAdapter = new CardsListingAdapter(cards);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        cardsRecyclerView.setLayoutManager(layoutManager);
        cardsRecyclerView.setAdapter(cardsListingAdapter);
    }
}
