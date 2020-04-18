package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import Model.Food;


public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DB_VERSION, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String str = "create table "+ Constants.DB_TABLE_NAME +"("
                +Constants.KEY_FOOD_ID +" INTEGER PRIMARY KEY,"
                +Constants.KEY_FOOD_NAME + " TEXT,"
                +Constants.KEY_FOOD_CALORIES + " INTEGER,"
                +Constants.KEY_FOOD_DATEADDED + " TEXT"
                +")";
        db.execSQL(str);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String str = "drop table "+Constants.DB_TABLE_NAME;
        db.execSQL(str);
        onCreate(db);

    }

    public int getSumCalories(){
        int cal = 0;
        SQLiteDatabase db = getReadableDatabase();
        String str = "SELECT SUM("+Constants.KEY_FOOD_CALORIES+") from "+Constants.DB_TABLE_NAME;
        Cursor cursor = db.rawQuery(str, null);
        if (cursor.moveToFirst()) {
            cal = cursor.getInt(0);
        }
        cursor.close();

        return cal;
    }

    public int getFoodCount(){
        int count = 0;
        SQLiteDatabase db = getReadableDatabase();
        String str = "SELECT * from "+ Constants.DB_TABLE_NAME;
        Cursor cursor = db.rawQuery(str,null);
        if (cursor.moveToFirst()) {
            count = cursor.getCount();
        }
        cursor.close();
        db.close();

        return count;

    }

    public void deleteFood(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Constants.DB_TABLE_NAME, Constants.KEY_FOOD_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void addFood(Food food){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();;
        values.put(Constants.KEY_FOOD_NAME, food.getName());
        values.put(Constants.KEY_FOOD_CALORIES, food.getCalories());
        values.put(Constants.KEY_FOOD_DATEADDED, System.currentTimeMillis());

        db.insert(Constants.DB_TABLE_NAME,null,values);
        Log.d("added food item ", "yesss!");
        db.close();
    }

    public ArrayList<Food> getAllFood(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(Constants.DB_TABLE_NAME,new String[]{Constants.KEY_FOOD_ID, Constants.KEY_FOOD_NAME,
                Constants.KEY_FOOD_CALORIES, Constants.KEY_FOOD_DATEADDED}, null, null,
                null, null, Constants.KEY_FOOD_DATEADDED+" DESC");

        //loop through
        ArrayList<Food> foodList = new ArrayList<>();

        if (cursor.moveToFirst()){
            do {
                Food food = new Food();
                food.setId(cursor.getInt(0));
                food.setName(cursor.getString(1));
                food.setCalories(cursor.getInt(2));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String date = dateFormat.format(new Date(cursor.getLong(3)));
                food.setDateAdded(date);

                foodList.add(food);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return foodList;
    }



}
