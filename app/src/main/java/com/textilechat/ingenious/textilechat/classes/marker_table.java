package com.textilechat.ingenious.textilechat.classes;

public class marker_table {
    private String c_id;
    private String sc_id;
    private String unread;


    public marker_table(String c_id, String sc_id, String unread) {
        this.c_id = c_id;
        this.sc_id = sc_id;
        this.unread = unread;
    }


    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getSc_id() {
        return sc_id;
    }

    public void setSc_id(String sc_id) {
        this.sc_id = sc_id;
    }

    public String getUnread() {
        return unread;
    }

    public void setUnread(String unread) {
        this.unread = unread;
    }


    public static final String TABLE_NAME = "unread_marker_table";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_c_id = "c_id";
    public static final String COLUMN_sc_id = "sc_id";
    public static final String COLUMN_unread = "unreadtotal";

    // Create table SQL query for carts
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_c_id + " TEXT,"
                    + COLUMN_sc_id + " TEXT,"
                    + COLUMN_unread + " INTEGER"
                    + ")";
}
