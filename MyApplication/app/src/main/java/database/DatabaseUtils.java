package database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Arrays;

/**
 * Created by Sallaberger on 24.06.2017.
 */

public class DatabaseUtils {

    public static void createDatabaseTable(String name, String... parameters){
        String SQL_CREATE_TABLE = "CREATE TABLE "+name+" (";

        for(int i=0;i<parameters.length;i++){
            if(i<parameters.length-1){
                SQL_CREATE_TABLE+=parameters[i]+",";
            }else{
                SQL_CREATE_TABLE+=parameters[i]+");";
            }
        }
    }

    @Deprecated
    public static Cursor getAllUsers(SQLiteDatabase db){
       return db.query(GradeCalculatorContract.User.TABLE_NAME,null,null,null,null,null,null, null);
    }

    public static String getCursorValue(Cursor cursor, String columnName){
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }



}
