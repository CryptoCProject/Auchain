package com.sec.secureapp.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Toast;

import com.sec.secureapp.R;
import com.sec.secureapp.databinding.ActivityAuctionDetailsBinding;
import com.sec.secureapp.general.T;

public class AuctionDetailsActivity extends BaseActivity {

    ActivityAuctionDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auction_details);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ActionBar actionBar = getSupportActionBar();
            getSupportActionBar().setTitle(bundle.getString("title"));

            binding.auctionObject.setText(bundle.getString("object"));
            binding.auctionAuctioneer.setText(bundle.getString("auctioneer"));
            binding.auctionPrice.setText(getString(R.string.auction_price, bundle.getDouble("price")));

            binding.auctionParticipate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    participate();
                }
            });
        }
    }

    private void participate() {
        T.VIEW_TOAST(getApplicationContext(), "Participate Confirmed", Toast.LENGTH_SHORT);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_auction_details;
    }

    @Override
    protected void clickButtons(View view) {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
