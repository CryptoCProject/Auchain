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
import com.sec.secureapp.general.ParticipatedAuctions;
import com.sec.secureapp.general.ParticipationInfo;
import com.sec.secureapp.general.T;

public class AuctionDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityAuctionDetailsBinding binding;

    private int auction_id;
    private String auctions = "";
    private double price = 0;

    //0: open, 1: running, 2:finished
    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auction_details);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            auction_id = Integer.parseInt(bundle.getString("title"));
            getSupportActionBar().setTitle("Auction id: " + bundle.getString("title"));

            binding.auctionObject.setText(bundle.getString("object"));
            binding.auctionAuctioneer.setText(bundle.getString("auctioneer"));
            System.out.println("****BIDS "+bundle.getString("title")+" "+ParticipatedAuctions.bids.get(bundle.getString("title")));
            price = Double.parseDouble(ParticipatedAuctions.bids.get(bundle.getString("title")));
            binding.auctionPrice.setText(getString(R.string.auction_price, price));
            type = bundle.getInt("running");
            binding.auctionAction.setText((type == 1 ? "Bid" : "Participate"));

            auctions = bundle.getString("auctions");

            // if finished
            if (type == 2) {
                binding.auctionAction.setVisibility(View.GONE);
            }

            if (type == 0 && bundle.getString("participated").equals("1")) {
                binding.auctionAction.setText("Already Participated");
                binding.auctionAction.setEnabled(false);
            }

            binding.auctionAction.setOnClickListener(this);
        }
    }

    // button action when in a running auction
    private void bid() {
        new InfoMessage(this, T.BID, new ParticipationInfo(T.USER_ID, auction_id, price + price * 0.1)).start();
        T.VIEW_TOAST(getApplicationContext(), "Bid Confirmed", Toast.LENGTH_SHORT);
    }

    // button action when in an open auction
    private void participate() {
        new InfoMessage(this, T.PARTICIPATE, new ParticipationInfo(T.USER_ID, auction_id, 0)).start();
        binding.auctionAction.setText("Already Participated");
        binding.auctionAction.setEnabled(false);

    }

    @Override
    public void onBackPressed() {
        // determine which was the previous activity
        if (type == 2) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("auctions", auctions);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.auction_action:
                if (type == 0)
                    participate();
                else
                    bid();
                break;
            default:
                break;
        }
    }
}
