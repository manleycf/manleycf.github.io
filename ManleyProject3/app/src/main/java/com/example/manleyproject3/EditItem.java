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
        setContentView(R.layout.edit_item_layout);

        inventoryDbHelper = new InventoryDatabaseHelper(this);
        nameEdit = findViewById(R.id.item_name);
        quantityEdit = findViewById(R.id.item_num);
        editButton = findViewById(R.id.editButton);
        deleteButton = findViewById(R.id.deleteButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editItem();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem();
            }
        });
    }

    private void editItem() {
        String name = nameEdit.getText().toString().trim();
        String quant = quantityEdit.getText().toString();
        int number = 0;
        number = Integer.parseInt(quant);

        if (name.isEmpty() || quant.isEmpty()) {
            Toast.makeText(this, "Name and quantity cannot be empty to edit", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            inventoryDbHelper.updateItem(name, number);
        }


        Intent intent = new Intent(EditItem.this, Inventory.class);
        startActivity(intent);
    }

    private void deleteItem() {
        String name = nameEdit.getText().toString().trim();

        inventoryDbHelper.delete_item(name);

        Intent intent = new Intent(EditItem.this, Inventory.class);
        startActivity(intent);
    }


}

