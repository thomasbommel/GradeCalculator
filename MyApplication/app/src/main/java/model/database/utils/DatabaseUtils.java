package model.database.utils;

import android.database.Cursor;

/**
 * Created by Sallaberger on 24.06.2017.
 */

public final class DatabaseUtils {

    private DatabaseUtils(){
        //prevent usage
    }

    public static String getCursorValue(Cursor cursor, String columnName){
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }
}
