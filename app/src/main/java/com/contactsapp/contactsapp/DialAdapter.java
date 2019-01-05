package com.contactsapp.contactsapp;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class DialAdapter extends BaseAdapter {
    private Context context;
    private String[] numbers;
    AppCompatEditText phoneNumberField;
    buttonsClickListener clickListener;

    public DialAdapter(Context context, String[] numbers) {
        this.context = context;
        this.numbers = numbers;
    }

    @Override
    public int getCount() {
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
        phoneNumberField = parent.findViewById(R.id.phoneNumberToDial);
        View view = LayoutInflater.from(context).inflate(R.layout.button_grid_dial_layout,parent,false);
        Button btn = view.findViewById(R.id.dialBtn);
        if(numbers[position].equals("Cancel")) {
            btn.setText(numbers[position]);
        }else if(numbers[position].equals("Dial")){
            btn.setText(numbers[position]);
        }else {
            btn.setText(numbers[position]);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(position);
            }
        });


        return view;
    }

    public void notifyAdapter(String[] numbers) {
        this.numbers = numbers;
        notifyDataSetChanged();
    }


    public interface buttonsClickListener{
        public void onClick(int position);
    }

    public void setButtonsClickListener(buttonsClickListener clickListener){
        this.clickListener = clickListener;
    }
}
