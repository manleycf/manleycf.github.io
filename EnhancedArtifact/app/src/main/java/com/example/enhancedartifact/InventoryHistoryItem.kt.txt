package com.example.enhancedartifact

// data class for Metrics display
data class InventoryHistoryItem(
    val itemName: String,
    val quantityBefore: Int,
    val quantityAfter: Int,
    val changeType: String,
    val timestamp: Long
)
