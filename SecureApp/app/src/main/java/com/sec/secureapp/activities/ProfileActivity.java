package com.sec.secureapp.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.sec.secureapp.R;
import com.sec.secureapp.databinding.ActivityProfileBinding;
import com.sec.secureapp.general.T;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        binding.profileShowAuctions.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_show_auctions:
                T.VIEW_TOAST(getApplicationContext(), "Show Previous Auctions", Toast.LENGTH_LONG);
                break;
            default:
                break;
        }
    }
}
