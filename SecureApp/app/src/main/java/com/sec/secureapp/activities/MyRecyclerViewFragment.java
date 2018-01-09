package com.sec.secureapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.sec.secureapp.general.T;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressLint("ValidFragment")
public class MyRecyclerViewFragment extends Fragment {

    private String auctions = "";

    private View view;

    private boolean running;

    private static RecyclerView recyclerView;

    //variable to store auctions
    ArrayList<HashMap<String, String>> auctionList;


    public MyRecyclerViewFragment() {
    }

    public MyRecyclerViewFragment(boolean running, String auctions) {
        this.running = running;
        this.auctions = auctions;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recycler_view, container, false);

        // wait from server response with auctions
        auctionList = new ArrayList<>();

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
                mIntent.putExtra("title", auctionList.get(position).get("auction_id"));
                mIntent.putExtra("object", auctionList.get(position).get("object_name"));
                mIntent.putExtra("auctioneer", auctionList.get(position).get("auctioneer_id"));
                mIntent.putExtra("price", auctionList.get(position).get("object_price"));
                mIntent.putExtra("running", running);
                T.VIEW_TOAST(getContext(), "" + running, Toast.LENGTH_SHORT);

                startActivity(mIntent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        try {
            jsonToObject(auctions);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyAdapter adapter = new MyAdapter(getActivity(), auctionList);
        recyclerView.setAdapter(adapter);// set adapter on recyclerview
    }

    // get information from json received from server
    public void jsonToObject(String auctions) throws JSONException {
        JSONArray jArray = new JSONArray(auctions);
        for (int i = 0; i < jArray.length(); i++) {

            JSONObject jObject = jArray.getJSONObject(i);

            HashMap<String, String> auction = new HashMap<>();

            auction.put("auction_id", jObject.getString("a"));
            auction.put("auctioneer_id", jObject.getString("i"));
            auction.put("auction_type", jObject.getString("t"));
            //auction.put("object_name", jObject.getString("n")); //uncomment for name
            auction.put("object_price", jObject.getString("p"));

            auctionList.add(auction);
        }
    }
}
