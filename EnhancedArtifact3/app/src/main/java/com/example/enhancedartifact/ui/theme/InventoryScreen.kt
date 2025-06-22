package com.example.enhancedartifact.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.enhancedartifact.InventoryDatabaseHelper

@Composable
fun InventoryScreen(
    userId: Int,
    dbHelper: InventoryDatabaseHelper,
    onAddItemClick: () -> Unit,
    onEditItemClick: () -> Unit,
    onShowMetricsClick: () -> Unit,
) {
    // holds list of inventory items
    var inventoryItems by remember { mutableStateOf(listOf<Pair<String, Int>>()) }

    // loads inventory items onto screen
    LaunchedEffect(userId) {
        // retrieves all items for the current user
        val cursor = dbHelper.getAllItems()
        val items = mutableListOf<Pair<String, Int>>()
        // iterates through cursor to get name and quantity for all items
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow("item_name"))
            val quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"))
            items.add(name to quantity)
        }
        cursor.close()
        inventoryItems = items
    }

    // column fills whole screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // title banner
        Text(
            text = "Inventory",
            fontSize = 40.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // creates space between title banner and buttons
        Spacer(modifier = Modifier.height(16.dp))

        // holds add/edit item buttons and show metrics button
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = onAddItemClick) {
                Text("Add item")
            }
            Button(onClick = onEditItemClick) {
                Text("Edit item")
            }
            Button(onClick = onShowMetricsClick) {
                Text("Show Metrics")
            }
        }

        // creates space between buttons and inventory list grid
        Spacer(modifier = Modifier.height(16.dp))

        // grid holds inventory items for display
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // gets all items from list for display
            items(inventoryItems.size) { index ->
                val (name, quantity) = inventoryItems[index]
                Column {
                    Text(text = name)
                    Text(text = quantity.toString())
                }
            }
        }
    }
}