package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import Model.Contact;
import Utils.Util;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);

    }
    //create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE= "CREATE TABLE "+ Util.TABLE_NAME + "("
                + Util.KEY_ID+" INTEGER PRIMARY KEY, "
                +Util.KEY_NAME+" TEXT,"
                +Util.KEY_PHONE_NUMBER +" TEXT" +")";

        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Util.TABLE_NAME);
        onCreate(db);
    }

    /**
     * CRUD Operations - Create, Read, Update, Delete
     */

    public void addContact(Contact contact){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Util.KEY_NAME, contact.getName());
        value.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        db.insert(Util.TABLE_NAME, null, value);
        db.close();
    }

    public Contact getContact(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.KEY_ID, Util.KEY_NAME, Util.KEY_PHONE_NUMBER},
                Util.KEY_ID+"=?",new String[]{id},null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        return contact;


    }

    public List<Contact> gettAllContacts(){
        SQLiteDatabase db = this.getReadableDatabase();

        List<Contact> contactList = new ArrayList<>();
        String readAll = "SELECT * FROM "+Util.TABLE_NAME;

        Cursor cursor = db.rawQuery(readAll, null);

        if(cursor.moveToFirst())
            do {
                Contact contact = new Contact();
                contact.setId( Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));

                contactList.add(contact);
            } while(cursor.moveToNext());

        return contactList;
    }
    public int updateContact(Contact contact ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Util.KEY_NAME, contact.getName());
        value.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());
        return db.update(Util.TABLE_NAME, value, Util.KEY_ID +"=?", new String[]{String.valueOf(contact.getId())} );
    }
}
