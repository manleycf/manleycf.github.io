package com.example.enhancedartifact.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditItemScreen(
    onEditClick: (String, Int) -> Unit,
    onDeleteClick: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }

    // column fills screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // title banner
        Text("Edit Item", fontSize = 40.sp)

        // name field
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Item Name") },
            modifier = Modifier.fillMaxWidth()
        )

        // quantity field
        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("New Quantity") },
            // provides numerical keyboard
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // row holds edit/delete buttons
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                if (name.isNotBlank() && quantity.isNotBlank()) {
                    onEditClick(name.trim(), quantity.toIntOrNull() ?: 0)
                }
            }) {
                Text("Edit Item")
            }

            Button(onClick = {
                if (name.isNotBlank()) {
                    onDeleteClick(name.trim())
                }
            }) {
                Text("Delete Item")
            }
        }

    }
}