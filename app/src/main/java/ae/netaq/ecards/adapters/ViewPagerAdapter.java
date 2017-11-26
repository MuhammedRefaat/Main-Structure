package ae.netaq.ecards.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ae.netaq.ecards.views.fragments.AvailableCardsFragment;
import ae.netaq.ecards.views.fragments.MyCardsFragment;

/**
 * Created by M.Refaat on 11/23/2017.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    FragmentManager manager;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.manager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) // if the position is 0 we are returning the First tab
            return new AvailableCardsFragment();
        else if (position == 1) // if the position is 1 we are returning the Second tab
            return new MyCardsFragment();
        else // by default, return the first tab
            return new AvailableCardsFragment();
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip
    @Override
    public int getCount() {
        return NumbOfTabs;
    }

    @Override
    public int getItemPosition(Object mObject) {
        return POSITION_NONE;
    }


}
