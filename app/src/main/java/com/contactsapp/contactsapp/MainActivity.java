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

    //context of the activity will be stored in this
    //context object.
    Context context;
    //listview displaying contacts object declared.
    ListView lv;
    //cursor adapter object declare
    //cursor adapter is used with SQLiteDatabase and ListView
    //Data is retrieved from the database table and passed to the Cursor Adapter
    //and the cursor adapter is then set to ListView
    CAdapter adapter;
    //@DB is the class containing all functions
    // through which the app interacts with sqlite database
    private DB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //In this method the declared objects are intialized.
        initObjects();
    }

    private void initObjects(){
        context = this;
        db = new DB(context);
        //here all the data is retrieved from the database and stored
        //in Cursor Class object @contacts
        //Cursor class object stores all the rows fetched from the database object.
        Cursor contacts = db.getData();
        //the object @contacts of the cursor class is then passed to the CursorAdapter
        //which in this case is @CAdapter
        adapter = new CAdapter(context,contacts);
        lv = findViewById(R.id.contactsList);
        //adapter is set to the listview
        lv.setAdapter(adapter);

        //when a contact is clicked, Call simulation will be initiated.
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //when a contact in the listView is clicked.
                //its id will be recieved in the click listener.
                //with the recieved ID, the contact further details
                //will be retrieved from the database and sent to the @CallActivity in intent

                Cursor cursor = db.getById(id);
                cursor.moveToFirst(); // this method is used to move the pointer to the first row of the rows fetched from the database
                //contactName, number and country code is stored in repective variables of type string.
                String contactName = cursor.getString(1);
                String contactNumber = cursor.getString(3);
                String code = cursor.getString(4);

                //here the data of the contact clicked is passed to the @CallActivity
                Intent call = new Intent(context,CallActivity.class);
                call.putExtra("phone_number",contactNumber);
                call.putExtra("call_name",contactName);
                call.putExtra("code",code);
                startActivity(call);
               // Log.i("MYCONTACTSAPP:",Long.toString(id)+ ": "+);
            }
        });


        //clicking listener for editing or deleting the contact
        //when a contact is clicked and hold, so this listener will be called and alertDialog will
        //appear with three buttons. @cancel, @delete, @edit
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Action")
                        //Edit Button
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //when edit button of the alert Dialog is clicked so
                                //the id of the contact clicked will be passed to the
                                //@EditActivity where the contac will be edited.
                                String idd = Long.toString(id);
                                Intent editActivity = new Intent(context,EditContact.class);
                                editActivity.putExtra("contact_id",idd);
                                startActivity(editActivity);
                            }
                        })
                        //delete Button
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //when delete button is cliced
                                //the clicked contact will be deleted.
                                String idd = Long.toString(id);

                                if(db.deleteContact(idd)){
                                    Snackbar.make(getCurrentFocus(),"Contact Deleted",Snackbar.LENGTH_LONG).show();
                                    //when contacted is add so the following function will be called to update the
                                    //listView with fresh data
                                    updateListView();
                                    dialog.dismiss();
                                }else {
                                    Snackbar.make(getCurrentFocus(),"Error Occurred in deleting the contact. Try again.",Snackbar.LENGTH_LONG).show();
                                    dialog.dismiss();
                                }
                            }
                        })

                        //when @cancel button is clicked the alert will be dismissed.
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //dismissing alertDialog
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
        //menu layout is created for the icons of @addcontact and @Dial
        //which is inflated here.
        //when this method is called. menu will appear in the activity.
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    //when the above menu items are clicked, so the following method will be called.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //id of the item clicked
        int id = item.getItemId();

        //id will be case matched
        switch (id){
            //if the addcontact icon was clicked
            case R.id.add_contact:
                //addContactActivity will be started
                Intent addContactActivity = new Intent(context,AddContact.class);
                startActivity(addContactActivity);
                break;
            case R.id.dial:
                //dialAcitivity will be called.
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
