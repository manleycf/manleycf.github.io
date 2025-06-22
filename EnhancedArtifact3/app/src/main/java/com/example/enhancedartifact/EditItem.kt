package com.example.enhancedartifact

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import android.content.Intent
import com.example.enhancedartifact.ui.theme.EditItemScreen

class EditItem : ComponentActivity() {
    private lateinit var inventoryDbHelper: InventoryDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // retrieve userId from intent, pass to helper
        val userId = intent.getIntExtra("USER_ID", -1)
        inventoryDbHelper = InventoryDatabaseHelper(this, userId)

        // load ui
        setContent {
            EditItemScreen(
                onEditClick = { name, quantity -> editItem(name, quantity) },
                onDeleteClick = { name -> deleteItem(name) }
            )
        }
    }

    private fun editItem(name: String, quantity: Int) {
        if (name.isEmpty() || quantity < 0) {
            Toast.makeText(this, "Name and quantity invalid", Toast.LENGTH_SHORT).show()
            return
        }

        inventoryDbHelper.editItem(name, quantity)
        // checkItem(name, quantity)

        Toast.makeText(this, "Item updated", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, Inventory::class.java))
    }

    private fun deleteItem(name: String) {
        if (name.isNotEmpty()) {
            inventoryDbHelper.deleteItem(name)
            Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, Inventory::class.java))
        }
    }
}