package com.sec.secureapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sec.secureapp.R;
import com.sec.secureapp.databinding.ActivityMainBinding;
import com.sec.secureapp.general.T;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    //TODO: Make it Tabbed Activity (Open/Running Auctions)

    ActivityMainBinding binding;

    private static ViewPager viewPager;
    private static TabLayout tabLayout;

    EditextReceiver edittextReceiver;

    // mockup data
    String[] auctionTitles = new String[16];
    String[] auctionObject = new String[16];
    String[] auctionAuctioneer = new String[16];
    double[] auctionPrice = new double[16];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutResourceId());

        // change actionbar title
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle("Auctions");

        setupViewPager(binding.viewPager);

        binding.tabLayout.setupWithViewPager(binding.viewPager);//setting tab over viewpager

        //Implementing tab selected listener over tablayout
        binding.tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());//setting current selected item over viewpager
                switch (tab.getPosition()) {
                    case 0:
                        T.VIEW_TOAST(getApplicationContext(), "Tab 1", Toast.LENGTH_SHORT);
                        break;
                    case 1:
                        T.VIEW_TOAST(getApplicationContext(), "Tab 2", Toast.LENGTH_SHORT);
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

        binding.fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent mIntent = new Intent(MainActivity.this, CreateAuctionActivity.class);
                startActivity(mIntent);
            }
        });

        edittextReceiver = new EditextReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.sec.secureapp.MAIN_TEXTVIEW");
        registerReceiver(edittextReceiver, filter);

    }

    //Setting View Pager
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new MyRecyclerViewFragment(false), false);
        adapter.addFrag(new MyRecyclerViewFragment(true), true);
        viewPager.setAdapter(adapter);
    }

    //View Pager fragments setting adapter class
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();//fragment arraylist
        private final List<String> mFragmentTitleList = new ArrayList<>();//title arraylist

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }


        //adding fragments and title method
        public void addFrag(Fragment fragment, boolean running) {
            mFragmentList.add(fragment);
            String title = ( running ? "Running" : "Open");
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

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
        unregisterReceiver(edittextReceiver);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void clickButtons(View view) {

    }

    class EditextReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            /*String temp = textView.getText() + "\n" + intent.getStringExtra("message");
            textView.setText(temp);*/
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
