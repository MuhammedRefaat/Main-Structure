package ae.netaq.ecards.views.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ae.netaq.ecards.R;
import ae.netaq.ecards.adapters.ViewPagerAdapter;
import ae.netaq.ecards.misc.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.pager)
    ViewPager pager;

    ViewPagerAdapter adapter;
    int numbOfTabs = 2;
    CharSequence Titles[] = new CharSequence[numbOfTabs];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        initSlidingTab();

        initNavigationDrawer();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.available_cards) {

        } else if (id == R.id.my_cards) {

        } else if (id == R.id.contact_us) {

        } else if (id == R.id.logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * The method which is responsible for initiating and defining all of the required
     * views & arguments for the sliding tab
     */
    private void initSlidingTab() {

        Titles[0] = getResources().getString(R.string.first_tab);
        Titles[1] = getResources().getString(R.string.second_tab);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, numbOfTabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);

        Utils.setViewAccordingToLocale(this, pager);

        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(1);
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // TODO what to do ?!!
            }
        });

        tabs.setTabTextColors(Utils.getColor(this, android.R.color.white), Utils.getColor(this, android.R.color.holo_orange_light));
        tabs.setTabMode(TabLayout.MODE_FIXED);
        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setupWithViewPager(pager);

    }

    /**
     * The method which is responsible for initiating and defining the required
     * arguments for the Navigation Drawer
     */
    private void initNavigationDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }
}
