package com.example.enhancedartifact

import android.Manifest
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import com.example.enhancedartifact.ui.theme.LoginScreen

// Login Screen activity
class MainActivity : ComponentActivity() {

    // declare database helper & sms permission code
    private lateinit var userDbHelper: UserDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        // loads previous instance of the activity
        super.onCreate(savedInstanceState)

        // initializes database helper
        userDbHelper = UserDatabaseHelper(this)

        // loads Compose UI for Login Screen
        setContent {
            LoginScreen(
                onLoginClick = { username, password -> loginUser(username, password) },
                onRegisterClick = { username, password -> registerUser(username, password) }
            )
        }
    }

    // creates a new user on register button press
    private fun registerUser(username: String, password: String) {
        // if username/password is empty
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        // attempt to add username/password to database
        try {
            val db: SQLiteDatabase = userDbHelper.writableDatabase
            db.execSQL("INSERT INTO users (username, password) VALUES (?, ?)", arrayOf(username, password))
            db.close()
            Toast.makeText(this, "registration successful!", Toast.LENGTH_SHORT).show()
        }
        // catch a failure to add account
        catch (e: Exception) {
            Toast.makeText(this, "Registration failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    // logs in with entered credentials
    private fun loginUser(username: String, password: String) {
        // if username/password is empty
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        // search entered username/password
        val db: SQLiteDatabase = userDbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", arrayOf(username, password))

        // if username/password are in database, log in
        if (cursor.moveToFirst()) {
            val userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Inventory::class.java)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
        }
        // else send failure message
        else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        db.close()
    }
}