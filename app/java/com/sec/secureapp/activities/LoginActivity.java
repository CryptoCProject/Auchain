package com.sec.secureapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.sec.secureapp.R;
import com.sec.secureapp.general.InfoMessage;
import com.sec.secureapp.general.T;
import com.sec.secureapp.general.UserInfo;

public class LoginActivity extends BaseActivity {

    EditText name, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        name = (EditText) findViewById(R.id.username_login);
        password = (EditText) findViewById(R.id.password_login);

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login;
    }

    @Override
    protected void clickButtons(View view) {
        switch (view.getId()) {
            case R.id.register_login_button: {
                Intent intent = new Intent(this, SignupActivity.class);
                this.startActivity(intent);
                finish();
                break;
            }
            case  R.id.signin_login_button: {
                String n = name.getText().toString();
                String p = password.getText().toString();
                String otpId = String.valueOf(System.currentTimeMillis());
                new InfoMessage(this, T.LOG_IN, new UserInfo(n, p, null, otpId, null)).start();
                break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

}
