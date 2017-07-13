package model;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.List;

/**
 * Created by Sallaberger on 07.0 7.2017.
 */

public interface DataBaseTable<T extends DataBaseTableEntity> extends BaseColumns {
    public static final String dropTableString = "DROP TABLE IF EXISTS ";

    public void insertIntoDataBase(SQLiteDatabase db, T element);

    public void deleteFromDataBase(SQLiteDatabase db, T element);

    public List<T> getAllFromDataBase(SQLiteDatabase db);

    public T getFromDataBase(SQLiteDatabase db, T element);

    public void createTable(SQLiteDatabase db);

    public void dropTable(SQLiteDatabase db);


}
