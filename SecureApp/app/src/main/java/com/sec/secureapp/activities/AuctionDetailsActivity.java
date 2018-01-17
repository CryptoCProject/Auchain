package com.sec.secureapp.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.sec.secureapp.R;
import com.sec.secureapp.databinding.ActivityAuctionDetailsBinding;
import com.sec.secureapp.general.InfoMessage;
import com.sec.secureapp.general.ParticipationInfo;
import com.sec.secureapp.general.T;

public class AuctionDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityAuctionDetailsBinding binding;

    private int auction_id;

    private boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auction_details);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            auction_id = Integer.parseInt(bundle.getString("title"));
            getSupportActionBar().setTitle(bundle.getString("title"));

            binding.auctionObject.setText(bundle.getString("object"));
            binding.auctionAuctioneer.setText(bundle.getString("auctioneer"));
            binding.auctionPrice.setText(getString(R.string.auction_price, bundle.getDouble("price")));
            running = bundle.getBoolean("running");
            binding.auctionAction.setText((running ? "Bid" : "Participate"));

            binding.auctionAction.setOnClickListener(this);
        }
    }

    // button action when in a running auction
    private void bid() {
        T.VIEW_TOAST(getApplicationContext(), "Bid Confirmed", Toast.LENGTH_SHORT);
    }

    // button action when in an open auction
    private void participate() {
        T.VIEW_TOAST(getApplicationContext(), "Participate Confirmed", Toast.LENGTH_SHORT);
        new InfoMessage(this, T.PARTICIPATE, new ParticipationInfo(T.USER_ID, auction_id)).start();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.auction_action:
                if (!running)
                    participate();
                else
                    bid();
                break;
            default:
                break;
        }
    }
}
