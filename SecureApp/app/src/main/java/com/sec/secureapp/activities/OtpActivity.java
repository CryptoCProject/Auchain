package com.sec.secureapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.sec.secureapp.R;
import com.sec.secureapp.general.InfoMessage;
import com.sec.secureapp.general.T;
import com.sec.secureapp.general.UserInfo;

public class OtpActivity extends BaseActivity {

    EditText otpText;
    String name;

    Button otp_cancel, otp_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        name = getIntent().getStringExtra("name");
        otpText = (EditText) findViewById(R.id.otp);

        otp_cancel = (Button) findViewById(R.id.cancel_otp_button);
        otp_continue = (Button) findViewById(R.id.continue_otp_button);

        otp_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setIntent = new Intent(OtpActivity.this, LoginActivity.class);
                startActivity(setIntent);
                finish();
            }
        });

        otp_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = otpText.getText().toString();
                new InfoMessage(OtpActivity.this, T.OTP,new UserInfo(name, null, null, null, otp)).start();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_otp;
    }

    @Override
    protected void clickButtons(View view) {
        switch (view.getId()) {
            case R.id.continue_otp_button: {
                String otp = otpText.getText().toString();
                new InfoMessage(this, T.OTP,new UserInfo(name, null, null, null, otp)).start();
                break;
            }
            case  R.id.cancel_otp_button: {
                Intent setIntent = new Intent(this, LoginActivity.class);
                startActivity(setIntent);
                finish();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, LoginActivity.class);
        startActivity(setIntent);
        finish();
    }

}
