package com.textilechat.ingenious.textilechat.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database_Helper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Textile_Chat";


    public Database_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(marker_table.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + marker_table.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }


    public long insert_marks(String c_id, String sc_id, int unread) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(marker_table.COLUMN_c_id, c_id);
        values.put(marker_table.COLUMN_sc_id, sc_id);
        values.put(marker_table.COLUMN_unread, unread);

        // insert row
        long id = db.insert(marker_table.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    //for checking c_id 0r sc_id exist or not
    public int getvalueexist(String c_id,String sc_id) {
        String countQuery = "SELECT  * FROM " + marker_table.TABLE_NAME + " WHERE " + marker_table.COLUMN_c_id +" = "+c_id +" & "+marker_table.COLUMN_sc_id +" = "+ sc_id ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updatemarks(String c_id,String sc_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(marker_table.COLUMN_unread, +1);

        // updating row
        return db.update(marker_table.TABLE_NAME, values, marker_table.COLUMN_c_id + " = ? AND " + marker_table.COLUMN_sc_id + " = ?",
                new String[]{c_id,sc_id});
    }



}
