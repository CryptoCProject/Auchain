package com.sec.secureapp.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sec.secureapp.R;
import com.sec.secureapp.databinding.RecyclerViewBinding;
import com.sec.secureapp.general.InfoMessage;
import com.sec.secureapp.general.T;
import com.sec.secureapp.general.UserInfo;

import java.util.Random;

@SuppressLint("ValidFragment")
public class MyRecyclerViewFragment extends Fragment {

    RecyclerViewBinding binding;

    BroadcastReceiver broadcastReceiver;

    private View view;

    private String title;//String for tab title
    private boolean running;

    private static RecyclerView recyclerView;

    // mockup data
    String[] auctionTitles = new String[16];
    String[] auctionObject = new String[16];
    String[] auctionAuctioneer = new String[16];
    double[] auctionPrice = new double[16];

    public MyRecyclerViewFragment(){}

    public MyRecyclerViewFragment(boolean running) {
        this.running = running;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recycler_view, container, false);

        IntentFilter intentFilter = new IntentFilter(
                "com.sec.secureapp.GET_AUCTIONS");

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String[] info = intent.getStringArrayExtra("getAuctions");
                System.out.print(info.toString());
            }
        };

        getActivity().registerReceiver(broadcastReceiver, intentFilter);

        setRecyclerView();

        return view;

    }

    //Setting recycler view
    private void setRecyclerView() {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//Linear Items

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent mIntent = new Intent(getActivity(), AuctionDetailsActivity.class);
                mIntent.putExtra("title", auctionTitles[position]);
                mIntent.putExtra("object", auctionObject[position]);
                mIntent.putExtra("auctioneer", auctionAuctioneer[position]);
                mIntent.putExtra("price", auctionPrice[position]);
                mIntent.putExtra("running", running);
                T.VIEW_TOAST(getContext(), ""+running, Toast.LENGTH_SHORT);
                startActivity(mIntent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        //TODO: Maybe move to AsyncTask if fetching is too slow
        //new MyAsyncTask(getActivity(), running).execute();

        FetchData(running);

        MyAdapter adapter = new MyAdapter(getActivity(), auctionTitles, auctionObject, auctionAuctioneer, auctionPrice);
        recyclerView.setAdapter(adapter);// set adapter on recyclerview

    }

    /**
     * auctioneer id
     * auction type
     * object name
     * price
     *
     * @param running
     */
    private void FetchData(boolean running){
        Random r = new Random();
        new InfoMessage(getContext(), T.AUCTIONS, new UserInfo("panagiotis", null, null, null, null)).start();

        for (int i = 0; i < 16; i++) {
            auctionTitles[i] = ( running ? "Running_Auction_" + i : "Open_Auction_"+i);
            auctionObject[i] = "Object_" + i;
            auctionAuctioneer[i] = "Auctioneer_" + i;
            auctionPrice[i] = r.nextDouble() * r.nextDouble() * 1000;
        }
    }

    // AsyncTask
    class MyAsyncTask extends AsyncTask<String, Void, Void> {

        private ProgressDialog progressDialog;
        Boolean running;

        public MyAsyncTask(Activity activity, boolean running) {
            progressDialog = new ProgressDialog(activity);
            this.running = running;
        }

        protected void onPreExecute() {
            this.progressDialog.setMessage("Downloading your data...");
            //this.progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {

            //TODO: Method To Fetch Data

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Random r = new Random();

            for (int i = 0; i < 16; i++) {
                auctionTitles[i] = ( running ? "Running_Auction_" + i : "Open_Auction_"+i);
                auctionObject[i] = "Object_" + i;
                auctionAuctioneer[i] = "Auctioneer_" + i;
                auctionPrice[i] = r.nextDouble() * r.nextDouble() * 1000;
            }

            return null;
        }

        protected void onPostExecute(Void v) {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            MyAdapter adapter = new MyAdapter(getActivity(), auctionTitles, auctionObject, auctionAuctioneer, auctionPrice);
            recyclerView.setAdapter(adapter);// set adapter on recyclerview
        }
    }
}
