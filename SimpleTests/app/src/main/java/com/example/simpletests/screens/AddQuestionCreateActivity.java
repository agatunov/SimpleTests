package com.example.simpletests.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.simpletests.R;

import DataPackage.CurrentTheme;
import DataPackage.DatabaseHelper;
import DataPackage.DatabaseOperation;

public class AddQuestionCreateActivity extends AppCompatActivity {

    private EditText questionTitleET, answer1ET, answer2ET,answer3ET,answer4ET;
    private RadioButton rightAnswer1RB, rightAnswer2RB,rightAnswer3RB,rightAnswer4RB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question_create);

        questionTitleET = findViewById(R.id.titleAddQuestionField);
        answer1ET = findViewById(R.id.answer1EditText);
        answer2ET = findViewById(R.id.answer2EditText);
        answer3ET = findViewById(R.id.answer3EditText);
        answer4ET = findViewById(R.id.answer4EditText);
        rightAnswer1RB = findViewById(R.id.rightAnswer1);
        rightAnswer2RB = findViewById(R.id.rightAnswer2);
        rightAnswer3RB = findViewById(R.id.rightAnswer3);
        rightAnswer4RB = findViewById(R.id.rightAnswer4);

    }

    public void createQuestionOnClick(View view) {
        boolean validateFlag = true;
        boolean uniqueFlag = true;
        boolean checkedFlag = true;
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues questionValues = new ContentValues();
        int themeId = CurrentTheme.ThemeID;

        String title = questionTitleET.getText().toString(); Log.d("MyTags", "53");
        String answer1 = answer1ET.getText().toString(); Log.d("MyTags", "54");
        String answer2 = answer2ET.getText().toString(); Log.d("MyTags", "55");
        String answer3 = answer3ET.getText().toString(); Log.d("MyTags", "56");
        String answer4 = answer4ET.getText().toString(); Log.d("MyTags", "57");
        if (answer1.length() > 0 && answer2.length() > 0 && answer3.length() > 0 && answer4.length() > 0 && title.length() > 0){
            Cursor questionCursor = db.query(DatabaseHelper.TABLE_QUESTIONS, null, null, null, null, null, null); Log.d("MyTags", "59");
            while(questionCursor.moveToNext())
            {
                int titleIndex = questionCursor.getColumnIndex(DatabaseHelper.COLUMN_QUESTIONS_TITLE); Log.d("MyTags", "62");
                int themeIdIndex = questionCursor.getColumnIndex(DatabaseHelper.COLUMN_QUESTIONS_THEME_ID); Log.d("MyTags", "63");
                String titleDb = questionCursor.getString(titleIndex); Log.d("MyTags", "64");
                int themeIdDb = questionCursor.getInt(themeIdIndex);
                if (titleDb.equals(title) && themeIdDb == themeId)
                {
                    Toast t = Toast.makeText(this, "Такой вопрос уже существует", Toast.LENGTH_LONG);
                    t.show();
                    uniqueFlag = false;
                    break;
                }
            }
            questionCursor.close();
        }
        else {
            validateFlag = false;
            Toast t = Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG);
            t.show();
        }

        int questionId = -1;
        checkedFlag = getRightAnswer() != 5;
        if (validateFlag && uniqueFlag && checkedFlag)
        {
            questionValues.put(DatabaseHelper.COLUMN_QUESTIONS_THEME_ID, themeId);
            questionValues.put(DatabaseHelper.COLUMN_QUESTIONS_TITLE, title);
            db.insert(DatabaseHelper.TABLE_QUESTIONS, null, questionValues);

            Cursor questionCursor = db.query(DatabaseHelper.TABLE_QUESTIONS, null, null, null, null, null, null);
            while(questionCursor.moveToNext())
            {
                int titleIndex = questionCursor.getColumnIndex(DatabaseHelper.COLUMN_QUESTIONS_TITLE);
                int themeIdIndex = questionCursor.getColumnIndex(DatabaseHelper.COLUMN_QUESTIONS_THEME_ID);
                String titleDb = questionCursor.getString(titleIndex);
                int themeIdDb = questionCursor.getInt(themeIdIndex);

                if (titleDb.equals(title) && themeIdDb == themeId)
                {
                    int questionIdIndex = questionCursor.getColumnIndex(DatabaseHelper.COLUMN_QUESTIONS_ID);
                    questionId = questionCursor.getInt(questionIdIndex);
                    break;
                }
            }
            questionCursor.close();
            try
            {
                ContentValues answer1Values = new ContentValues();
                ContentValues answer2Values = new ContentValues();
                ContentValues answer3Values = new ContentValues();
                ContentValues answer4Values = new ContentValues();
                switch (getRightAnswer()){
                    case 1:
                        answer1Values.put(DatabaseHelper.COLUMN_ANSWERS_QUESTION_ID, questionId);
                        answer1Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE, answer1);
                        answer1Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE_CORRECT, 1);

                        answer2Values.put(DatabaseHelper.COLUMN_ANSWERS_QUESTION_ID, questionId);
                        answer2Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE, answer2);
                        answer2Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE_CORRECT, 0);

                        answer3Values.put(DatabaseHelper.COLUMN_ANSWERS_QUESTION_ID, questionId);
                        answer3Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE, answer3);
                        answer3Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE_CORRECT, 0);

                        answer4Values.put(DatabaseHelper.COLUMN_ANSWERS_QUESTION_ID, questionId);
                        answer4Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE, answer4);
                        answer4Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE_CORRECT, 0);
                        break;
                    case 2:
                        answer1Values.put(DatabaseHelper.COLUMN_ANSWERS_QUESTION_ID, questionId);
                        answer1Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE, answer1);
                        answer1Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE_CORRECT, 0);

                        answer2Values.put(DatabaseHelper.COLUMN_ANSWERS_QUESTION_ID, questionId);
                        answer2Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE, answer2);
                        answer2Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE_CORRECT, 1);

                        answer3Values.put(DatabaseHelper.COLUMN_ANSWERS_QUESTION_ID, questionId);
                        answer3Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE, answer3);
                        answer3Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE_CORRECT, 0);

                        answer4Values.put(DatabaseHelper.COLUMN_ANSWERS_QUESTION_ID, questionId);
                        answer4Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE, answer4);
                        answer4Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE_CORRECT, 0);
                        break;
                    case 3:
                        answer1Values.put(DatabaseHelper.COLUMN_ANSWERS_QUESTION_ID, questionId);
                        answer1Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE, answer1);
                        answer1Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE_CORRECT, 0);

                        answer2Values.put(DatabaseHelper.COLUMN_ANSWERS_QUESTION_ID, questionId);
                        answer2Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE, answer2);
                        answer2Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE_CORRECT, 0);

                        answer3Values.put(DatabaseHelper.COLUMN_ANSWERS_QUESTION_ID, questionId);
                        answer3Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE, answer3);
                        answer3Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE_CORRECT, 1);

                        answer4Values.put(DatabaseHelper.COLUMN_ANSWERS_QUESTION_ID, questionId);
                        answer4Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE, answer4);
                        answer4Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE_CORRECT, 0);
                        break;
                    case 4:
                        answer1Values.put(DatabaseHelper.COLUMN_ANSWERS_QUESTION_ID, questionId);
                        answer1Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE, answer1);
                        answer1Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE_CORRECT, 0);

                        answer2Values.put(DatabaseHelper.COLUMN_ANSWERS_QUESTION_ID, questionId);
                        answer2Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE, answer2);
                        answer2Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE_CORRECT, 0);

                        answer3Values.put(DatabaseHelper.COLUMN_ANSWERS_QUESTION_ID, questionId);
                        answer3Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE, answer3);
                        answer3Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE_CORRECT, 0);

                        answer4Values.put(DatabaseHelper.COLUMN_ANSWERS_QUESTION_ID, questionId);
                        answer4Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE, answer4);
                        answer4Values.put(DatabaseHelper.COLUMN_ANSWERS_TITLE_CORRECT, 1);
                        break;

                }
                db.insert(DatabaseHelper.TABLE_ANSWERS, null, answer1Values);
                db.insert(DatabaseHelper.TABLE_ANSWERS, null, answer2Values);
                db.insert(DatabaseHelper.TABLE_ANSWERS, null, answer3Values);
                db.insert(DatabaseHelper.TABLE_ANSWERS, null, answer4Values);
                db.close();
                Intent i = new Intent(AddQuestionCreateActivity.this, AddQuestionActivity.class);
                startActivity(i);

            }
            catch (Exception e)
            {
                Toast t = Toast.makeText(this, "Что-то пошло не так", Toast.LENGTH_LONG);
                t.show();
            }

        }
        else
        {
            Toast t = Toast.makeText(this, "Проверьте заполненность данных", Toast.LENGTH_LONG);
        }







        db.close();

    }

    public int getRightAnswer()
    {
        if (rightAnswer1RB.isChecked()) return 1;
        if (rightAnswer2RB.isChecked()) return 2;
        if (rightAnswer3RB.isChecked()) return 3;
        if (rightAnswer4RB.isChecked()) return 4;
        return 5;
    }
}