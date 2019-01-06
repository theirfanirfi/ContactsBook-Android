package com.contactsapp.contactsapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CallActivity extends AppCompatActivity {

    //These are the Layout Components of the activity.
    TextView phone,callName;
    FloatingActionButton hangUp;

    //variable of type string - which will store phoneNumber, contactName,country code recieved in Intent from
    // @DialAcitivity or @MainActivity
    //initally its value is null.
    String phoneNumberToCall = null,cName = null,code = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        //the extras will be extracted from the intent recieved and
        // will be assigned to the @phoneNumberToCall variable
        phoneNumberToCall = getIntent().getExtras().getString("phone_number");
        cName = getIntent().getExtras().getString("call_name");
        code = getIntent().getExtras().getString("code");

        //in this function/method the UI components of the layout will be initialized.

        initObjects();

    }

    private void initObjects() {
        phone = findViewById(R.id.phoneNumber);
        hangUp = findViewById(R.id.hangup);
        callName = findViewById(R.id.callName);
        //the number recieved will be assigned to the textview
        // of the calling activity
        if(code.isEmpty()) {
            phone.setText(phoneNumberToCall);
        }else {
            phone.setText(code+" "+phoneNumberToCall);

        }

        if(cName.isEmpty()){
            callName.setVisibility(View.GONE);
        }else{
            callName.setText(cName);
            callName.setVisibility(View.VISIBLE);
        }

        //there is only one event in this activity and that is
        // set on @FloatinActionButton inside the event method
        //just for the sake of code clearity.
        events();
    }

    private void events(){

        //when the handUp button is clicked, so the
        // call simulating activity will be finished/Destroyed/Ended.
        hangUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this function finishes/destroys activity
                finish();
            }
        });
    }
}
