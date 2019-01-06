package com.contactsapp.contactsapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.contactsapp.contactsapp.Adapters.CAdapter;

public class MainActivity extends AppCompatActivity {

    Context context;
    ListView lv;
    CAdapter adapter;
    String CONTACTS[] = {"Irfan","Shahid","Shoaib","Hammad","Abid","Ijaz","Aziz","Abbas"};
    private DB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initObjects();
    }

    private void initObjects(){
        context = this;
        db = new DB(context);
        Cursor contacts = db.getData();
        adapter = new CAdapter(context,contacts);
        lv = findViewById(R.id.contactsList);
        lv.setAdapter(adapter);

        //when a contact is clicked, Call simulation will be initiated.
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = db.getById(id);
                cursor.moveToFirst();
                String contactName = cursor.getString(1);
                String contactNumber = cursor.getString(3);
                String code = cursor.getString(4);

                Intent call = new Intent(context,CallActivity.class);
                call.putExtra("phone_number",contactNumber);
                call.putExtra("call_name",contactName);
                call.putExtra("code",code);
                startActivity(call);
               // Log.i("MYCONTACTSAPP:",Long.toString(id)+ ": "+);
            }
        });

        //clicking listener for editing or deleting the contact
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Action")
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String idd = Long.toString(id);
                                Intent editActivity = new Intent(context,EditContact.class);
                                editActivity.putExtra("contact_id",idd);
                                startActivity(editActivity);
                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String idd = Long.toString(id);
                                if(db.deleteContact(idd)){
                                    Snackbar.make(getCurrentFocus(),"Contact Deleted",Snackbar.LENGTH_LONG).show();
                                    updateListView();
                                    dialog.dismiss();
                                }else {
                                    Snackbar.make(getCurrentFocus(),"Error Occurred in deleting the contact. Try again.",Snackbar.LENGTH_LONG).show();
                                    dialog.dismiss();
                                }
                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                return true;
            }
        });
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

    //when another activty such as @AddContactActivity or @DialActivity starts,
    //so this activity goes into pause mode.
    //when it resumes, so it is required that the listview should be updated
    //therefore the cursor of the cursor adapter is changed in the onResume state of the activity
    @Override
    protected void onResume() {
        super.onResume();
        updateListView();
    }

    private void updateListView(){
        Cursor cursor = db.getData();
        adapter.changeCursor(cursor);
    }
}
