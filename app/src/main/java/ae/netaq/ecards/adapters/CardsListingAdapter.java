package ae.netaq.ecards.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.SecureRandom;
import java.util.List;

import ae.netaq.ecards.R;
import ae.netaq.ecards.database.dataModel.Cards;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by M.Refaat on 12/5/2017.
 * -------------------
 * The required RecyclerView Adapter for setting the RecyclerView used in displaying the list of Cards
 */

public class CardsListingAdapter extends RecyclerView.Adapter<CardsListingAdapter.CardsViewHolder> {

    private List<Cards> cards;
    CardClickListener cardClickListener;

    /**
     * instructor which is used to set the cards
     *
     * @param cards the corresponding list of cards
     */
    public CardsListingAdapter(List<Cards> cards) {
        this.cards = cards;
    }

    public class CardsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card)
        public ImageView card;
        @BindView(R.id.card_title)
        public TextView title;
        @BindView(R.id.card_customizations_num)
        public TextView customizationsNum;

        public CardsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            //setting the click listener for the card
            itemView.setOnClickListener(onCardClick);
            
            //TODO now set the views values
            title.setText("CARD NUM: " + new SecureRandom().nextInt(9));
        }

        /**
         * The onClick Listener for the card
         */
        private View.OnClickListener onCardClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardClickListener.cardClick(getAdapterPosition(), view);
            }
        };
    }

    @Override
    public CardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cardview, parent, false);

        return new CardsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardsViewHolder holder, int position) {
        Cards card = this.cards.get(position);
        //holder.title.setText("[BIND] " + holder.title.getText());
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    /**
     * The interface that handles the routing of the click action to the corresponding initiator(fragment)
     */
    public interface CardClickListener {

        /**
         * The onClick listener of the card itself
         *
         * @param position the current position of the RecyclerView to get the corresponding card data
         * @param view     the corresponding view that got the click action
         */
        void cardClick(int position, View view);

    }

    /**
     * to set the interface for the corresponding fragment
     *
     * @param cardClickListener the interface instance from the corresponding fragment
     */
    public void setListener(CardClickListener cardClickListener) {
        this.cardClickListener = cardClickListener;
    }

}
