package model.database.tables;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import model.database.objects.Test;

/**
 * Created by Sallaberger on 14.08.2017.
 */

public class TestTable implements DataBaseTable<Test>{


    @Override
    public void insertIntoDataBase(SQLiteDatabase db, Test element) {

    }

    @Override
    public void deleteFromDataBase(SQLiteDatabase db, Test element) {

    }

    @Override
    public List<Test> getAllFromDataBase(SQLiteDatabase db) {
        return null;
    }

    @Override
    public Test getFromDataBase(SQLiteDatabase db, Test element) {
        return null;
    }

    @Override
    public void createTable(SQLiteDatabase db) {

    }

    @Override
    public void dropTable(SQLiteDatabase db) {

    }
}
