package com.example.manleyproject3;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.ContentValues;

public class InventoryDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "inventory_database.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_INVENTORY_TABLE = "CREATE TABLE inventory (item_name TEXT, quantity INTEGER)";

    public InventoryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_INVENTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS inventory");
        onCreate(db);
    }

    public Cursor getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM inventory", null);
    }

    public void add_item(String name, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("item_name", name);
        values.put("quantity", quantity);
        db.insert("inventory", null, values);
    }

    public void delete_item(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "item_name=?";
        String[] whereArgs = new String[] { name };
        db.delete("inventory", whereClause, whereArgs);
        db.close();
    }

    public void updateItem(String itemName, int newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("quantity", newQuantity);

        db.update("inventory", values, "item_name = ?", new String[]{itemName});
        db.close();
    }
}
