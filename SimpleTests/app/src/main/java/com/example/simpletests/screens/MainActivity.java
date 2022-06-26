package com.example.simpletests.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.simpletests.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loginClick(View view) {
        Intent newLoginScreen = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(newLoginScreen);
    }

    public void registrationOnClick(View view) {
        Intent newRegistrationScreen = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(newRegistrationScreen);
    }
}