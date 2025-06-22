package com.example.enhancedartifact.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.enhancedartifact.InventoryHistoryItem

// UI for Metrics display
@Composable
fun MetricScreen(historyItems: List<InventoryHistoryItem>) {
    // Determine values for display
    val totalAdded = historyItems.sumOf { (it.quantityAfter - it.quantityBefore).coerceAtLeast(0) }
    val totalRemoved = historyItems.sumOf { (it.quantityBefore - it.quantityAfter).coerceAtLeast(0) }
    val totalEdits = historyItems.count { it.changeType == "edit" }

    // Calculate total current quantity
    val latestQuantities = historyItems
        .groupBy { it.itemName }
        .mapValues { entry -> entry.value.maxByOrNull { it.timestamp }?.quantityAfter ?: 0 }
    val totalCurrentQuantity = latestQuantities.values.sum()

    // text that displays values
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Inventory Metrics", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        SummaryMetricRow(label = "Total Added Items", value = totalAdded)
        SummaryMetricRow(label = "Total Removed Items", value = totalRemoved)
        SummaryMetricRow(label = "Total Edits", value = totalEdits)
        SummaryMetricRow(label = "Current Total Quantity", value = totalCurrentQuantity)
    }
}

@Composable
fun SummaryMetricRow(label: String, value: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label)
        Text(text = value.toString(), fontWeight = FontWeight.Bold)
    }
}
