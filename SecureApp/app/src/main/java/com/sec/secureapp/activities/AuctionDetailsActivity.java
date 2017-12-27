package com.sec.secureapp.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.sec.secureapp.R;
import com.sec.secureapp.databinding.ActivityAuctionDetailsBinding;
import com.sec.secureapp.general.T;

public class AuctionDetailsActivity extends AppCompatActivity implements View.OnClickListener {

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
            binding.auctionParticipate.setText((bundle.getBoolean("running") ? "Bid" : "Participate"));

            binding.auctionParticipate.setOnClickListener(this);
        }
    }

    private void participate() {
        T.VIEW_TOAST(getApplicationContext(), "Participate Confirmed", Toast.LENGTH_SHORT);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.auction_participate:
                participate();
                break;
        }
    }
}
