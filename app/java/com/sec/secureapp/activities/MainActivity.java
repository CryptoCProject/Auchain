package com.sec.secureapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.view.View;
import android.widget.TextView;

import com.sec.secureapp.R;
import com.sec.secureapp.general.InfoMessage;
import com.sec.secureapp.general.T;

public class MainActivity extends BaseActivity {

    TextView textView;
    EditextReceiver edittextReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = (TextView) findViewById(R.id.main_textview);

        edittextReceiver = new EditextReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.sec.secureapp.MAIN_TEXTVIEW");
        registerReceiver(edittextReceiver,filter);

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
        switch (view.getId()) {
            case R.id.get_main_button: {
                new InfoMessage(this, T.MAIN, null).start();
                break;
            }
            case R.id.logout_main_button: {
                Intent setIntent = new Intent(this, LoginActivity.class);
                startActivity(setIntent);
                break;
            }
        }
    }

    class EditextReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			String temp = textView.getText() + "\n" + intent.getStringExtra("message");
            textView.setText(temp);
		}
	}

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
