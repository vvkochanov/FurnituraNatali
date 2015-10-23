package ru.furnituranatali.app;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vavan on 23.10.2015.
 */
public class ControlSQL {
    private static final String DB_NAME = "catalog.db";
    private static final int DB_VER = 1;

    private static final String TBL_FIELD_CAPTION = "caption"; // type: text
    private static final String TBL_FIELD_CAPTION_L = "caption long"; // type: text
    private static final String TBL_FIELD_IMAGE_HTML = "image html"; // type: text
    private static final String TBL_FIELD_IMAGE_PATH = "image path"; // type: text
    private static final String TBL_FIELD_LEVEL = "level"; // type: integer
    private static final String TBL_FIELD_PARENT_ID = "parent id"; // type: integer
    private static final String TBL_FIELD_PRICE = "price"; // type: real
    private static final String TBL_FIELD_SALE = "sale"; // type: real

    private static final String TBL_FIELD_CODE = "code"; // type: integer
    private static final String TBL_FIELD_COMMENT = "comment"; // type: text

    private static final String DB_TBL_CODES = "codes";
    private static final String DB_TBL_CATALOGS = "catalogs";
    private static final String DB_TBL_GOODS = "goods";

    private List<CardData> currentCardList;
    private int currentLevel;
    private int parentIdx;

    private SQL_Helper helper;
//    private SQLiteDatabase catalogDB;

    // public methods
    public ControlSQL(Context context) {
        helper = new SQL_Helper(context);
        currentLevel = 0;
        parentIdx = 0;
        SetCurrentCardList();
    }

    public void SetCurrentCardList() {
        if (helper != null){
            SQLiteDatabase readCatalogDB = helper.getReadableDatabase();
            if (currentCardList == null) currentCardList = new ArrayList<>();
            String nameTable = DB_TBL_CATALOGS;
            if (currentLevel > 0 && parentIdx < currentCardList.size()) {
                //нужно найти все значения в таблицах, связанные с индексом указанным в CardData
                // с номером parentIdx
            }
            readCatalogDB.beginTransaction();
            Cursor cursor = readCatalogDB.query(nameTable, new String[] {"ALL"}, null, null, null, null, null);

            readCatalogDB.endTransaction();
        }
    }

    public void levelUp() {

    }
    public void levelDown(){

    }

//  inner Class _SQL_Helper
    private class SQL_Helper extends SQLiteOpenHelper implements BaseColumns{

    private static final String SQL_CREATE_TBL_CODES = "create table " + DB_TBL_CODES
            + " ("
            + BaseColumns._ID + " integer primary key autoincrement, "
            + TBL_FIELD_CODE + " integer, "
            + TBL_FIELD_COMMENT + " text";
    private static final String SQL_CREATE_TBL_ = "create table ";
    private static final String CATALOGS_FIELDS_ =  " ("
            + BaseColumns._ID + " integer primary key autoincrement, "
            + TBL_FIELD_PARENT_ID + " integer, "
            + TBL_FIELD_LEVEL + " integer, "
            + TBL_FIELD_CAPTION + " text not null, "
            + TBL_FIELD_CAPTION_L + " text, "
            + TBL_FIELD_IMAGE_HTML + " text, "
            + TBL_FIELD_IMAGE_PATH + " text";
    private static final String GOODS_FIELDS_ = ", "
            + TBL_FIELD_PRICE + " real, "
            + TBL_FIELD_SALE + " real";
    private static final String SQL_END_CREATE_TBL = ");";

        public SQL_Helper(Context context){
            super(context, DB_NAME, null, DB_VER);
        }
        public SQL_Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public SQL_Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_TBL_CODES + SQL_END_CREATE_TBL);
            db.execSQL(SQL_CREATE_TBL_ + DB_TBL_CATALOGS + CATALOGS_FIELDS_ + SQL_END_CREATE_TBL);
            db.execSQL(SQL_CREATE_TBL_ + DB_TBL_GOODS + CATALOGS_FIELDS_ + GOODS_FIELDS_ + SQL_END_CREATE_TBL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
