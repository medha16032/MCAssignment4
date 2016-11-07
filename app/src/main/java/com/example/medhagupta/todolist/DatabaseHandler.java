package com.example.medhagupta.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Medha Gupta on 11/3/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper{

    // Static Variables
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ToDoList";
    private static final String TABLE_NAME = "doList";
    private static final String KEY_ID = "id";
    private static final String TITLE = "title";
    private static final String DETAILS = "details";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    /*Override this function to create a new table*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " TEXT,"
                + DETAILS + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    /*Override this function to upgrade your table design / structure*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the old table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }
    /*addUser() will add a new User to database*/
    public void addItem(ToDoList item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE, item.getTitle());
        values.put(DETAILS, item.getDetails());

        db.insert(TABLE_NAME, null, values); //Insert query to store the record in the database
        db.close();

    }

    /*getUser() will return he user's object if id matches*/
    public ToDoList getItem(int key_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID,
                        TITLE, DETAILS}, KEY_ID + "=?",
                new String[]{String.valueOf(key_id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ToDoList itemFound = new ToDoList(cursor.getString(1), cursor.getString(2), cursor.getInt(0));
        return itemFound;
    }

    /*getAllUsers() will return the list of all users*/
    public ArrayList<ToDoList> getAllItems() {
        ArrayList<ToDoList> itemsList = new ArrayList<ToDoList>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ToDoList item = new ToDoList(cursor.getString(1), cursor.getString(2), cursor.getInt(0));
                itemsList.add(item);
            } while (cursor.moveToNext());
        }
        return itemsList;
    }
    /*getUsersCount() will give the total number of records in the table*/
    public int getListCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
    /*updateUser() will be used to update the existing user record*/
    public int updateItem(ToDoList item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE, item.getTitle());
        values.put(DETAILS, item.getDetails());
        // updating record
        return db.update(TABLE_NAME, values, KEY_ID + " = ?", // update query to make changes to the existing record
                new String[]{String.valueOf(item.getId())});
    }

    /*deleteContact() to delete the record from the table*/
    public void deleteItem(ToDoList item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(item.getId())});
        db.close();
    }
}

