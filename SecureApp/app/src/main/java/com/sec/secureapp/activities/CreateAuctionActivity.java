package com.sec.secureapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sec.secureapp.R;
import com.sec.secureapp.databinding.ActivityCreateAuctionBinding;
import com.sec.secureapp.general.T;

public class CreateAuctionActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    ActivityCreateAuctionBinding binding;

    //create a list of items for the spinner.
    String[] type = new String[]{"British Auction"};
    String[] objects = new String[]{"Object1", "Object2"};

    String auctioneer_id = "12345678";

    private String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutResourceId());

        //create a list of items for the spinner.
        setSpinner(type, binding.createType);

        binding.createAuctioneer.setText(auctioneer_id);

        binding.createChooseObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });

        binding.createSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setSpinner(String[] spinner_items, Spinner spinner) {
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinner_items);
        //set the spinners adapter to the previously created one.
        spinner.setAdapter(adapter);
        if (spinner == binding.createType)
            spinner.setOnItemSelectedListener(new TypeSpinner());
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_create_auction;
    }

    @Override
    protected void clickButtons(View view) {

    }

    @Override
    public void onBackPressed() {
        Intent mIntent = new Intent(CreateAuctionActivity.this, MainActivity.class);
        startActivity(mIntent);
    }

    @Override
    public void onClick(View view) {

    }

    class TypeSpinner implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            Toast.makeText(v.getContext(), "Your choose :" + type[position], Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                T.VIEW_TOAST(getApplicationContext(), "British", Toast.LENGTH_SHORT);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private String dialog(){
        final EditText txtObject = new EditText(this);

        new AlertDialog.Builder(this)
                .setTitle("Choose Object")
                .setMessage("Write the object you want to put on auction.")
                .setView(txtObject)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String url = txtObject.getText().toString();
                        T.VIEW_TOAST(getApplicationContext(), url, Toast.LENGTH_SHORT);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                })
                .show();

        return "";
    }
}
