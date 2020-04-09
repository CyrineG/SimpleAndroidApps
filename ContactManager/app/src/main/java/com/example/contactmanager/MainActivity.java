package com.example.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

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

    }
}
