package com.example.manleyproject3;
import android.Manifest;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import android.content.Intent;

public class EditItem extends AppCompatActivity {
    private InventoryDatabaseHelper inventoryDbHelper;
    private EditText nameEdit;
    private EditText quantityEdit;
    private Button editButton;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // load layout xml
        setContentView(R.layout.edit_item_layout);

        // create database helper
        inventoryDbHelper = new InventoryDatabaseHelper(this);
        // assign editTexts and buttons to variables
        nameEdit = findViewById(R.id.item_name);
        quantityEdit = findViewById(R.id.item_num);
        editButton = findViewById(R.id.editButton);
        deleteButton = findViewById(R.id.deleteButton);

        // listener for edit button
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editItem();
            }
        });

        // listener for delete button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem();
            }
        });
    }

    private void editItem() {
        // get texts from editTexts
        String name = nameEdit.getText().toString().trim();
        String quant = quantityEdit.getText().toString();
        int number = 0;
        number = Integer.parseInt(quant);

        // handle empty fields
        if (name.isEmpty() || quant.isEmpty()) {
            Toast.makeText(this, "Name and quantity cannot be empty to edit", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            // update database info
            inventoryDbHelper.updateItem(name, number);
        }


        // return to inventory screen
        Intent intent = new Intent(EditItem.this, Inventory.class);
        startActivity(intent);
    }

    private void deleteItem() {
        // get text from editText
        String name = nameEdit.getText().toString().trim();

        inventoryDbHelper.delete_item(name);

        // return to inventory screen
        Intent intent = new Intent(EditItem.this, Inventory.class);
        startActivity(intent);
    }


}

