package com.contactsapp.contactsapp;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;

public class DialActivity extends AppCompatActivity implements DialAdapter.buttonsClickListener {

    private AppCompatEditText phoneNumberField;
    GridView gridView;
    DialAdapter dialAdapter;
    private Context context;
    String[] zeroButton = {"0"};
    String[] buttons = {"1","2","3","4","5","6","7","8","9","0","Cancel","Dial"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);
        initObjects();
        watchChangesInTheNumber();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            phoneNumberField.setShowSoftInputOnFocus(false);
        else
            phoneNumberField.setTextIsSelectable(true);
    }

    private void watchChangesInTheNumber() {
        phoneNumberField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                onlyZeroButton();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("contacts App: ",s.toString());
                    if(s.toString().length() >= 1){
                        AllButtons();
                    }else {
                        onlyZeroButton();
                    }
            }
        });
    }

    private void initObjects() {
        context = this;
        phoneNumberField = findViewById(R.id.phoneNumberToDial);
//        phoneNumberField.setInputType(InputType.TYPE_NULL);
//        phoneNumberField.setCursorVisible(true);
//        phoneNumberField.setFocusable(true);
//        phoneNumberField.setFocusableInTouchMode(true);
        gridView = findViewById(R.id.dialingButtonsGridView);
        String[] buttons = {"0"};
        dialAdapter = new DialAdapter(this,buttons);
        dialAdapter.setButtonsClickListener(this);
        gridView.setAdapter(dialAdapter);
    }

    private void onlyZeroButton(){

        dialAdapter.notifyAdapter(zeroButton);
    }

    private void AllButtons(){

        dialAdapter.notifyAdapter(buttons);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onClick(int position) {
                String numberToAppend = null;

                if(phoneNumberField.getText().toString().length() < 1 ){
                    numberToAppend = "0";
                }else if(phoneNumberField.getText().toString().length() >= 1){
                    numberToAppend =  buttons[position];
                }


                phoneNumberField.setText(phoneNumberField.getText().toString()+numberToAppend);
    }
}
