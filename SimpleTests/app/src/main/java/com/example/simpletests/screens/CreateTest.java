package com.example.simpletests.screens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpletests.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import DataPackage.CurrentQuestion;
import DataPackage.CurrentTheme;
import DataPackage.CurrentUser;
import DataPackage.DatabaseHelper;
import DataPackage.DatabaseOperation;

public class CreateTest extends AppCompatActivity {

    ListView testList;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);
        testList = findViewById(R.id.testListView);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        updateList();

        testList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                CurrentTheme.Title = ((TextView)itemClicked).getText().toString();

                SQLiteDatabase db  = databaseHelper.getWritableDatabase();
                Cursor themesCursor = db.query(DatabaseHelper.TABLE_THEMES, null, null, null, null, null, null);
                while(themesCursor.moveToNext())
                {
                    int titleIndex = themesCursor.getColumnIndex(DatabaseHelper.COLUMN_THEMES_TITLE);
                    String titleTheme = themesCursor.getString(titleIndex);
                    if (titleTheme.equals(CurrentTheme.Title))
                    {
                        int idIndex = themesCursor.getColumnIndex(DatabaseHelper.COLUMN_THEMES_ID);
                        CurrentTheme.ThemeID =  themesCursor.getInt(idIndex);
                        break;
                    }
                }
                themesCursor.close();
                db.close();

                Intent i = new Intent(CreateTest.this, AddQuestionActivity.class);
                startActivity(i);
            }
        });
        testList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View item, int i, long l) {
                new AlertDialog.Builder(CreateTest.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Предупреждение")
                        .setMessage("Вы действительно хотите удалить тест?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (DatabaseOperation.deleteTheme(((TextView) item).getText().toString(), getApplicationContext()))
                                {
                                    Toast t = Toast.makeText(getApplicationContext(), "Успешно", Toast.LENGTH_LONG);
                                    t.show();
                                    updateList();
                                }
                                else
                                {
                                    Toast t = Toast.makeText(getApplicationContext(), "Что-то пошло не так", Toast.LENGTH_LONG);
                                    t.show();
                                }
                            }
                        })
                        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
                return true;
            }
        });

    }



    public void updateList() {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ArrayList<String> nameTests = new ArrayList<String>();
        Cursor cursor = database.query("Themes", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int themeNameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_THEMES_TITLE);
            int themeOwnerIdIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_THEMES_OWNER_ID);
            String themeName = cursor.getString(themeNameIndex);
            int themeOwnerId = cursor.getInt(themeOwnerIdIndex);

            if (themeOwnerId == CurrentUser.UserID) {
                nameTests.add(themeName);
            }
        }
        cursor.close();
        databaseHelper.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameTests);
        testList.setAdapter(adapter);
    }

    public void addTestButtonTest(View view) {
        Intent i = new Intent(CreateTest.this, CreateTestAddTest.class);
        startActivity(i);
    }
}