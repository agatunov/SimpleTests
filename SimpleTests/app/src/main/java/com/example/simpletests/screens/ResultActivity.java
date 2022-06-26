package com.example.simpletests.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.simpletests.R;

import java.util.ArrayList;

import DataPackage.CurrentUser;
import DataPackage.DatabaseHelper;

public class ResultActivity extends AppCompatActivity {

    ListView resultList;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        resultList = findViewById(R.id.resultListView);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        updateList();
    }

    private void updateList() {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ArrayList<String> results = new ArrayList<String>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_RESULTS, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int indexUserId = cursor.getColumnIndex(DatabaseHelper.COLUMN_RESULTS_USER_ID);
            if (cursor.getInt(indexUserId) == CurrentUser.UserID)
            {
                int indexThemeId = cursor.getColumnIndex(DatabaseHelper.COLUMN_RESULTS_THEME_ID);
                Cursor themeCursor = database.query(DatabaseHelper.TABLE_THEMES, null, null, null, null, null, null);
                while(themeCursor.moveToNext())
                {
                    int indexThemeIdDb = themeCursor.getColumnIndex(DatabaseHelper.COLUMN_THEMES_ID);
                    int themeId = themeCursor.getInt(indexThemeIdDb);
                    if (themeId == cursor.getInt(indexThemeId))
                    {
                        int indexScore = cursor.getColumnIndex(DatabaseHelper.COLUMN_RESULTS_POINTS);
                        int indexThemeTitle = themeCursor.getColumnIndex(DatabaseHelper.COLUMN_THEMES_TITLE);
                        String line = themeCursor.getString(indexThemeTitle) + " " + cursor.getString(indexScore);
                        results.add(line); Log.d("MyLogs", line);
                    }
                }
                themeCursor.close();
            }
        }
        cursor.close();
        databaseHelper.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results);
        resultList.setAdapter(adapter);
    }
}