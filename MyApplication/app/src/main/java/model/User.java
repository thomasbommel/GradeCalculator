package model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import at.sallaberger.thomas.myapplication.MainActivity;
import database.DatabaseUtils;
import database.GradeCalculatorContract;

/**
 * Created by Sallaberger on 24.06.2017.
 */

public class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public User insertIntoDatabase(SQLiteDatabase db) {
        db.execSQL("INSERT INTO "+GradeCalculatorContract.User.TABLE_NAME+"(name) VALUES('" + name + "');");
        Log.d(this.getClass().toString(),"User '"+name+"' created");
        MainActivity.refreshMainActivity();
        return new User(name);
    }

    public User deleteFromDatabase(SQLiteDatabase db) {
        db.execSQL("DELETE FROM "+GradeCalculatorContract.User.TABLE_NAME+" WHERE " + GradeCalculatorContract.User.COLUMN_NAME + " = '"+name +"';");
        Log.d(this.getClass().toString(),"User '"+name+"' created");
        MainActivity.refreshMainActivity();
        return new User(name);
    }

    public User getUserFromDatabase(SQLiteDatabase db, String name) {
            Cursor findEntry = db.query(GradeCalculatorContract.User.TABLE_NAME, null, "name='"+name+"'", null, null, null, null);

        User foundUser;
        if(findEntry.getCount()>0){
            Log.wtf(this.getClass().toString(),"there is more then 1 user with name:"+name);
            throw new IllegalArgumentException();
        }

        findEntry.moveToFirst();
        String userName = DatabaseUtils.getCursorValue(findEntry,GradeCalculatorContract.User.COLUMN_NAME);
        return new User(userName);
    }



}
