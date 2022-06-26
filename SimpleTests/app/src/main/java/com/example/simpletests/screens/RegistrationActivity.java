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

import DataPackage.DatabaseHelper;
import DataPackage.DatabaseOperation;
import helpers.Validator;

public class RegistrationActivity extends AppCompatActivity {

    EditText loginET, passwordET, emailET, nameET;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        loginET = findViewById(R.id.loginRegField);
        passwordET = findViewById(R.id.passwordRegField);
        emailET = findViewById(R.id.emailRegField);
        nameET = findViewById(R.id.nameRegField);
        databaseHelper = new DatabaseHelper(getApplicationContext());
    }

    public void registrationClick(View view) {
        String login = loginET.getText().toString();
        String password = passwordET.getText().toString();
        String email = emailET.getText().toString();
        String name = nameET.getText().toString();
        boolean validateFlag = true;


        if (login.length() > 0 && password.length() >0 && email.length() > 0 && name.length() > 0);
        else {
            Toast t = Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG);
            t.show();
            validateFlag = false;
        }

        if (Validator.registrationValidate(login, password, email) && validateFlag);
        else{
            openInfoDialog();
            validateFlag = false;
        }

        if (validateFlag && DatabaseOperation.createAccount(login, password, email, name, getApplicationContext()))
        {
            Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
            Toast t = Toast.makeText(this, "Успешная регистрация", Toast.LENGTH_LONG);
            t.show();
            startActivity(i);
        }
        else
        {
            Toast t = Toast.makeText(this, "Пользователь с таким логином или почтой уже существует", Toast.LENGTH_LONG);
            t.show();
        }
    }

    public void openInfoDialog(){
        RegistrationDialog registrationDialog = new RegistrationDialog();
        registrationDialog.show(getSupportFragmentManager(), "exampleDialog");
    }
}