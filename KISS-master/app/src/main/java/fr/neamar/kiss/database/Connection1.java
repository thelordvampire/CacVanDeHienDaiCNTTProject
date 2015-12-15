package fr.neamar.kiss.database;

/**
 * Created by Bao on 12/15/2015.
 */

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Connection1 {

    private static SQLiteDatabase database;

    public static void createDatabase(Activity activity)
    {
        try {
            database = activity.openOrCreateDatabase("data.db", activity.MODE_PRIVATE, null);
        }catch(Exception e)
        {
            Log.d("db", e.getMessage());
        }
    }

    public static void createTable()
    {
        String sql = "create table IF NOT EXISTS tblApp(name text)";
        database.execSQL(sql);
    }

    public static void insertValue(String name)
    {
        ContentValues value = new ContentValues();
        value.put("name", name);


        database.insert("tblApp", null, value);
    }

    public static void deleteValue()
    {
        database.delete("tblApp", null, null);
    }

    public static List<String> getAllValue()
    {
        List<String> listApp = null;
        Cursor cursor = database.query("tblApp", null, null, null, null, null, null);
        if(cursor.moveToFirst())
        {
            listApp = new ArrayList<>();
            while(!cursor.isAfterLast())
            {
                listApp.add(cursor.getString(0));

                cursor.moveToNext();
            }
            cursor.close();
        }

        return listApp;
    }
}
