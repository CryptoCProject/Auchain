package com.sec.secureapp.activities;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sec.secureapp.R;
import com.sec.secureapp.databinding.ActivityCreateAuctionBinding;
import com.sec.secureapp.general.T;

import java.util.Calendar;

public class CreateAuctionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    ActivityCreateAuctionBinding binding;

    //create a list of items for the spinner.
    String[] type = new String[]{"British Auction"};

    private String auctionType = "";
    private String objectName = "";
    private String auctioneerId = "123456789";
    private String auctionDuration = "";
    private String initialPrice = "";
    private String participants = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_auction);

        //create a list of items for the spinner.
        setSpinner(type, binding.createType);

        binding.createAuctioneer.setText(auctioneerId);

        binding.createChooseObject.setOnClickListener(this);
        binding.createSubmit.setOnClickListener(this);
        binding.createDuration.setOnClickListener(this);
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
    public void onBackPressed() {
        Intent mIntent = new Intent(CreateAuctionActivity.this, MainActivity.class);
        startActivity(mIntent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create_choose_object:
                dialog();
                break;
            case R.id.create_submit:
                initialPrice = binding.createPrice.getText().toString();
                participants = binding.createParticipants.getText().toString();
                System.out.println("Auction Type: "+auctionType+" Object Name: "+objectName+" Auctioneer ID: "+auctioneerId+" Duration: "+auctionDuration+" Initial Price: "+initialPrice+" Participants: "+participants);
                break;
            case R.id.create_duration:
                durationPicker();
        }
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
                auctionType = "British";
                T.VIEW_TOAST(getApplicationContext(), auctionType, Toast.LENGTH_SHORT);
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
                        objectName = txtObject.getText().toString();
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

    private void durationPicker(){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(CreateAuctionActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                auctionDuration = selectedHour + ":" + selectedMinute;
                T.VIEW_TOAST(getApplicationContext(), selectedHour + ":" + selectedMinute, Toast.LENGTH_SHORT);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
}
