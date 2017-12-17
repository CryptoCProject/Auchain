package com.sec.secureapp.general;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.sec.secureapp.activities.*;
import com.sec.secureapp.client.Client;
import com.sec.secureapp.database.DB;
import com.sec.secureapp.security.Hashing;

public class InfoMessage extends Thread {

    private Context context;
    private String _case;
    private Object messageInfo;
    private Client client;
    private Hashing hash;

    public InfoMessage(Context context, String _case, Object messageInfo) {
        this.context = context;
        this._case = _case;
        this.messageInfo = messageInfo;
        this.hash = new Hashing();
    }

    @Override
    public void run() {
        client = new Client(this.context);
        client.start();
        if (this._case.equals(T.SIGN_UP)) {
            signup((UserInfo) messageInfo);
        }
        else if (this._case.equals(T.LOG_IN)) {
            login((UserInfo) messageInfo);
        }
        else if (this._case.equals(T.OTP)) {
            otp((UserInfo) messageInfo);
        }
        else if (this._case.equals(T.MAIN)) {
            main(null);
        }
        client.closeCrap();
    }

    private void signup(UserInfo ui) {
        client.sendMessage(T.SIGN_UP + T.getJson(new String[]{"u", ui.getName(), "p", hash.getHashedCode(ui.getPwd()), "e", ui.getEmail()}).toString());
        int counter = 0;
        for (;;){
            T.SLEEP(100);
            counter++;
            if (T.SIGN_UP_MESSAGE != null) {
                if (T.SIGN_UP_MESSAGE.equals(T.NOT_SUCCESS)) {
                    T.VIEW_TOAST(this.context, "Unsuccessful registration. Something went wrong with the server. Try again please.", Toast.LENGTH_LONG);
                }
                else if (T.SIGN_UP_MESSAGE.equals(T.NAME_EXIST)) {
                    T.VIEW_TOAST(this.context, "Unsuccessful registration. Try an other user name.", Toast.LENGTH_LONG);
                }
                else {
                    String prkey = T.SIGN_UP_MESSAGE;
                    DB db = new DB(context);
                    db.createTables();
                    db.initializeValues();
                    db.setMyPrivateKey(prkey.getBytes());
                    T.SIGN_UP_MESSAGE = null;
                    client.sendMessage(T.PRIVATE_KEY + T.PRIVATE_KEY_ACK);
                    counter = 0;
                    for (;;) {
                        T.SLEEP(100);
                        counter++;
                        if (T.SIGN_UP_MESSAGE != null) {
                            if (T.SIGN_UP_MESSAGE.equals(T.SUCCESS)) {
                                T.VIEW_TOAST(this.context, "Successful registration. Log in please.", Toast.LENGTH_LONG);
                                Intent intent = new Intent(this.context, LoginActivity.class);
                                this.context.startActivity(intent);
                            } else if (T.SIGN_UP_MESSAGE.equals(T.NOT_SUCCESS)) {
                                T.VIEW_TOAST(this.context, "Unsuccessful registration. Something went wrong with the server. Try again please.", Toast.LENGTH_LONG);
                            }
                            break;
                        }
                        else if (counter == 50) {
                            T.VIEW_TOAST(this.context, "Server not responding. Try again please.", Toast.LENGTH_LONG);
                            break;
                        }
                    }
                }
                break;
            }
            else if (counter == 50) {
                T.VIEW_TOAST(this.context, "Server not responding. Try again please.", Toast.LENGTH_LONG);
                break;
            }
        }
        T.SIGN_UP_MESSAGE = null;
    }

    private void login(UserInfo ui) {
        client.sendMessage(T.LOG_IN + T.getJson(new String[]{"u", ui.getName(), "p", hash.getHashedCode(ui.getPwd()), "o", ui.getSalt()}).toString());
        int counter = 0;
        for (;;){
            T.SLEEP(100);
            counter++;
            if (T.LOG_IN_MESSAGE != null) {
                if (T.LOG_IN_MESSAGE.equals(T.SUCCESS)) {
                    Intent intent = new Intent(this.context, OtpActivity.class);
                    intent.putExtra("name", ui.getName());
                    this.context.startActivity(intent);
                }
                else if (T.LOG_IN_MESSAGE.equals(T.NOT_SUCCESS)) {
                    T.VIEW_TOAST(this.context, "Something went wrong with the server. Try again please.", Toast.LENGTH_LONG);
                }
                else if (T.LOG_IN_MESSAGE.equals(T.WRONG_CREDENTIALS)) {
                    T.VIEW_TOAST(this.context, "Wrong user name or password. Try again please.", Toast.LENGTH_LONG);
                }
                break;
            }
            else if (counter == 80) {
                T.VIEW_TOAST(this.context, "Server not responding. Try again please.", Toast.LENGTH_LONG);
                break;
            }
        }
        T.LOG_IN_MESSAGE = null;
    }

    private void otp(UserInfo ui) {
        client.sendMessage(T.OTP + T.getJson(new String[]{"u", ui.getName(), "o", ui.getOtp()}).toString());
        int counter = 0;
        for (;;){
            T.SLEEP(100);
            counter++;
            if (T.OTP_MESSAGE != null) {
                if (T.OTP_MESSAGE.equals(T.NOT_SUCCESS)) {
                    T.VIEW_TOAST(this.context, "Something went wrong with the server. Try again please.", Toast.LENGTH_LONG);
                }
                else if (T.OTP_MESSAGE.equals(T.WRONG_OTP)) {
                    T.VIEW_TOAST(this.context, "Wrong OTP. Try again please.", Toast.LENGTH_LONG);
                }
                else if (T.OTP_MESSAGE.equals(T.OTP_ERROR)) {
                    T.VIEW_TOAST(this.context, "You tried 3 times or time elapsed. Log in again.", Toast.LENGTH_LONG);
                    Intent intent = new Intent(this.context, LoginActivity.class);
                    this.context.startActivity(intent);
                }
                else if (T.OTP_MESSAGE.equals(T.SUCCESS)) {
                    T.VIEW_TOAST(this.context, "Successful otp", Toast.LENGTH_LONG);
                    Intent intent = new Intent(this.context, MainActivity.class);
                    this.context.startActivity(intent);
                }
                break;
            }
            else if (counter == 50) {
                T.VIEW_TOAST(this.context, "Server not responding. Try again please.", Toast.LENGTH_LONG);
                break;
            }
        }
        T.OTP_MESSAGE = null;
    }

    private void main(Object obj) {
        client.sendMessage(T.MAIN);
        int counter = 0;
        for (;;){
            T.SLEEP(100);
            counter++;
            if (T.MAIN_MESSAGE != null) {
                Intent i = new  Intent("com.sec.secureapp.MAIN_TEXTVIEW");
                i.putExtra("message", T.MAIN_MESSAGE);
                context.sendBroadcast(i);
                break;
            }
            else if (counter == 50) {
                T.VIEW_TOAST(this.context, "Server not responding. Try again please.", Toast.LENGTH_LONG);
                break;
            }
        }
        T.MAIN_MESSAGE = null;
    }

}
