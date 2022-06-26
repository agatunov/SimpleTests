package DataPackage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperation {

    private static DatabaseHelper databaseHelper;

    public static boolean createAccount(String login, String password, String email, String name, Context context)
    {
        databaseHelper = new DatabaseHelper(context);
        if (isRegUnique(login, password, databaseHelper))
        {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_USER_LOGIN, login);
            values.put(DatabaseHelper.COLUMN_USER_PASSWORD, password);
            values.put(DatabaseHelper.COLUMN_USER_EMAIL, email);
            values.put(DatabaseHelper.COLUMN_USER_NAME, name);
            db.insert(DatabaseHelper.TABLE_USERS, null, values);
            databaseHelper.close();
            return  true;
        }
        else
        {
            return false;
        }




    }
    public static boolean isRegUnique(String login, String email, DatabaseHelper databaseHelper)
    {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor usersCursor = db.query(DatabaseHelper.TABLE_USERS, null, null, null, null, null, null);
        while(usersCursor.moveToNext())
        {
            int loginIndex = usersCursor.getColumnIndex(DatabaseHelper.COLUMN_USER_LOGIN);
            int emailIndex = usersCursor.getColumnIndex(DatabaseHelper.COLUMN_USER_EMAIL);
            String loginDb = usersCursor.getString(loginIndex);
            String emailDb = usersCursor.getString(emailIndex);
            if (loginDb.equals(login) || emailDb.equals(email))
            {
                usersCursor.close();
                db.close();
                return false;
            }
        }
        usersCursor.close();
        db.close();
        return true;
    }

   public static boolean createTest(String title, String code, Context context)
   {
       databaseHelper = new DatabaseHelper(context);
       if (isTestUnique(title, code, databaseHelper))
       {
           SQLiteDatabase db = databaseHelper.getWritableDatabase();
           ContentValues values = new ContentValues();
           values.put(DatabaseHelper.COLUMN_THEMES_TITLE, title);
           values.put(DatabaseHelper.COLUMN_THEMES_CODE, code);
           values.put(DatabaseHelper.COLUMN_THEMES_OWNER_ID, CurrentUser.UserID);
           db.insert(DatabaseHelper.TABLE_THEMES, null, values);
           db.close();
           databaseHelper.close();
           return true;
       }
       databaseHelper.close();
       return false;

   }
   public static boolean isTestUnique(String title, String code, DatabaseHelper databaseHelper)
   {
       SQLiteDatabase db = databaseHelper.getWritableDatabase();
       Cursor themesCursor = db.query(DatabaseHelper.TABLE_THEMES, null, null, null, null, null, null);
       while(themesCursor.moveToNext())
       {
           int titleIndex = themesCursor.getColumnIndex(DatabaseHelper.COLUMN_THEMES_TITLE);
           int codeIndex = themesCursor.getColumnIndex(DatabaseHelper.COLUMN_THEMES_CODE);
           String titleDb = themesCursor.getString(titleIndex);
           String codeDb = themesCursor.getString(codeIndex);
           if (titleDb.equals(title) || codeDb.equals(code))
           {
               themesCursor.close();
               db.close();
               return false;
           }
       }
       themesCursor.close();
       db.close();
       return true;
   }

   public static boolean deleteTheme(String title, Context context) {
        databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int id = findIdTheme(title, databaseHelper);
        if (id == -1) {
            db.close();
            Toast t = Toast.makeText(context, "" + id, Toast.LENGTH_LONG);
            t.show();
            return false;
        }

            int deleteCount = db.delete(DatabaseHelper.TABLE_THEMES, DatabaseHelper.COLUMN_THEMES_ID + "= " + id, null);
            Log.d("myLogs", "121"+ deleteCount);
            db.close();
            Log.d("myLogs", "123"+ deleteCount);
            databaseHelper.close();
            Log.d("myLogs", "125"+ deleteCount);
            return true;

        /*
        catch (Exception e)
        {
            Toast t = Toast.makeText(context, "" + id, Toast.LENGTH_LONG);
            t.show();
            Log.d("myLogs", ""+ e.toString());
            db.close();
            databaseHelper.close();
            return false;
        }

         */




   }

   public static int findIdTheme (String title, DatabaseHelper databaseHelper)
   {
       SQLiteDatabase db  = databaseHelper.getWritableDatabase();
       Cursor themesCursor = db.query(DatabaseHelper.TABLE_THEMES, null, null, null, null, null, null);
       while(themesCursor.moveToNext())
       {
           int titleIndex = themesCursor.getColumnIndex(DatabaseHelper.COLUMN_THEMES_TITLE);
           String titleTheme = themesCursor.getString(titleIndex);
           if (titleTheme.equals(title))
           {
               int idIndex = themesCursor.getColumnIndex(DatabaseHelper.COLUMN_THEMES_ID);
               int id = themesCursor.getInt(idIndex);

               themesCursor.close();
               db.close();
               return id;
           }
       }
       themesCursor.close();
       db.close();
       return -1;
   }

   public static ArrayList<Integer> getQuestionListByThemeId(int id, DatabaseHelper databaseHelper)
   {
       ArrayList<Integer> result = new ArrayList<Integer>();
       SQLiteDatabase db = databaseHelper.getWritableDatabase();
       Cursor questionCursor = db.query(DatabaseHelper.TABLE_QUESTIONS, null, null, null, null, null, null);
       while(questionCursor.moveToNext())
       {
           int idThemeIndex = questionCursor.getColumnIndex(DatabaseHelper.COLUMN_QUESTIONS_THEME_ID);
           int idTheme = questionCursor.getInt(idThemeIndex);
           if (idTheme == id)
           {
               int questionIdIndex = questionCursor.getColumnIndex(DatabaseHelper.COLUMN_QUESTIONS_ID);
               int questionId = questionCursor.getInt(questionIdIndex);
               result.add(questionId);
           }
       }
       questionCursor.close();
       db.close();
       return result;
   }


}
