package com.contactsapp.contactsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
