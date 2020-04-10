package com.example.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import Data.DatabaseHandler;
import Model.Contact;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);

        //to reset db, comment when using in app
        db.onUpgrade(db.getWritableDatabase(),1,1);

        Log.d("Insert: ", "inserting...");
        db.addContact(new Contact("Paul", "64 525 545"));
        db.addContact(new Contact("Sami", "22 555 222"));
        db.addContact(new Contact("houda", "22 333 213"));

        Log.d("Reading: ", "Reading all contacts...");
        List<Contact> contactList = db.gettAllContacts();


        for(Contact c : contactList){
            String log="ID: "+c.getId() +  ", Name: "+c.getName() + ", Phone: "+ c.getPhoneNumber();
            Log.d("Contact: ", log);
        }

        int res = db.updateContact(new Contact(1,"Paul Changed", "64 525 545"));
        Log.d("Update ", String.valueOf(res));
        Contact c = db.getContact("1");
        String log="ID: "+c.getId() +  ", Name: "+c.getName() + ", Phone: "+ c.getPhoneNumber();
        Log.d("Contact ", log);
        Log.d("Count  ", String.valueOf(db.getContactsCount()));
        db.deleteContact(c);

        Log.d("Reading ", "Reading all contacts...");
        contactList = db.gettAllContacts();

        Log.d("Count after delete ", String.valueOf(db.getContactsCount()));
        for(Contact c1 : contactList){
            String log1="ID: "+c1.getId() +  ", Name: "+c1.getName() + ", Phone: "+ c1.getPhoneNumber();
            Log.d("Contact ", log1);
        }



    }

}
