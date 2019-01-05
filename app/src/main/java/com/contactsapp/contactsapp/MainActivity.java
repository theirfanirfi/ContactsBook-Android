package com.contactsapp.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Context context;
    ListView lv;
    ArrayAdapter<String> adapter;
    String CONTACTS[] = {"Irfan","Shahid","Shoaib","Hammad","Abid","Ijaz","Aziz","Abbas"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initObjects();
    }

    private void initObjects(){
        context = this;
        adapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,CONTACTS);
        lv = findViewById(R.id.contactsList);
        lv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.add_contact:
                Intent addContactActivity = new Intent(context,AddContact.class);
                startActivity(addContactActivity);
                break;
            case R.id.dial:
                Intent dialAct = new Intent(context,DialActivity.class);
                startActivity(dialAct);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
