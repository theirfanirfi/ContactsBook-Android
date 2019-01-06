package com.contactsapp.contactsapp;

import android.content.ContentValues;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.lang.reflect.Type;

public class AddContact extends AppCompatActivity {
    TextInputLayout fullnameLayout, countryCodeLayout, addressLayout,phoneLayout;
    AppCompatEditText fullname, countryCode, phoneNumber, address;
    ConstraintLayout layout;
    DB db;
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
        db = new DB(this);

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
                saveContact();
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

    private void saveContact(){
        String name = fullname.getText().toString();
        String codee = countryCode.getText().toString();
        String number = phoneNumber.getText().toString();
        String addres = address.getText().toString();

        if(name.isEmpty() || codee.isEmpty() || number.isEmpty() || addres.isEmpty()){
            Snackbar.make(getCurrentFocus(),"Fields cannot be empty",Snackbar.LENGTH_LONG).show();
        }else {
            if(!(codee.equals("0044") || codee.equals("044") || codee.equals("44") || codee.equals("+44"))){
                Snackbar.make(getCurrentFocus(),"Country code is not a valid UK code.",Snackbar.LENGTH_LONG).show();
            }else if(number.length() < 11 || number.length() > 11){
                Snackbar.make(getCurrentFocus(),"Phone number must be 11 digits.",Snackbar.LENGTH_LONG).show();
            }else {
                //Now, the war is over. Zero Killed.
                //You can peacfully insert the data to the database now.

                ContentValues values = new ContentValues();
                values.put(DB.NAME,name);
                values.put(DB.ADDRESS,addres);
                values.put(DB.NUMBER,number);
                values.put(DB.COUNTRY_CODE,codee);
                Boolean isAdded = db.addContact(values);

                if(isAdded){
                    Snackbar.make(getCurrentFocus(),"Contact Added",Snackbar.LENGTH_LONG).show();
                }else {
                    fullname.setText("");
                    countryCode.setText("");
                    phoneNumber.setText("");
                    address.setText("");
                    Snackbar.make(getCurrentFocus(),"Error occured in Adding the contact.",Snackbar.LENGTH_LONG).show();
                }
            }
        }
    }
}
