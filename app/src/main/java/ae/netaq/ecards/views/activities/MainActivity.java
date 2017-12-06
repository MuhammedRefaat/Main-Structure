package ae.netaq.ecards.views.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import ae.netaq.ecards.R;
import ae.netaq.ecards.adapters.ViewPagerAdapter;
import ae.netaq.ecards.misc.Utils;
import ae.netaq.ecards.models.Navigator;
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
    public TabLayout tabs;
    @BindView(R.id.pager)
    public ViewPager pager;

    ViewPagerAdapter adapter;
    int numbOfTabs = 2;
    CharSequence Titles[] = new CharSequence[numbOfTabs];

    private RecyclerView cardsRecyclerView;

    Navigator navigator;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        navigator = new Navigator(MainActivity.this);

        initSlidingTab();

        initNavigationDrawer();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START | GravityCompat.END)) {
            drawer.closeDrawers();
            return;
        } else {
            getSupportFragmentManager().popBackStack();
            if (doubleBackToExitPressedOnce && tabs.getVisibility() == View.VISIBLE) {
                super.onBackPressed();
                return;
            } else if (tabs.getVisibility() == View.GONE) {
                tabs.setVisibility(View.VISIBLE);
            } else {
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, getResources().getString(R.string.back_exit), Toast.LENGTH_SHORT).show();
            }
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.available_cards) {
            navigator.goToAvailableCards();
        } else if (id == R.id.my_cards) {
            navigator.goToMyCards();
        } else if (id == R.id.contact_us) {
            navigator.goToContactUs();
        } else if (id == R.id.logout) {
            navigator.logout();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        // Setting the ViewPager to work with the SlidingTabsLayout
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
