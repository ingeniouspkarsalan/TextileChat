package com.textilechat.ingenious.textilechat.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class Sqlite_for_markers extends SQLiteOpenHelper {

    //this lines for markers
    public static final String TABLE_NAME = "Unread_markers";
    public static final String TABLE_NAME_Personal = "Personal_chat";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_c_id = "category_id";
    public static final String COLUMN_sc_id = "Sub_category_id";
    public static final String COLUMN_msg_id = "msg_id";
    public static final String COLUMN_from_id = "from_user_id";


    // Create table SQL query for markers
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_msg_id + " INTEGER UNIQUE,"
                    + COLUMN_c_id + " TEXT,"
                    + COLUMN_sc_id + " TEXT"
                    + ")";


    // Create table SQL query for carts
    public static final String CREATE_TABLE_Personal =
            "CREATE TABLE " + TABLE_NAME_Personal + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_from_id + " TEXT"
                    + ")";




    public Sqlite_for_markers(Context context) {
        super(context, "Textile_chat", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_Personal);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_Personal);
        onCreate(db);
    }

        //inserting cat and sub
    public long insert_marks(int msg_id, String c_id, String sc_id) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(COLUMN_msg_id, msg_id);
        values.put(COLUMN_c_id, c_id);
        values.put(COLUMN_sc_id, sc_id);

        // insert row
        long id = db.insert(TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }


    //inserting personal_marker
    public long insert_per_marks(String from_id) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(COLUMN_from_id, from_id);

        // insert row
        long id = db.insert(TABLE_NAME_Personal, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }


    //getting all personal count
    public int getpersonalCount(String from_id) {
        String countQuery = "SELECT  * FROM " + TABLE_NAME_Personal+ " WHERE "+COLUMN_from_id+"="+from_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

db.close();
        // return count
        return count;
    }

    //deleting personal
    public void delete_personal(String from_id){
        String deletequery = "Delete  FROM " + TABLE_NAME_Personal+ " WHERE "+COLUMN_from_id+"="+from_id;
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(deletequery);
        db.close();
    }


    //getting all category count
    public int getCategoryCount(String c_id) {
        String countQuery = "SELECT  * FROM " + TABLE_NAME+ " WHERE "+COLUMN_c_id+"="+c_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

db.close();
        // return count
        return count;
    }

    //getting all sub category counts
    public int getSubCount(String c_id,String sc_id) {
        String countQuery = "SELECT  * FROM " + TABLE_NAME+ " WHERE "+COLUMN_c_id+"="+c_id+" AND "+COLUMN_sc_id+"="+sc_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

db.close();
        // return count
        return count;
    }

    //deleting cat
    public void delete_cat(String c_id){
        String deletequery = "Delete  FROM " + TABLE_NAME+ " WHERE "+COLUMN_c_id+"="+c_id;
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(deletequery);
        db.close();
    }

    //deleting sub cate
    public void delete_sub_cat(String c_id,String sc_id){
        String deletequery = "Delete  FROM " + TABLE_NAME+ " WHERE "+COLUMN_c_id+"="+c_id+" AND "+COLUMN_sc_id+"="+sc_id;
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(deletequery);
        db.close();
    }



    //getting unread msg id
    public int get_position(String c_id,String sc_id)
    {
        String countQuery = "SELECT  * FROM " + TABLE_NAME+ " WHERE "+COLUMN_c_id+"="+c_id+" AND "+COLUMN_sc_id+"="+sc_id;// +" LIMIT 1,1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        int msg_id=cursor.getInt(cursor.getColumnIndex(COLUMN_msg_id));
        cursor.close();
        db.close();
        return msg_id;
    }
}
