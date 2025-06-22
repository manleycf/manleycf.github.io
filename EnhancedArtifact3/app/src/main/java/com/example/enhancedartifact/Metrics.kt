package com.example.enhancedartifact

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.enhancedartifact.ui.theme.MetricScreen

class Metrics : ComponentActivity() {

    // create databaseHelper
    private lateinit var dbHelper: InventoryDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get userId
        val userId = intent.getIntExtra("USER_ID", -1)
        dbHelper = InventoryDatabaseHelper(this, userId)

        // Query history from DB
        val cursor = dbHelper.readableDatabase.rawQuery(
            "SELECT item_name, quantity_before, quantity_after, change, timestamp FROM inventory_history WHERE user_id = ?",
            arrayOf(userId.toString())
        )
        // load data into historyList data type
        val historyList = dbHelper.cursorToHistoryList(cursor)

        // load ui
        setContent {
            MetricScreen(historyItems = historyList)
        }
    }
}
