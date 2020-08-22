package lunainc.com.mx.pricetracker.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import lunainc.com.mx.pricetracker.Model.Price;
import lunainc.com.mx.pricetracker.Model.Product;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Tracker.db";
    private static final String PRODUCT_TABLE_NAME = "porduct";
    private static final String PRODUCT_COLUMN_ID = "id";
    private static final String PRODUCT_COLUMN_IDPRODUCT = "id_product";
    private static final String PRODUCT_COLUMN_NAME = "name";
    private static final String PRODUCT_COLUMN_PRICE = "price";
    private static final String PRODUCT_COLUMN_URL = "url";
    private static final String PRODUCT_COLUMN_DIST = "dist";
    private static final String PRODUCT_COLUMN_STATUS = "status";
    private static final String PRODUCT_COLUMN_CREATED_AT = "created_at";

    private static final String PRICE_TABLE_NAME = "price";
    private static final String PRICE_COLUMN_ID = "id";
    private static final String PRICE_COLUMN_PRODUCT_ID = "product_id";
    private static final String PRICE_COLUMN_PRICE = "price";
    private static final String PRICE_COLUMN_CHECKED = "checked";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "CREATE TABLE " + PRODUCT_TABLE_NAME +
                        "("+PRODUCT_COLUMN_ID+" integer primary key autoincrement," +
                        PRODUCT_COLUMN_IDPRODUCT+" integer," +
                        PRODUCT_COLUMN_NAME+" text," +
                        PRODUCT_COLUMN_PRICE+" text," +
                        PRODUCT_COLUMN_URL+" text," +
                        PRODUCT_COLUMN_DIST+" text," +
                        PRODUCT_COLUMN_STATUS+" text," +
                        PRODUCT_COLUMN_CREATED_AT+" DATETIME DEFAULT (datetime('now','localtime')))"
        );

        db.execSQL(
                "CREATE TABLE " + PRICE_TABLE_NAME +
                        "("+PRICE_COLUMN_ID+"  integer primary key autoincrement," +
                        PRICE_COLUMN_PRODUCT_ID+" integer," +
                        PRICE_COLUMN_PRICE+" text," +
                        PRICE_COLUMN_CHECKED+" DATETIME DEFAULT (datetime('now','localtime')))"
        );
    }


    public boolean createProduct(String id_product, String name, String price, String url , String dist, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_COLUMN_IDPRODUCT, id_product);
        contentValues.put(PRODUCT_COLUMN_NAME, name);
        contentValues.put(PRODUCT_COLUMN_PRICE, price);
        contentValues.put(PRODUCT_COLUMN_URL, url);
        contentValues.put(PRODUCT_COLUMN_DIST, dist);
        contentValues.put(PRODUCT_COLUMN_STATUS, status);
        long rowInserted = db.insert(PRODUCT_TABLE_NAME, null, contentValues);
        return rowInserted != -1;
    }

    public boolean createPrice(int product_id, String price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRICE_COLUMN_PRODUCT_ID, product_id);
        contentValues.put(PRICE_COLUMN_PRICE, price);
        long rowInserted = db.insert(PRICE_TABLE_NAME, null, contentValues);
        return rowInserted != -1;
    }



    public ArrayList<Product> getProducts(){
        ArrayList<Product> productArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        @SuppressLint("Recycle")
        Cursor res = db.rawQuery("SELECT * FROM "+PRODUCT_TABLE_NAME+ " ORDER BY "+PRODUCT_COLUMN_ID+" DESC", null);
        res.moveToFirst();

        while (!res.isAfterLast()){
            Product product = new Product();
            product.setId(res.getInt(res.getColumnIndex(PRODUCT_COLUMN_ID)));
            product.setId_product(res.getString(res.getColumnIndex(PRODUCT_COLUMN_IDPRODUCT)));
            product.setName(res.getString(res.getColumnIndex(PRODUCT_COLUMN_NAME)));
            product.setPrice(res.getString(res.getColumnIndex(PRODUCT_COLUMN_PRICE)));
            product.setUrl(res.getString(res.getColumnIndex(PRODUCT_COLUMN_URL)));
            product.setDist(res.getString(res.getColumnIndex(PRODUCT_COLUMN_DIST)));
            product.setCreated_at(res.getString(res.getColumnIndex(PRODUCT_COLUMN_CREATED_AT)));
            productArrayList.add(product);
            res.moveToNext();
        }

        return productArrayList;
    }

    public Integer updateProduct(String attr, String value, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(attr, value);
        return db.update(PRODUCT_TABLE_NAME, contentValues, "id = ?", new String[]{id});
    }




    public ArrayList<Price> getPrices(int id){
        ArrayList<Price> priceArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        @SuppressLint("Recycle")
        Cursor res = db.rawQuery("SELECT * FROM "+PRICE_TABLE_NAME+" WHERE "+PRICE_COLUMN_PRODUCT_ID+" = ? ORDER BY "+PRICE_COLUMN_ID+" ASC", new String[]{String.valueOf(id)});
        res.moveToFirst();

        while (!res.isAfterLast()){
            Price product = new Price();
            product.setId(res.getInt(res.getColumnIndex(PRODUCT_COLUMN_ID)));
            product.setProduct_id(res.getInt(res.getColumnIndex(PRICE_COLUMN_PRODUCT_ID)));
            product.setPrice(res.getString(res.getColumnIndex(PRICE_COLUMN_PRICE)));
            product.setChecked(res.getString(res.getColumnIndex(PRICE_COLUMN_CHECKED)));
            priceArrayList.add(product);
            res.moveToNext();
        }

        return priceArrayList;
    }


    public Integer deleteProduct(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PRODUCT_TABLE_NAME, "id = ?", new String[]{id});
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+PRODUCT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+PRICE_TABLE_NAME);
        onCreate(db);
    }


}
