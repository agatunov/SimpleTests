package com.example.simpletests.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simpletests.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import DataPackage.CurrentTheme;
import DataPackage.CurrentUser;
import DataPackage.DatabaseHelper;

public class AddQuestionActivity extends AppCompatActivity {

    ListView questionList;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        questionList = findViewById(R.id.testListView2);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        updateList();
    }


    public void updateList() {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ArrayList<String> nameQuestion = new ArrayList<String>();
        Cursor cursor = database.query("Questions", null, null, null, null, null, null);
        while(cursor.moveToNext()) {
            int questionNameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_QUESTIONS_TITLE);
            int questionThemeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_QUESTIONS_THEME_ID);
            String questionName = cursor.getString(questionNameIndex);
            int questionThemeOwnerId = cursor.getInt(questionThemeIndex);
            if (questionThemeOwnerId == CurrentTheme.ThemeID)
            {
                nameQuestion.add(questionName);
            }

        }
        cursor.close();
        databaseHelper.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameQuestion);
        questionList.setAdapter(adapter);
    }

    public void addTestButtonTest2(View view) {
        Intent i = new Intent(AddQuestionActivity.this, AddQuestionCreateActivity.class);
        startActivity(i);
    }
}