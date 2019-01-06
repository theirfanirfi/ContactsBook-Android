package com.contactsapp.contactsapp.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.contactsapp.contactsapp.R;

public class CAdapter extends CursorAdapter {
    public CAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_layout,parent,false);
        TextView name = view.findViewById(R.id.name);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView contactName = view.findViewById(R.id.name);
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        contactName.setText(name);
    }

}
