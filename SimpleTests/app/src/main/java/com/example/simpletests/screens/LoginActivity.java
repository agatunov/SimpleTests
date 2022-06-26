package com.example.simpletests.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simpletests.R;

import DataPackage.CurrentUser;
import DataPackage.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText loginET, passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        loginET = findViewById(R.id.loginLoginActivity);
        passwordET = findViewById(R.id.passwordLoginActivity);
    }

    public void loginClick(View view) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        String login = loginET.getText().toString();
        String password = passwordET.getText().toString();
        boolean check = false;
        Cursor cursor = database.query("Users", null, null, null,null, null, null);
        while (cursor.moveToNext()) {
            int loginIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_LOGIN);
            int passwordIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_PASSWORD);
            int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_ID);
            int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_NAME);
            String loginDb = cursor.getString(loginIndex);
            String passwordDb = cursor.getString(passwordIndex);
            String nameDb = cursor.getString(nameIndex);
            int userIdDb = cursor.getInt(idIndex);
            if (login.equals(loginDb) && password.equals(passwordDb)) {
                databaseHelper.close();
                CurrentUser.UserID = userIdDb;
                CurrentUser.Name = nameDb;
                Intent i = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(i);
                check = true;
            }
        }
        if (!check){
            Toast toast = Toast.makeText(getApplicationContext(), "Пользователь не найден", Toast.LENGTH_LONG);
            toast.show();
        }
        cursor.close();
        databaseHelper.close();
    }
}