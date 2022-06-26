package com.example.simpletests.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpletests.R;

import java.util.ArrayList;

import DataPackage.CurrentTheme;
import DataPackage.CurrentUser;
import DataPackage.DatabaseHelper;
import helpers.PassingTestHelper;

public class TestActivity extends AppCompatActivity {

    public int ThemeId;
    public int Score = 0;
    public int CurrentQuestionPos = 0;
    public int CountQuestions;
    public ArrayList<Integer> questionIds = new ArrayList<Integer>();
    public int RightAnswer;
    public int PosRightAnswer = 1;

    private TextView questionTitleTV;
    private Button answer1Btn, answer2Btn, answer3Btn, answer4Btn;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ThemeId = CurrentTheme.StartThemeID;
        databaseHelper = new DatabaseHelper(getApplicationContext());
        getQuestions();
        setCountQuestions();

        questionTitleTV = findViewById(R.id.questionTitleTV);
        answer1Btn = findViewById(R.id.answer1Button);
        answer2Btn = findViewById(R.id.answer2Button);
        answer3Btn = findViewById(R.id.answer3Button);
        answer4Btn = findViewById(R.id.answer4Button);

        nextQuestion();

    }

    public void getQuestions(){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor questionsCursor = db.query(DatabaseHelper.TABLE_QUESTIONS, null, null, null, null, null, null);
        while(questionsCursor.moveToNext())
        {
            int indexThemeId = questionsCursor.getColumnIndex(DatabaseHelper.COLUMN_QUESTIONS_THEME_ID);
            int themeId = questionsCursor.getInt(indexThemeId);
            if (themeId == ThemeId)
            {
                int indexQuestionId = questionsCursor.getColumnIndex(DatabaseHelper.COLUMN_QUESTIONS_ID);
                int questionId = questionsCursor.getInt(indexQuestionId);
                questionIds.add(questionId);
            }
        }
        questionsCursor.close();
        db.close();
    }
    public void setCountQuestions() {
        CountQuestions = questionIds.size();
    }
    public void nextQuestion() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor questionsCursor = db.query(DatabaseHelper.TABLE_QUESTIONS, null, null, null, null, null, null);
        while(questionsCursor.moveToNext())
        {
            int indexQuestionId = questionsCursor.getColumnIndex(DatabaseHelper.COLUMN_QUESTIONS_ID);
            int questionId = questionsCursor.getInt(indexQuestionId);
            if (questionId == questionIds.get(CurrentQuestionPos))
            {
                int indexTitle = questionsCursor.getColumnIndex(DatabaseHelper.COLUMN_QUESTIONS_TITLE);
                questionTitleTV.setText(questionsCursor.getString(indexTitle));
                break;
            }
        }
        questionsCursor.close();

        Cursor answersCursor = db.query(DatabaseHelper.TABLE_ANSWERS, null, null, null, null, null ,null);
        ArrayList<String> answers = new ArrayList<>();
        int i = 1;
        while(answersCursor.moveToNext())
        {
            int indexQuestionId = answersCursor.getColumnIndex(DatabaseHelper.COLUMN_ANSWERS_QUESTION_ID);
            int questionId = answersCursor.getInt(indexQuestionId);
            if (questionId == questionIds.get(CurrentQuestionPos))
            {

                int indexTitleAnswer = answersCursor.getColumnIndex(DatabaseHelper.COLUMN_ANSWERS_TITLE);
                answers.add(answersCursor.getString(indexTitleAnswer));
                int indexRight = answersCursor.getColumnIndex(DatabaseHelper.COLUMN_ANSWERS_TITLE_CORRECT);
                RightAnswer = answersCursor.getInt(indexRight);
                if (RightAnswer == 1)
                {
                    PosRightAnswer = i;
                }
                i++;
            }
        }
        answersCursor.close();

        answer1Btn.setText(answers.get(0));
        answer2Btn.setText(answers.get(1));
        answer3Btn.setText(answers.get(2));
        answer4Btn.setText(answers.get(3));
        CurrentQuestionPos += 1;
        db.close();
    }


    public void answer1ButtonOnClick(View view) {
        if (PosRightAnswer == 1)
        {
            Score += 1;
        }

        if (CurrentQuestionPos < CountQuestions)
        {
            nextQuestion();
        }
        else
        {
            endTest();
        }
    }



    public void answer2ButtonOnClick(View view) {
        if (PosRightAnswer == 2)
        {
            Score += 1;
        }
        if (CurrentQuestionPos < CountQuestions)
        {
            nextQuestion();
        }
        else
        {
            endTest();
        }
    }
    public void answer3ButtonOnClick(View view) {
        if (PosRightAnswer == 3)
        {
            Score += 1;
        }
        if (CurrentQuestionPos < CountQuestions)
        {
            nextQuestion();
        }
        else
        {
            endTest();
        }
    }
    public void answer4ButtonOnClick(View view) {
        if (PosRightAnswer == 4)
        {
            Score += 1;
        }
        if (CurrentQuestionPos < CountQuestions)
        {
            nextQuestion();
        }
        else
        {
            endTest();
        }
    }

    private void endTest() {
        String score = Score + " из " + CountQuestions;
        ContentValues resultValues = new ContentValues();
        resultValues.put(DatabaseHelper.COLUMN_RESULTS_THEME_ID, ThemeId);
        resultValues.put(DatabaseHelper.COLUMN_RESULTS_USER_ID, CurrentUser.UserID);
        resultValues.put(DatabaseHelper.COLUMN_RESULTS_POINTS, score);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.insert(DatabaseHelper.TABLE_RESULTS, null, resultValues);
        Intent i = new Intent(TestActivity.this, MenuActivity.class);
        db.close();
        Toast t = Toast.makeText(this, score, Toast.LENGTH_LONG);
        t.show();
        startActivity(i);

    }
}