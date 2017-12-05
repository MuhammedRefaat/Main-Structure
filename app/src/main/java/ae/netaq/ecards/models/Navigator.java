package ae.netaq.ecards.models;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;

import ae.netaq.ecards.R;
import ae.netaq.ecards.views.activities.MainActivity;
import ae.netaq.ecards.views.fragments.AvailableCardsFragment;
import ae.netaq.ecards.views.fragments.CardView;
import ae.netaq.ecards.views.fragments.ContactUS;
import ae.netaq.ecards.views.fragments.CustomizedCard;
import ae.netaq.ecards.views.fragments.MyCardsFragment;

/**
 * Created by M.Refaat on 11/29/2017.
 * -----------------------------
 * The model class which is resp|onsible for Navigating between the different fragments
 */

public class Navigator {

    private MainActivity activity;
    private Fragment fragment;

    public Navigator(Context activity) {
        this.activity = (MainActivity) activity;
    }

    /**
     * Available Cards page
     */
    public void goToAvailableCards() {
        activity.tabs.setVisibility(View.VISIBLE);
        activity.pager.setCurrentItem(0);
        fragment = new AvailableCardsFragment();
        launchFragment();
    }

    /**
     * My Cards page
     */
    public void goToMyCards() {
        activity.tabs.setVisibility(View.VISIBLE);
        activity.pager.setCurrentItem(1);
        fragment = new MyCardsFragment();
        launchFragment();
    }

    /**
     * The card display fragment (to customize/edit)
     *
     * @param bundle the Bundle that contains the required data to be send along with the fragment
     */
    public void goToCardView(Bundle bundle) {
        activity.tabs.setVisibility(View.GONE);
        fragment = new CardView();
        fragment.setArguments(bundle);
        launchFragment();
    }

    /**
     * The customization inputs page (to allow the user enter his inputs)
     *
     * @param bundle the Bundle that contains the required data to be send along with the fragment
     */
    public void goToCustomizationPage(Bundle bundle) {
        activity.tabs.setVisibility(View.GONE);
        fragment = new CardView();
        fragment.setArguments(bundle);
        launchFragment();
    }

    /**
     * the resulted card after customization
     *
     * @param bundle the Bundle that contains the required data to be send along with the fragment
     */
    public void goToCustomizedCard(Bundle bundle) {
        activity.tabs.setVisibility(View.GONE);
        fragment = new CustomizedCard();
        fragment.setArguments(bundle);
        launchFragment();
    }

    /**
     * The Contact Us page
     */
    public void goToContactUs() {
        activity.tabs.setVisibility(View.GONE);
        fragment = new ContactUS();
        launchFragment();
    }

    /**
     * The method which is responsible for logging-out from the application
     */
    public void logout() {

    }

    /**
     * The method which is responsible for the Hard Word for launching the fragment
     */
    private void launchFragment() {
        // get the corresponding holder layout
        activity.getSupportFragmentManager().popBackStack();
        FrameLayout frameLayout = (FrameLayout) activity.findViewById(R.id.content);
        frameLayout.setVisibility(View.VISIBLE);

        // set Animations
        int toEnter = R.anim.enter_from_left;
        int toExit = R.anim.exit_to_right;
        if (activity.getResources().getBoolean(R.bool.is_arabic_lang)) {
            toEnter = R.anim.enter_from_right;
            toExit = R.anim.exit_to_left;
        }

        // do the fragment replacement
        activity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(toEnter, toExit)
                .replace(R.id.content, fragment)
                .addToBackStack(null)
                .commit();
    }

}
