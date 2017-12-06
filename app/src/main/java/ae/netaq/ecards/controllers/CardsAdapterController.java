package ae.netaq.ecards.controllers;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ae.netaq.ecards.adapters.CardsListingAdapter;
import ae.netaq.ecards.database.dataModel.Cards;

/**
 * Created by M.Refaat on 12/6/2017.
 */

public class CardsAdapterController {

    /**
     * To set the whole operation of the cards list adapter
     */
    public static CardsListingAdapter settingTheCardsAdapter(Context context, RecyclerView cardsRecyclerView, List<Cards> cards) {
        CardsListingAdapter cardsListingAdapter = new CardsListingAdapter(cards);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
        cardsRecyclerView.setLayoutManager(layoutManager);
        cardsRecyclerView.setAdapter(cardsListingAdapter);
        return cardsListingAdapter;
    }


    /**
     * To get the corresponding cards to be displayed in the RecyclerView list
     *
     * @return the corresponding cards
     */
    public static List<Cards> gettingTheRequiredCards(boolean gettingAllAvailableCards) {
        List<Cards> cards = new ArrayList<>();
        // filling Dummy cards
        Cards card = new Cards();
        int idx = 0;
        if(!gettingAllAvailableCards)
            idx = 5;
        for (int i = idx; i < 11; i++)
            cards.add(card);
        return cards;
    }

}
