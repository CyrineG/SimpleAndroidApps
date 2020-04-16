package Data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.Grocery;
import Util.Constants;

public class DatabaseHandler extends SQLiteOpenHelper {
    private Context ctx;
    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GROCERY_TABLE = "CREATE TABLE "+Constants.GROCERY_TABLE_NAME+"("
                + Constants.KEY_ITEM_ID +" INTEGER PRIMARY KEY,"
                + Constants.KEY_ITEM_NAME +" TEXT,"
                + Constants.KEY_ITEM_QTY + " TEXT,"
                +Constants.KEY_DATE_ITEM_ADDED + " LONG"
                +")";
        db.execSQL(CREATE_GROCERY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Constants.GROCERY_TABLE_NAME);
        onCreate(db);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addGrocery(Grocery item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Constants.KEY_ITEM_NAME, item.getName());
        value.put(Constants.KEY_ITEM_QTY, item.getQuantity());
        value.put(Constants.KEY_DATE_ITEM_ADDED, java.lang.System.currentTimeMillis());

        db.insert(Constants.GROCERY_TABLE_NAME, null, value);
        //Log.d("Saved", "saved to DB with date" + System.currentTimeMillis());
        db.close();
    }


    public Grocery getGroceryItem(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(Constants.GROCERY_TABLE_NAME, new String[]{String.valueOf(Constants.KEY_ITEM_ID), Constants.KEY_ITEM_NAME,
        Constants.KEY_ITEM_QTY, String.valueOf(Constants.KEY_DATE_ITEM_ADDED)}, Constants.KEY_ITEM_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null );
        Grocery item = new Grocery();
        if(cursor.moveToFirst()){

            item.setId(Integer.parseInt(cursor.getString(0)));
            item.setName(cursor.getString(1));
            item.setQuantity(cursor.getString(2));

            DateFormat dateFormat = DateFormat.getDateInstance();
            String formatedDate = dateFormat.format(new Date(Long.parseLong(cursor.getString(3))).getTime());
            item.setDateItemAdded(formatedDate);
        }
        cursor.close();
        return item;
    }

    public List<Grocery> getAllGrocery(){
        SQLiteDatabase db = getReadableDatabase();
        String getAllGrocery = "SELECT * FROM "+Constants.GROCERY_TABLE_NAME;
        Cursor cursor = db.query(Constants.GROCERY_TABLE_NAME, new String[]{String.valueOf(Constants.KEY_ITEM_ID), Constants.KEY_ITEM_NAME,
                Constants.KEY_ITEM_QTY, String.valueOf(Constants.KEY_DATE_ITEM_ADDED)}, null, null, null,
                null, Constants.KEY_DATE_ITEM_ADDED + " DESC");

        List<Grocery> groceryList = new ArrayList<>();

        if(cursor.moveToFirst()) do{
            Grocery item = new Grocery();
            item.setId(Integer.parseInt(cursor.getString(0)));
            item.setName(cursor.getString(1));
            item.setQuantity(cursor.getString(2));

            DateFormat dateFormat = DateFormat.getDateInstance();
            String formatedDate = dateFormat.format(new Date(cursor.getLong(3)).getTime());;
            item.setDateItemAdded(formatedDate);

            groceryList.add(item);

        } while (cursor.moveToNext());
         cursor.close();
         return groceryList;
    }

    public void deleteGrocery(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Constants.GROCERY_TABLE_NAME, Constants.KEY_ITEM_ID + "=?", new String[]{String.valueOf(id)});

        db.close();
    }

    public int editGrocery(Grocery item){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put(Constants.KEY_ITEM_ID, item.getId());
        value.put(Constants.KEY_ITEM_NAME, item.getName());
        value.put(Constants.KEY_ITEM_QTY, item.getQuantity());
        value.put(Constants.KEY_DATE_ITEM_ADDED, java.lang.System.currentTimeMillis());

        int res = db.update(Constants.GROCERY_TABLE_NAME, value, Constants.KEY_ITEM_ID + "=?", new String []{String.valueOf(item.getId())});

        db.close();
        return res;
    }

    public int getGroceryCount(){
        SQLiteDatabase db = getReadableDatabase();
        String COUNT_QUERY = "SELECT * FROM "+Constants.GROCERY_TABLE_NAME;
        Cursor cursor = db.rawQuery(COUNT_QUERY, null);
        int res = cursor.getCount();

        cursor.close();

        return res;
    }
}
