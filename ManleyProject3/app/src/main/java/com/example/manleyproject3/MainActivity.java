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

        userDbHelper = new UserDatabaseHelper(this);
        usernameEdit = findViewById(R.id.username);
        passwordEdit = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void registerUser() {
        String username = usernameEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            SQLiteDatabase db = userDbHelper.getWritableDatabase();
            db.execSQL("INSERT INTO users (username, password) VALUES (?, ?)", new Object[]{username, password});
            db.close();
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loginUser() {
        String username = usernameEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = userDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND PASSWORD = ?", new String[]{username, password});

        if (cursor.moveToFirst()) {
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, Inventory.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
    }
}
