package com.example.manleyproject3;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.Toast;
import android.content.Intent;
import android.Manifest;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity{
    private UserDatabaseHelper userDbHelper;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private Button loginButton;
    private Button registerButton;
    private static final int SMS_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        // create user database helper
        userDbHelper = new UserDatabaseHelper(this);
        // assign editTexts and buttons to variables
        usernameEdit = findViewById(R.id.username);
        passwordEdit = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        // listener for register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        // listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void registerUser() {
        // get text from fields
        String username = usernameEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();

        // handle empty fields
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            // add username/password combo to user database
            SQLiteDatabase db = userDbHelper.getWritableDatabase();
            db.execSQL("INSERT INTO users (username, password) VALUES (?, ?)", new Object[]{username, password});
            db.close();
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
            // error message
        } catch (Exception e) {
            Toast.makeText(this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loginUser() {
        // get text from fields
        String username = usernameEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();

        // handle empty fields
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // search user database for provided username/password combo
        SQLiteDatabase db = userDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND PASSWORD = ?", new String[]{username, password});

        // if valid combo move to inventory screen
        if (cursor.moveToFirst()) {
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, Inventory.class);
            startActivity(intent);
        }
        // failed login message
        else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
    }
}
