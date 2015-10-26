package ru.furnituranatali.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 * Created by Vavan on 23.10.2015.
 */
public class ControlSQL {
    private static final String DB_NAME = "catalog.db";
    private static final int DB_VER = 2;

    private static final String TBL_FIELD_CAPTION = "caption"; // type: text
    private static final String TBL_FIELD_CAPTION_L = "caption_long"; // type: text
    private static final String TBL_FIELD_IMAGE_HTML = "image_html"; // type: text
    private static final String TBL_FIELD_IMAGE_PATH = "image_path"; // type: text
    private static final String TBL_FIELD_LEVEL = "level"; // type: integer
    private static final String TBL_FIELD_PARENT_ID = "parent_ID"; // type: integer
    private static final String TBL_FIELD_PRICE = "price"; // type: real
    private static final String TBL_FIELD_SALE = "sale"; // type: real

    private static final String TBL_FIELD_CODE = "code"; // type: integer
	private static final String TBL_FIELD_UPD_TIME = "update_time"; // type integer
    private static final String TBL_FIELD_COMMENT = "comment"; // type: text

    private static final String TBL_FIELD_SUM = "sum"; // type: integer
    private static final String TBL_FIELD_GOODS_ID = "goods_ID"; // type: integer

    private static final String DB_TBL_CODES = "codes";
    private static final String DB_TBL_CATALOGS = "catalogs";
    private static final String DB_TBL_GOODS = "goods";
    private static final String DB_TBL_CART = "goods_cart";

    private List<CardData> currentCardList;
    private int currentLevel;
    private int parentIdx;
	private int updateCode;
	private Date updateTime;
    private boolean isSyncDB;
	
    private SQL_Helper helper;
    private Context context;
//    private SQLiteDatabase catalogDB;

    // public methods

    /**
     * Constructor
     * @param context - контекст активити из которого вызван конструктор
     */
    public ControlSQL(Context context) {
        this.context = context;
        helper = new SQL_Helper(context);
        currentLevel = 0;
        parentIdx = 0;

		InitTables();

//        SetCurrentCardList();
    }

    /**
     * Проверяем наличие данных в таблицах и если проверяемая таблица пуста создаем начальные значения
     */
    private void InitTables() {
        /**
         * Проверка таблицы codes
         */
//        SQLiteDatabase readDB = helper.getReadableDatabase();
        SQLiteDatabase writeDB = helper.getWritableDatabase();
        Cursor cursor;
        cursor = writeDB.query(DB_TBL_CODES, new String[]{TBL_FIELD_CODE, TBL_FIELD_UPD_TIME}, null, null, null, null, null);
        if (cursor.getCount() == 0) {
//            readDB.close();

            ContentValues val = new ContentValues();
            val.put(TBL_FIELD_CODE, 0);
            long curTime = System.currentTimeMillis() / 1000;
            val.put(TBL_FIELD_UPD_TIME, curTime);

            writeDB.insert(DB_TBL_CODES, null, val);
        }
        /**
         * Проверка таблицы catalogs
         */
//        writeDB = helper.getReadableDatabase();
        cursor = writeDB.query(DB_TBL_CATALOGS, new String[]{SQL_Helper._ID, TBL_FIELD_CAPTION}, null, null, null, null, null);
        if (cursor.getCount() == 0) {
//            readDB.close();
//            SQLiteDatabase writeDB = helper.getWritableDatabase();
            ContentValues val = new ContentValues();
            val.put(TBL_FIELD_CAPTION, context.getString(R.string.str_item0));
            writeDB.insert(DB_TBL_CATALOGS, TBL_FIELD_CAPTION, val);
        }
        writeDB.close();
    }

    public void setCurrentCardList() {
        if (helper != null){
            SQLiteDatabase readCatalogDB = helper.getReadableDatabase();
            if (currentCardList == null) currentCardList = new ArrayList<>();
            /**
             *  выборка значений для текущего отображения, в соответствии с уровнем и выбранным элементом верхнего уровня (если есть)
             */
			
            String nameTable = DB_TBL_CATALOGS;
            if (currentLevel > 0 && parentIdx < currentCardList.size()) {
                /**
                 * нужно найти все значения в таблицах, связанные с индексом указанным в CardData
                 * с номером parentIdx
                 */
            }
            readCatalogDB.beginTransaction();
            Cursor cursor = readCatalogDB.query(nameTable, new String[] {"*"}, null, null, null, null, null);

            readCatalogDB.endTransaction();
        }
    }

    public void levelUp() {

    }
    public void levelDown(){

    }
    public void pushToDB(){

    }
    public void pullFromDB(){

    }

    /**
     * Добавление нового элемента каталога для текущего уровня
     * @param card -
     */
    public void addCard(CardData card){

    }
    public void insertCard(CardData card, int position){

    }

    /**
     * Добавляем дочерний элемент для указанного элемента текущего уровня
     * @param cardIdx - индекс элемента каталога текущего уровня, к которому добавляем дочерний элемент
     * @param insertedCard - добавляемый в конец списка дочерний элемент
     * @param stayOnLevel - true: текущий уровень не меняется,
     *                    false: уровень увеличивается на 1 (вызывается метод levelDown)
     */
    public void addChildCard(int cardIdx, CardData insertedCard, boolean stayOnLevel){

    }
    public List<CardData> getCurrentCardList(){
        return currentCardList;
    }

    /**
     * inner Class _SQL_Helper
     *
     */
    private class SQL_Helper extends SQLiteOpenHelper implements BaseColumns{

    private static final String SQL_CREATE_TBL_CODES = "create table " + DB_TBL_CODES
            + " ("
            + BaseColumns._ID + " integer primary key autoincrement, "
            + TBL_FIELD_CODE + " integer, "
            + TBL_FIELD_UPD_TIME + " integer, "
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
    private static final String CART_FIELDS_ = " ("
            + BaseColumns._ID + " integer primary key autoincrement, "
            + TBL_FIELD_GOODS_ID + " integer, "
            + TBL_FIELD_CAPTION + " text, "
            + TBL_FIELD_PRICE + " real, "
            + BaseColumns._COUNT + " integer, "
            + TBL_FIELD_SUM + " real";
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
            db.execSQL(SQL_CREATE_TBL_ + DB_TBL_CART + CART_FIELDS_ + SQL_END_CREATE_TBL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
