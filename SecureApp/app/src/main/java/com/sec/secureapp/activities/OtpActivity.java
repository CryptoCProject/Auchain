package com.sec.secureapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.sec.secureapp.R;
import com.sec.secureapp.general.InfoMessage;
import com.sec.secureapp.general.T;
import com.sec.secureapp.general.UserInfo;

public class OtpActivity extends BaseActivity {

    EditText otpText;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        name = getIntent().getStringExtra("name");
        otpText = (EditText) findViewById(R.id.otp);

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
