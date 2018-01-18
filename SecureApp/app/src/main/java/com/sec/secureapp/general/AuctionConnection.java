package com.sec.secureapp.general;

import android.content.Context;
import android.widget.Toast;

import com.sec.secureapp.client.SSLclient;
import com.sec.secureapp.security.Hashing;

public class AuctionConnection extends Thread {

    private Context context;
    private String auction_id;
    private SSLclient client;
    private Hashing hash;

    public AuctionConnection(Context context, String auction_id) {
        this.context = context;
        this.auction_id = auction_id;
        this.hash = new Hashing();
    }

    @Override
    public void run() {
        client = new SSLclient(this.context);
        client.start();
        client.sendMessage(T.CONNECT_AUCTION + this.auction_id);
        for (;;){
            T.SLEEP(500);
            if (T.CONNECT_AUCTION_MESSAGE != null) {
                if (T.CONNECT_AUCTION_MESSAGE.equals(T.AUCTION_WAITING)) {
                    T.VIEW_TOAST(this.context, "Waiting for auction start.", Toast.LENGTH_LONG);
                }
                else if (T.CONNECT_AUCTION_MESSAGE.equals(T.AUCTION_RUNNING)) {
                    T.VIEW_TOAST(this.context, "Auction started!!!", Toast.LENGTH_LONG);
                }
                else if (T.CONNECT_AUCTION_MESSAGE.equals(T.AUCTION_FINISHED)) {
                    T.VIEW_TOAST(this.context, "Auction finished!!!", Toast.LENGTH_LONG);
                    break;
                }
                else if (T.CONNECT_AUCTION_MESSAGE.equals(T.NOT_SUCCESS)) {
                    T.VIEW_TOAST(this.context, "Server not responding . Try again please.", Toast.LENGTH_LONG);
                    break;
                }
                T.CONNECT_AUCTION_MESSAGE = null;
            }
        }
        this.client.closeCrap();
    }

}
