package com.example.manleyproject3;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.widget.GridLayout;
import android.database.Cursor;

public class Inventory extends AppCompatActivity {
    private InventoryDatabaseHelper inventoryDbHelper;
    private Button addItem;
    private Button editRow;
    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_layout);

        inventoryDbHelper = new InventoryDatabaseHelper(this);

        gridLayout = findViewById(R.id.grid_layout);
        addItem = findViewById(R.id.addItem);
        editRow = findViewById(R.id.editRow);

        loadInventoryData();

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

        editRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editRow();
            }
        });
    }

    private void loadInventoryData() {
        Cursor cursor = inventoryDbHelper.getAllItems();

        while (cursor.moveToNext()) {
            String itemName = cursor.getString(cursor.getColumnIndexOrThrow("item_name"));
            int itemQuantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));

            TextView itemNameView = new TextView(this);
            itemNameView.setText(itemName);
            gridLayout.addView(itemNameView);

            TextView itemQuantityView = new TextView(this);
            itemQuantityView.setText(String.valueOf(itemQuantity));
            gridLayout.addView(itemQuantityView);

            TextView holder = new TextView(this);
            holder.setText("");
            gridLayout.addView(holder);
        }
    }

    private void addItem() {
        Intent intent = new Intent(Inventory.this, AddItem.class);
        startActivity(intent);
    }

    private void editRow() {
        Intent intent = new Intent(Inventory.this, EditItem.class);
        startActivity(intent);
    }
}
