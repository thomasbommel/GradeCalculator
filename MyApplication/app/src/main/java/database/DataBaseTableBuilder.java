package database;

import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import static database.DataBaseField.DataBaseFieldType.INTEGER;
import static database.DataBaseField.Modifiers.AUTOINCREMENT;
import static database.DataBaseField.Modifiers.PRIMARY_KEY;

/**
 * Created by Sallaberger on 24.06.2017.
 */

public class DataBaseTableBuilder {

    private StringBuilder createTableString = new StringBuilder();

    public DataBaseTableBuilder addTableName(String name){
        createTableString.append("CREATE TABLE IF NOT EXISTS "+name+" (");
        return this;
    }

    public DataBaseTableBuilder addIDColumn(){
        addParameter(BaseColumns._ID, INTEGER,PRIMARY_KEY, AUTOINCREMENT);
        return this;
    }

    /**
     * <b>use addName before the first call of addParameter</b>
     * @param parameter
     */
    public DataBaseTableBuilder addParameter(String parameter, DataBaseField.DatabaseEntity... modifiers){
        if(parameter == null){
            throw new IllegalArgumentException();
        }
        createTableString.append(parameter);
        for(int i=0;i<modifiers.length;i++) {
            if (i < modifiers.length - 1) {
                createTableString.append(" " + modifiers[i].toString() + "");
            } else {
                createTableString.append(" " + modifiers[i].toString());
            }
        }
        createTableString.append(",");
        return this;
    }

    public DataBaseTableBuilder addString(String string){
        createTableString.append(string);
        return this;
    }

    public String build(){
        String returnString = createTableString.toString();
        if(returnString.lastIndexOf(',')==returnString.length()-1){
            returnString = returnString.substring(0,returnString.lastIndexOf(","));
        }
        returnString+=");";
        return returnString;
    }


}
