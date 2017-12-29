package com.sec.secureapp.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sec.secureapp.R;
import com.sec.secureapp.databinding.ActivityMainBinding;
import com.sec.secureapp.general.InfoMessage;
import com.sec.secureapp.general.T;
import com.sec.secureapp.general.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;

    //variable to store auctions
    ArrayList<HashMap<String, String>> auctionList;

    // wait for answer from server with data
    AuctionReceiver runningAuctionReceiver;
    AuctionReceiver openAuctionReceiver;

    // string with json got from server
    String runningAuctions = "";
    String openAuctions = "";

    // the tab titles
    String[] tabTitles = {"OPEN", "RUNNING"};

    ViewPagerAdapter adapter;

    // progress dialog showing until data is fetched
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // send message to server for open and running auctions
        new InfoMessage(this, T.OPEN_AUCTIONS, new UserInfo("angelos", null, null, null, null)).start();
        new InfoMessage(this, T.RUNNING_AUCTIONS, new UserInfo("angelos", null, null, null, null)).start();

        // show dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Downloading your data...");
        progressDialog.show();

        // filter used for receiver
        IntentFilter filter = new IntentFilter();

        // create a receiver for running auctions and wait response from server
        openAuctionReceiver = new AuctionReceiver();
        filter.addAction("com.sec.secureapp.OPEN_AUCTIONS");
        registerReceiver(openAuctionReceiver, filter);

        // create a receiver for running auctions and wait response from server
        runningAuctionReceiver = new AuctionReceiver();
        filter.addAction("com.sec.secureapp.RUNNING_AUCTIONS");
        registerReceiver(runningAuctionReceiver, filter);

        // change actionbar title
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle("Auctions");

        // TODO: Fetch data on tab change
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // filter used for receiver
                switch (tab.getPosition()){
                    case 0:
                        T.VIEW_TOAST(getApplicationContext(), "OPEN", Toast.LENGTH_SHORT);
                        //new InfoMessage(getApplicationContext(), T.OPEN_AUCTIONS, new UserInfo("angelos", null, null, null, null)).start();
                        break;
                    case 1:
                        T.VIEW_TOAST(getApplicationContext(), "RUNNING", Toast.LENGTH_SHORT);
                        //new InfoMessage(getApplicationContext(), T.RUNNING_AUCTIONS, new UserInfo("angelos", null, null, null, null)).start();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.fab.setOnClickListener(this);
    }

    //Setting View Pager
    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), tabTitles, tabTitles.length);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Intent mIntent = new Intent(MainActivity.this, CreateAuctionActivity.class);
                startActivity(mIntent);
                break;
        }
    }

    //TODO: Create new java file
    //View Pager fragments setting adapter class
    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        String[] tabTitles;
        int numOfTabs;

        public ViewPagerAdapter(FragmentManager manager, String[] tabTitles, int numOfTabs) {
            super(manager);

            this.tabTitles = tabTitles;
            this.numOfTabs = numOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {// if the position is 0 we are returning the First tab
                case 0:
                    MyRecyclerViewFragment tab1 = new MyRecyclerViewFragment(false, openAuctions);
                    return tab1;
                case 1:
                    MyRecyclerViewFragment tab2 = new MyRecyclerViewFragment(true, runningAuctions);
                    return tab2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numOfTabs;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

    // options menu top right of screen
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent;

        switch (item.getItemId()) {
            case R.id.action_profile:
                intent = new Intent(this, ProfileActivity.class);
                this.startActivity(intent);
                break;
            case R.id.action_logout:
                intent = new Intent(this, LoginActivity.class);
                this.startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(runningAuctionReceiver);
        unregisterReceiver(openAuctionReceiver);
        super.onPause();
        finish();
    }

    //TODO: Put it on a new java file
    class AuctionReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            boolean running = intent.getBooleanExtra("running", false);

            if (running) {
                runningAuctions = intent.getStringExtra("getAuctions");

                setupViewPager(binding.viewPager);
                binding.tabLayout.setupWithViewPager(binding.viewPager);//setting tab over viewpager
            } else {
                openAuctions = intent.getStringExtra("getAuctions");
            }

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
}
