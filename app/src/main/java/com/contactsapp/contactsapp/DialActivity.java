package com.contactsapp.contactsapp;

import android.content.Context;
import android.content.Intent;
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

    //These are the components of the Layout.
    private AppCompatEditText phoneNumberField; //phoneNumber entered will be displayed in this field
    //gridView is for displaying the buttons in grid Format
    //each row of the grid will have @3 columns
    GridView gridView;

    //An adapter is declared for the gridview - which will diplay the buttons in grid Format
    DialAdapter dialAdapter;
    //context object of this class/Activity - where ever context is required so the object will be passed

    private Context context;

    //as mentioned in the documentation that initially, when no number is entered, only zero button should be displayed.
    //So for this a separate array of strings is taken, which will be initially passed to the adapter
    String[] zeroButton = {"0"};
    //After 0 is entered, so the gridView adapter will be adapted with following buttons
    //by passing this @buttons array of strings to the grid view Adapter - which in this case
    // is @DialAdapter and its object is @dialAdapter.
    String[] buttons = {"1","2","3","4","5","6","7","8","9","0","Cancel","Dial"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);

        //function/method for intializing the components of the layout and
        // adapter is also initalized here.
        initObjects();
        //this the method declare and @TextWatch event is set on the @phoneNumberField
        //if the field was empty, so only zero button will be displayed else all buttons
        watchChangesInTheNumber();
    }

    private void watchChangesInTheNumber() {
        phoneNumberField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //mean if the field is empty.
                onlyZeroButton();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //after text is changed so All buttons function will be called
                //and the adapter will be updated with all buttons
                    if(s.toString().length() >= 1){
                        //if the length of the string in the @phoneNumberField was greater than
                        // @1 so all buttons should be displayed
                        // and for this @AllButtons function is declared which will notify
                        // the adapter by passing the array of strings containing all buttons
                        AllButtons();
                    }else {
                        //else only zero button will be displayed.
                        onlyZeroButton();
                    }
            }
        });
    }

    private void initObjects() {
        //context object is initalized
        context = this;
        //phoneNumberField and GridView is initialized.
        phoneNumberField = findViewById(R.id.phoneNumberToDial);
        gridView = findViewById(R.id.dialingButtonsGridView);
        //here @dialAdapter object is initialized which requires activity context
        // an array of strings for displaying buttons
        dialAdapter = new DialAdapter(this,zeroButton);
        //then a custom click listener interface is declared inside the @DialAdapter class
        // which is being set on the dialAdapter here.
        dialAdapter.setButtonsClickListener(this);

        //and finally the adapter is set to the @gridView
        gridView.setAdapter(dialAdapter);
    }

    private void onlyZeroButton(){

        //notifyAdapter is the method declared in the adapter class
        //which will notify the adapter when any change occurs
        dialAdapter.notifyAdapter(zeroButton);
    }

    private void AllButtons(){

        //notifyAdapter is the method declared in the adapter class
        //which will notify the adapter when any change occurs
        dialAdapter.notifyAdapter(buttons);
    }

    //when the activity goes into @onPause state or in background
    //we will finish it. And the activity will be no more in the background.
    // why? we will no longer need id.
    // If it was needed it will be started with an intent and destroyed with the same scenario
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    //when any button is clicked, this method will be called.
    @Override
    public void onClick(int position) {
        //when any button is clicked, this method will be called.
                String numberToAppend = null;
                //if length of the text field was less than @1 means zero
                if(phoneNumberField.getText().toString().length() < 1 ){
                    //so only 0 will be written in the @phoneNumberField
                    numberToAppend = "0";
                    phoneNumberField.setText(phoneNumberField.getText().toString()+numberToAppend);
                }else if(phoneNumberField.getText().toString().length() >= 1){
                    //else if greater than zero

                    //if the buttons text was equal to cancel, so it means the cancel button
                    //is pressed so we will finish the activity.
                    if(buttons[position].equals("Cancel")){
                        finish();

                    }else if(buttons[position].equals("Dial")){
                        //if the button pressed was Dial. so the number from the @phoneNumberField
                        // will be passed to the @CallActivity in intent as extra
                        Intent callAct = new Intent(context,CallActivity.class);
                        callAct.putExtra("phone_number",phoneNumberField.getText().toString());
                        callAct.putExtra("call_name","");
                        callAct.putExtra("code","");
                        startActivity(callAct);
                    }else {
                        //else the number will be appended to already entered number in the @phoneNumberField
                        numberToAppend =  buttons[position];
                        phoneNumberField.setText(phoneNumberField.getText().toString()+numberToAppend);
                    }

                }


               // phoneNumberField.setText(phoneNumberField.getText().toString()+numberToAppend);
    }
}
