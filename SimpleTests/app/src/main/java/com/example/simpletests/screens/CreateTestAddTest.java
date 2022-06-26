package com.example.simpletests.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simpletests.R;

import DataPackage.CurrentUser;
import DataPackage.DatabaseHelper;
import DataPackage.DatabaseOperation;

public class CreateTestAddTest extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText titleField, codeField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test_add_test);
        titleField = findViewById(R.id.titleAddTestField);
        codeField = findViewById(R.id.codeAddTestRegField);
        databaseHelper = new DatabaseHelper(getApplicationContext());
    }

    public void addTestClick(View view) {

        boolean validateFlag = true;
        String title = titleField.getText().toString();
        String code = codeField.getText().toString();
        if (title.length() < 1 || code.length() < 1) {
            Toast t = Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG);
            t.show();
            validateFlag = false;
        }

        if (validateFlag && DatabaseOperation.createTest(title, code, getApplicationContext()))
        {
            Toast t = Toast.makeText(this, "Успешно", Toast.LENGTH_LONG);
            t.show();
            Intent i = new Intent(CreateTestAddTest.this, CreateTest.class);
            startActivity(i);
        }
        else

        {
            Toast t = Toast.makeText(this, "Тест с таким названием или кодом уже существует", Toast.LENGTH_LONG);
            t.show();
        }
    }
}