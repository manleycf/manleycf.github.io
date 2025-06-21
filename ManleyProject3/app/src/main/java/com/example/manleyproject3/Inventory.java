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
        // load layout xml
        setContentView(R.layout.inventory_layout);

        inventoryDbHelper = new InventoryDatabaseHelper(this);

        // create gridLayout for displaying inventory data
        gridLayout = findViewById(R.id.grid_layout);
        addItem = findViewById(R.id.addItem);
        editRow = findViewById(R.id.editRow);

        // retrieve info from database
        loadInventoryData();

        // listener for add item button
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

        // listener for edit item button
        editRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editRow();
            }
        });
    }

    // retrieves and displays info from database
    private void loadInventoryData() {
        // load all items in database into cursor
        Cursor cursor = inventoryDbHelper.getAllItems();

        // loop through entire cursor
        while (cursor.moveToNext()) {
            // assign values to variable
            String itemName = cursor.getString(cursor.getColumnIndexOrThrow("item_name"));
            int itemQuantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));

            // put item name in displayable textview
            TextView itemNameView = new TextView(this);
            itemNameView.setText(itemName);
            // add textview to grid
            gridLayout.addView(itemNameView);

            // put quantity in displayable textview
            TextView itemQuantityView = new TextView(this);
            itemQuantityView.setText(String.valueOf(itemQuantity));
            // add textview to grid
            gridLayout.addView(itemQuantityView);

            // empty textview placeholder in third column
            TextView holder = new TextView(this);
            holder.setText("");
            gridLayout.addView(holder);
        }
    }

    private void addItem() {
        // load addItem screen
        Intent intent = new Intent(Inventory.this, AddItem.class);
        startActivity(intent);
    }

    private void editRow() {
        // load editItem screen
        Intent intent = new Intent(Inventory.this, EditItem.class);
        startActivity(intent);
    }
}
