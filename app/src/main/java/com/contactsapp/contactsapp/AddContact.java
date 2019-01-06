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
    //objects of repective types are declared
    TextInputLayout fullnameLayout, countryCodeLayout, addressLayout,phoneLayout;
    AppCompatEditText fullname, countryCode, phoneNumber, address;
    ConstraintLayout layout;
    DB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        //objects are initialized
        initObjects();
        //click events are declared in the events function.
        events();
    }

    private void initObjects(){
        //all the objects are initialized here.
        fullnameLayout = findViewById(R.id.fullnameLayout);
        fullname = findViewById(R.id.name);
        layout = findViewById(R.id.mainlayout);
        countryCodeLayout = findViewById(R.id.countrycodeLayout);
        countryCodeLayout.setCounterMaxLength(3);
        countryCode = findViewById(R.id.countrycode);
        countryCodeLayout.setCounterEnabled(true);
        countryCodeLayout.setCounterMaxLength(3);
        //title is set here
        getSupportActionBar().setTitle("New Contact");

        phoneLayout = findViewById(R.id.phoneNumberLayout);
        phoneNumber = findViewById(R.id.phoneNumber);
        addressLayout = findViewById(R.id.addressLayout);
        address = findViewById(R.id.address);
        //database class object is initialized
        db = new DB(this);

    }

    private void events() {
        //if fullname field was not focused and was empty.
        //it will turn red and the following text will appear.

        fullname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if (fullname.getText().toString().isEmpty()) {
                        // this one
                        fullnameLayout.setError("Full Name is required.");
                        fullnameLayout.setErrorEnabled(true);
                    } else {
                        //else no error.
                        fullnameLayout.setErrorEnabled(false);
                    }
                }
            }
        });

        //if text changes in the fullname field so the error should be removed
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


    //creating a menu for the two buttons @Save and @Discard appearing in the Toolbar of the activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_contact_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //click listener of the above menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.save:
                saveContact();
                break;
            case R.id.discard:
                finish();
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
        //data from the text fields will be stored in the repective variables
        String name = fullname.getText().toString();
        String codee = countryCode.getText().toString();
        String number = phoneNumber.getText().toString();
        String addres = address.getText().toString();

        //validating the fields
        if(name.isEmpty() || codee.isEmpty() || number.isEmpty() || addres.isEmpty()){
            Snackbar.make(getCurrentFocus(),"Fields cannot be empty",Snackbar.LENGTH_LONG).show();
        }else {
            //validating the country code
            if(!(codee.equals("0044") || codee.equals("044") || codee.equals("44") || codee.equals("+44"))){
                Snackbar.make(getCurrentFocus(),"Country code is not a valid UK code.",Snackbar.LENGTH_LONG).show();
                //validating the length of phone number
            }else if(number.length() < 11 || number.length() > 11){
                Snackbar.make(getCurrentFocus(),"Phone number must be 11 digits.",Snackbar.LENGTH_LONG).show();
            }else {
                //Now, the war is over. Zero Killed.
                //You can peacfully insert the data to the database now.

                //in sqlite database table insertion.
                //first the data is stored into ContentValues class object
                //which is then passed to database function
                ContentValues values = new ContentValues();
                //DB.NAME, DB.ADDRESS, DB.NUMBER, DB.COUNTRY_CODE are the variables declared in
                //the DB class representing the table columns
                values.put(DB.NAME,name);
                values.put(DB.ADDRESS,addres);
                values.put(DB.NUMBER,number);
                values.put(DB.COUNTRY_CODE,codee);

                Boolean isAdded = db.addContact(values);

                //if true was return
                if(isAdded){
                    Snackbar.make(getCurrentFocus(),"Contact Added",Snackbar.LENGTH_LONG).show();
                }else {
                    Snackbar.make(getCurrentFocus(),"Error occured in Adding the contact.",Snackbar.LENGTH_LONG).show();
                }
            }
        }
    }
}
