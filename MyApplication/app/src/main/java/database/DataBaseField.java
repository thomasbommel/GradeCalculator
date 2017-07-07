package database;

/**
 * Created by Sallaberger on 24.06.2017.
 */

public class DataBaseField {

    public enum DataBaseFieldType implements DatabaseEntity{
        TEXT, BOOLEAN, INTEGER, FLOAT, DATE, TIMESTAMP;
    }

    public enum Modifiers implements DatabaseEntity{
        NULL, DEFAULT, NOT, CURRENT_TIMESTAMP,PRIMARY_KEY, AUTOINCREMENT, NOT_NULL, TRUE, FALSE;

        @Override
        public String toString(){
            if(this == NOT_NULL) {
                return "NOT NULL";
            }
            if(this==PRIMARY_KEY){
                return "PRIMARY KEY";
            }

            return this.name();
        }
    }

    public interface DatabaseEntity{}

}
