package com.sec.secureapp.general;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.sec.secureapp.client.SSLclient;
import com.sec.secureapp.database.DB;
import com.sec.secureapp.security.Keys;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class T {

    public static DB DB;

    public static String SERVER_IP = "192.168.1.2";
    public static int SERVER_PORT = 54321;
    public static String CERT_NAME = "sslsec.bks";

    public static String SUCCESS = "1";
    public static String NOT_SUCCESS = "2";
    public static String NAME_EXIST = "3";
    public static String WRONG_CREDENTIALS = "4";
    public static String WRONG_OTP = "5";
    public static String OTP_ERROR = "6";

    public static String SIGN_UP = " a";
    public static String SIGN_UP_CONFIRM = " b";

    public static String LOG_IN = " c";
    public static String LOG_IN_CONFIRM = " d";

    public static String OTP = " e";
    public static String OTP_CONFIRM = " f";

    public static String MAIN = " g";
    public static String MAIN_CONFIRM = " h";

    public static String SIGN_UP_MESSAGE = null;
    public static String LOG_IN_MESSAGE = null;
    public static String OTP_MESSAGE = null;
    public static String MAIN_MESSAGE = null;

    public static void VIEW_TOAST(final Context con, final String toast, final int dur) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                Toast.makeText(con, toast, dur).show();
            }
        });
    }

    public static void SLEEP(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject getJson (String ... strings) {
        JSONObject jo = new JSONObject();
        HashMap <String, String> map = new HashMap<>();
        int i = 0;
        try {
            for(;;) {
                try {
                    map.put(strings[i], strings[i + 1]);
                    i += 2;
                } catch (ArrayIndexOutOfBoundsException ex) {
                    break;
                }
            }
            for (String k : map.keySet()) {
                jo.put(k, map.get(k));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return jo;
    }

}
