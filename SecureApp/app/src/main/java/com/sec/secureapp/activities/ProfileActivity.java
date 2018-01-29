package com.sec.secureapp.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sec.secureapp.R;
import com.sec.secureapp.databinding.ActivityProfileBinding;
import com.sec.secureapp.general.T;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityProfileBinding binding;

    private String auctions = "";
    //variable to store auctions
    ArrayList<HashMap<String, String>> auctionList;

    String[] auctionIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        getSupportActionBar().setTitle("My Profile");

        auctionList = new ArrayList<>();

        // take finished auctions from intent extras
        Intent intent = getIntent();
        this.auctions = intent.getStringExtra("auctions");

        try {
            jsonToObject(this.auctions);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        binding.profileShowAuctions.setOnClickListener(this);
        binding.profileAddFunds.setOnClickListener(this);
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
                T.VIEW_TOAST(getApplicationContext(), "Show Previous Auctions", Toast.LENGTH_SHORT);
                new MaterialDialog.Builder(this)
                        .title(R.string.finished)
                        .items(auctionIds)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                T.VIEW_TOAST(getApplicationContext(), ""+which, Toast.LENGTH_SHORT);
                                Intent mIntent = new Intent(getApplicationContext(), AuctionDetailsActivity.class);
                                mIntent.putExtra("title", auctionList.get(which).get("auction_id"));
                                mIntent.putExtra("object", auctionList.get(which).get("object_name"));
                                mIntent.putExtra("auctioneer", auctionList.get(which).get("auctioneer_id"));
                                mIntent.putExtra("price", auctionList.get(which).get("object_price"));
                                mIntent.putExtra("participated", auctionList.get(which).get("participated"));
                                mIntent.putExtra("running", 2);
                                mIntent.putExtra("auctions", auctions);
                                startActivity(mIntent);
                            }
                        })
                        .show();
                break;
            case R.id.profile_add_funds:
                T.VIEW_TOAST(getApplicationContext(), "Add funds", Toast.LENGTH_SHORT);
                new MaterialDialog.Builder(this)
                        .title(R.string.add_funds)
                        .content(R.string.insert_amount)
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .input("5000", "100", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                int funds = Integer.parseInt("" + input);
                                double zafeirium = funds*0.00001;
                                T.VIEW_TOAST(getApplicationContext(), "Zafeirium added to account "+zafeirium, Toast.LENGTH_LONG);
                            }
                        })
                        .negativeText(R.string.cancel)
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            default:
                break;
        }
    }

    // get information from json received from server
    public void jsonToObject(String auctions) throws JSONException {
        JSONArray jArray = new JSONArray(auctions);
        auctionIds = new String[jArray.length()];
        for (int i = 0; i < jArray.length(); i++) {

            JSONObject jObject = jArray.getJSONObject(i);

            HashMap<String, String> auction = new HashMap<>();

            // store ids only
            auctionIds[i] = jObject.getString("a");

            auction.put("auction_id", jObject.getString("a"));
            auction.put("auctioneer_id", jObject.getString("i"));
            auction.put("auction_type", jObject.getString("t"));
            auction.put("object_name", jObject.getString("n"));
            auction.put("object_price", jObject.getString("p"));

            auctionList.add(auction);
        }
    }
}
