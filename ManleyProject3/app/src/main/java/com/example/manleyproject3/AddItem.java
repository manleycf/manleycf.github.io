package com.example.manleyproject3;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class AddItem extends AppCompatActivity{
    private InventoryDatabaseHelper inventoryDbHelper;
    private EditText nameEdit;
    private EditText quantityEdit;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // load layout xml
        setContentView(R.layout.add_item_layout);

        // create database helper
        inventoryDbHelper = new InventoryDatabaseHelper(this);
        // assign variables to editTexts and button
        nameEdit = findViewById(R.id.item_name);
        quantityEdit = findViewById(R.id.item_num);
        addButton = findViewById(R.id.addButton);

        // create listener for button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });
    }

    private void addItem() {
        // get text from editTexts
        String name = nameEdit.getText().toString().trim();
        String quant = quantityEdit.getText().toString();
        int number = 0;
        number = Integer.parseInt(quant);

        // handle empty fields
        if (name.isEmpty() || quant.isEmpty()) {
            Toast.makeText(this, "Item name and quantity cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            SQLiteDatabase db = inventoryDbHelper.getWritableDatabase();
            // write to inventory database
            db.execSQL("INSERT INTO inventory (item_name, quantity) VALUES (?,?)", new Object[]{name, number});
            db.close();
            Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();

            // return to inventory screen
            Intent intent = new Intent(AddItem.this, Inventory.class);
            startActivity(intent);
        }
    }
}
