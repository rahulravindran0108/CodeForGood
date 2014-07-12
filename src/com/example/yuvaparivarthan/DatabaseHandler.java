package com.example.yuvaparivarthan;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	 
    // Database Name
    private static final String DATABASE_NAME = "DatabaseManager";
 
    // Contacts table name
    private static final String TABLE_CONTACTS = "Data";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_date = "date";
    private static final String KEY_location = "location";
    private static final String KEY_attendance = "attendance";
    private static final String KEY_campCode = "campcode";
    private static final String KEY_feescollected = "feescollected";
    
	public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_date + " TEXT," + KEY_location + " TEXT,"
                + KEY_attendance + " TEXT," + KEY_campCode+" TEXT,"+KEY_feescollected+" TEXT"+")";
        db.execSQL(CREATE_CONTACTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
		 
        // Create tables again
        onCreate(db);
	}
	
	public void addData(Data data) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(KEY_date, data.getDate()); // Contact Name
	    values.put(KEY_location, data.getLocation());
	    values.put(KEY_attendance, data.getAttendance());
	    values.put(KEY_campCode, data.getCampCode());
	    values.put(KEY_feescollected, data.getFeesCollected());// Contact Phone Number
	 
	    // Inserting Row
	    db.insert(TABLE_CONTACTS, null, values);
	    db.close(); // Closing database connection
	}
	/*
	public Data getData(String id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_date,
	            KEY_location, KEY_attendance,KEY_campCode,KEY_feescollected }, KEY_ID + "=?",
	            new String[] { String.valueOf(id) }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    Data contact = new Data(Integer.parseInt(cursor.getString(0)),
	            cursor.getString(1), cursor.getString(2));
	    // return contact
	    return contact;
	}
	*/
	   // Getting All Data
	 public List<Data> getAllData() {
	    List<Data> DataList = new ArrayList<Data>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	            Data data = new Data(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
	            DataList.add(data);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return DataList;
	}
	 
	 public void deleteContact(Data d) {
	        SQLiteDatabase db = this.getWritableDatabase();
	        db.delete(TABLE_CONTACTS, KEY_date + " = ? AND "+KEY_campCode+" = ?",
	                new String[] { d.getDate(),d.getCampCode()});
	        db.close();
	    }
	
}
