package com.example.simpletests.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpletests.R;

import DataPackage.CurrentTheme;
import DataPackage.CurrentUser;
import DataPackage.DatabaseHelper;

public class MenuActivity extends AppCompatActivity implements SearchTestDialog.SearchTestDialogListener {

    TextView loginField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        loginField = findViewById(R.id.loginMenuTextView);
        loginField.setText(CurrentUser.Name);
    }

    public void createTestMenuButtonOnClick(View view) {
        Intent i = new Intent(MenuActivity.this, CreateTest.class);
        startActivity(i);
    }

    public void searchTestButtonOnClick(View view) {
        openDialog();
    }

    private void openDialog() {
        SearchTestDialog searchTestDialog = new SearchTestDialog();
        searchTestDialog.show(getSupportFragmentManager(), "example1");
    }

    @Override
    public void applyText(String code) {
        searchTest(code);
    }

    public void searchTest(String code) {
        if (code.length() > 0)
        {
            DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            Cursor themesCursor = db.query(DatabaseHelper.TABLE_THEMES, null, null, null, null, null, null);
            while(themesCursor.moveToNext())
            {
                int codeIndex = themesCursor.getColumnIndex(DatabaseHelper.COLUMN_THEMES_CODE);
                String codeDb = themesCursor.getString(codeIndex);
                if (codeDb.equals(code))
                {
                    int themeIdIndex = themesCursor.getColumnIndex(DatabaseHelper.COLUMN_THEMES_ID);
                    CurrentTheme.StartThemeID = themesCursor.getInt(themeIdIndex);
                    themesCursor.close();
                    Cursor resultCursor = db.query(DatabaseHelper.TABLE_RESULTS, null, null, null, null, null, null);
                    boolean resultFlag = true;
                    while(resultCursor.moveToNext())
                    {
                        int indexResultThemeId = resultCursor.getColumnIndex(DatabaseHelper.COLUMN_RESULTS_THEME_ID);
                        int indexThemeId = resultCursor.getInt(indexResultThemeId);
                        if (indexThemeId == CurrentTheme.StartThemeID)
                        {
                            resultFlag = false;
                            break;
                        }
                    }
                    resultCursor.close();
                    if (resultFlag)
                    {
                        Intent i = new Intent(MenuActivity.this, TestActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        Toast t = Toast.makeText(this, "Вы проходили этот тест", Toast.LENGTH_LONG);
                        t.show();
                    }

                }
            }
            themesCursor.close();
            db.close();
        }
        else
        {
            Toast t = Toast.makeText(this, "Проверьте правильность ввода", Toast.LENGTH_LONG);
            t.show();
        }
    }

    public void resultButtonOnClick(View view) {
        Intent i = new Intent(MenuActivity.this, ResultActivity.class);
        startActivity(i);

    }
}