package com.contactsapp.contactsapp;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class AddContact extends AppCompatActivity {
    TextInputLayout fullnameLayout, countryCodeLayout, addressLayout,phoneLayout;
    AppCompatEditText fullname, countryCode, phoneNumber, address;
    ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        initObjects();
        events();
    }

    private void initObjects(){
        fullnameLayout = findViewById(R.id.fullnameLayout);
        fullname = findViewById(R.id.name);
        layout = findViewById(R.id.mainlayout);
        countryCodeLayout = findViewById(R.id.countrycodeLayout);
        countryCodeLayout.setCounterMaxLength(3);
        countryCode = findViewById(R.id.countrycode);
        countryCodeLayout.setCounterEnabled(true);
        countryCodeLayout.setCounterMaxLength(3);
        getSupportActionBar().setTitle("New Contact");

        phoneLayout = findViewById(R.id.phoneNumberLayout);
        phoneNumber = findViewById(R.id.phoneNumber);
        addressLayout = findViewById(R.id.addressLayout);
        address = findViewById(R.id.address);

    }

    private void events() {
        layout.setOnClickListener(null);


        fullname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if (fullname.getText().toString().isEmpty()) {
                        fullnameLayout.setError("Full Name is required.");
                        fullnameLayout.setErrorEnabled(true);
                    } else {
                        fullnameLayout.setErrorEnabled(false);
                    }
                }
            }
        });

        fullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(fullname.getText().toString().isEmpty()){
                    fullnameLayout.setError("Full Name is required.");
                    fullnameLayout.setErrorEnabled(true);
                }else {
                    fullnameLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(fullname.getText().toString().isEmpty()){
                    fullnameLayout.setError("Full Name is required.");
                    fullnameLayout.setErrorEnabled(true);
                }else {
                    fullnameLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_contact_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.save:
                break;
            case R.id.discard:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
