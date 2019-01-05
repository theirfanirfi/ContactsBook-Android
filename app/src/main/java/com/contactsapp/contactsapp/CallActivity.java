package com.contactsapp.contactsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CallActivity extends AppCompatActivity {

    String phoneNumberToCall = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        phoneNumberToCall = getIntent().getExtras().getString("phone_number");

    }
}
