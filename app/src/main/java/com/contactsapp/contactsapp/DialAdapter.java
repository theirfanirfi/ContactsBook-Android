package com.contactsapp.contactsapp;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

//adapter class for the GrideView
//that displays Dial buttons in GridFormat
public class DialAdapter extends BaseAdapter {
    private Context context;
    private String[] numbers;
    AppCompatEditText phoneNumberField;
    buttonsClickListener clickListener;

    //strings to be displayed on each button are recieved in the constructor
    //from the main activity.
    public DialAdapter(Context context, String[] numbers) {
        //the recieved context and number strings are stored
        //into the instance variables of the class.
        this.context = context;
        this.numbers = numbers;
    }

    @Override
    public int getCount() {
        //size of the array is passed to the adapter.
        //it will generate buttons according to the size of the strings array which is @numbers
        //in this case
        return numbers.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //layout is created for displaying buttons and is inflated during run time.
        //the layout has only one button
        View view = LayoutInflater.from(context).inflate(R.layout.button_grid_dial_layout,parent,false);
        //the button is extracted from the layout by id
        Button btn = view.findViewById(R.id.dialBtn);
        //and the text to each button is set according to its position
            btn.setText(numbers[position]);

            //click listener is also set on each created button.
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //the button clicked will have a position which will be passed to the
                //@buttonsClickListener interface declared in this class
                clickListener.onClick(position);
            }
        });


        return view;
    }

    //this function is declared for notifying/updating
    //adapter of the gridview.

    public void notifyAdapter(String[] numbers) {
        this.numbers = numbers;
        notifyDataSetChanged();
    }

    //it is the click listener interface of the buttons
    //which is implemented in the main activity.

    public interface buttonsClickListener{
        public void onClick(int position);
    }

    //this method will be set on gridView.
    //in order to enable the click event on buttons of the gridview in DialActivity.
    public void setButtonsClickListener(buttonsClickListener clickListener){
        this.clickListener = clickListener;
    }
}
