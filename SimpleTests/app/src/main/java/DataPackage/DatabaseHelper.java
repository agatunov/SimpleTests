package DataPackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "simple_tests.db";
    private static final int SCHEMA = 2;

    // Users
    public static final String TABLE_USERS = "Users";
    public static final String COLUMN_USER_ID = "UserID";
    public static final String COLUMN_USER_LOGIN = "Login";
    public static final String COLUMN_USER_PASSWORD = "Password";
    public static final String COLUMN_USER_EMAIL = "Email";
    public static final String COLUMN_USER_NAME = "Name";

    // Themes
    public static final String TABLE_THEMES = "Themes";
    public static final String COLUMN_THEMES_ID = "ThemeID";
    public static final String COLUMN_THEMES_TITLE = "Title";
    public static final String COLUMN_THEMES_CODE = "Code";
    public static final String COLUMN_THEMES_OWNER_ID = "OwnerID";

    // Results
    public static final String TABLE_RESULTS = "Results";
    public static final String COLUMN_RESULTS_THEME_ID = "ThemeID";
    public static final String COLUMN_RESULTS_USER_ID = "UserID";
    public static final String COLUMN_RESULTS_POINTS = "Points";

    // Questions
    public static final String TABLE_QUESTIONS = "Questions";
    public static final String COLUMN_QUESTIONS_ID = "QuestionID";
    public static final String COLUMN_QUESTIONS_TITLE = "Title";
    public static final String COLUMN_QUESTIONS_THEME_ID = "ThemeID";

    // Answers
    public static final String TABLE_ANSWERS = "Answers";
    public static final String COLUMN_ANSWERS_ID = "AnswerID";
    public static final String COLUMN_ANSWERS_QUESTION_ID = "QuestionID";
    public static final String COLUMN_ANSWERS_TITLE = "Title";
    public static final String COLUMN_ANSWERS_TITLE_CORRECT = "Correct";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Users (" +
                        COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_USER_LOGIN + " TEXT NOT NULL UNIQUE," +
                        COLUMN_USER_PASSWORD + " TEXT NOT NULL," +
                        COLUMN_USER_EMAIL + " TEXT NOT NULL UNIQUE," +
                        COLUMN_USER_NAME + " TEXT NOT NULL);"
        );
        db.execSQL("CREATE TABLE Themes (" +
                COLUMN_THEMES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_THEMES_TITLE + " TEXT NOT NULL," +
                COLUMN_THEMES_CODE + " TEXT NOT NULL UNIQUE," +
                COLUMN_THEMES_OWNER_ID + " INTEGER NOT NULL," +
                "FOREIGN KEY (OwnerID) REFERENCES Users (UserID)" +
                "ON UPDATE CASCADE ON DELETE CASCADE);"
        );

        db.execSQL("CREATE TABLE Results (" +
                COLUMN_RESULTS_THEME_ID + " INTEGER NOT NULL," +
                COLUMN_RESULTS_USER_ID + " INTEGER NOT NULL," +
                COLUMN_RESULTS_POINTS + " TEXT NOT NULL," +
                "FOREIGN KEY (ThemeID) REFERENCES Themes (ThemeID)" +
                "ON UPDATE CASCADE ON DELETE CASCADE," +
                "FOREIGN KEY (UserID) REFERENCES Users (UserID)" +
                "ON UPDATE CASCADE ON DELETE CASCADE);"
        );
        db.execSQL("CREATE TABLE Questions (" +
                COLUMN_QUESTIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_QUESTIONS_TITLE + " TEXT NOT NULL," +
                COLUMN_QUESTIONS_THEME_ID + " INTEGER NOT NULL," +
                "FOREIGN KEY (ThemeID) REFERENCES Themes (ThemeID)" +
                "ON UPDATE CASCADE ON DELETE CASCADE);"
        );
        db.execSQL("CREATE TABLE Answers (" +
                COLUMN_ANSWERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_ANSWERS_QUESTION_ID + " INTEGER NOT NULL," +
                COLUMN_ANSWERS_TITLE + " TEXT NOT NULL," +
                COLUMN_ANSWERS_TITLE_CORRECT + " INTEGER NOT NULL," +
                "FOREIGN KEY (QuestionID) REFERENCES Questions (QuestionID)" +
                "ON UPDATE CASCADE ON DELETE CASCADE);"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THEMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWERS);
        onCreate(db);
    }






}
