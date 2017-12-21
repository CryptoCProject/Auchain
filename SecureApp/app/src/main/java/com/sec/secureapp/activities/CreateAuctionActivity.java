package com.sec.secureapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sec.secureapp.R;

public class CreateAuctionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_create_auction;
    }

    @Override
    protected void clickButtons(View view) {

    }

    @Override
    public void onBackPressed() {
        Intent mIntent = new Intent(CreateAuctionActivity.this, MainActivity.class);
        startActivity(mIntent);
    }
}
