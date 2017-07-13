package model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import at.sallaberger.thomas.myapplication.DebugToast;
import at.sallaberger.thomas.myapplication.MainActivity;
import database.DataBaseTableBuilder;
import database.DatabaseUtils;

import static database.DataBaseField.DataBaseFieldType.BOOLEAN;
import static database.DataBaseField.DataBaseFieldType.TEXT;
import static database.DataBaseField.Modifiers.DEFAULT;
import static database.DataBaseField.Modifiers.NOT_NULL;
import static database.DataBaseField.Modifiers.TRUE;


/**
 * Created by Sallaberger on 07.07.2017.
 */

public class UserTable implements DataBaseTable<User>{
    private static final String TABLE_NAME = "user";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_REMINDME = "remindme";

    private static UserTable instance;

    private UserTable(){
        //prevent usage
    }

    public static UserTable getInstance(){
        if(instance==null){
            instance = new UserTable();
        }
        return instance;
    }

    @Override
    public void insertIntoDataBase(SQLiteDatabase db, User user) {
        db.execSQL("INSERT INTO "+TABLE_NAME+"(name) VALUES('" + user.getName() + "');");
        Log.d(this.getClass().toString(),"User '"+user.getName()+"' created");
        MainActivity.getInstance().refreshMainActivity();
    }

    @Override
    public void deleteFromDataBase(SQLiteDatabase db, User user) {
        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE " + COLUMN_NAME + " = '"+user.getName() +"';");
        Log.d(this.getClass().toString(),"User '"+user.getName()+"' deleted");
        MainActivity.getInstance().refreshMainActivity();
    }

    @Override
    public List<User> getAllFromDataBase(SQLiteDatabase db) {
        final Cursor userCursor = getAllUsers(db);
        List<User> userList = new ArrayList<>();

        try {
            int i = 0;
            userList.clear();
            while (userCursor.moveToNext()) {
                String id = userCursor.getString(userCursor.getColumnIndex(_ID));
                String name = userCursor.getString(userCursor.getColumnIndex(COLUMN_NAME));

                if(id==null || name == null){
                    DebugToast.showDebugToast(MainActivity.getInstance().getApplicationContext(), "id is:\"+id+\", name = \"+name+\", one of them was null.", Toast.LENGTH_SHORT);
                    throw new IllegalStateException(_ID+" is:"+id+", "+COLUMN_NAME+" = "+name+", one of them was null.");
                }
                userList.add(0,new User(id,name));
            }
        }catch (Exception e){
            Log.e(this.getClass().toString(),e.getMessage());
        }
        finally {
            userCursor.close();
        }
        Log.i(this.getClass().toString(),"getAllFromDataBase userlistsize "+userList.size());
        return userList;
    }

    private Cursor getAllUsers(SQLiteDatabase db){
        return db.query(TABLE_NAME,null,null,null,null,null,null, null);
    }

    @Override
    public User getFromDataBase(SQLiteDatabase db, User user) {
        Cursor findEntry = db.query(TABLE_NAME, null, COLUMN_NAME+"='"+user.getName()+"'", null, null, null, null);
        if(findEntry.getCount()==0){
            return null;
        }else if(findEntry.getCount()>1){
            Log.wtf(User.class.toString(),"there is more then 1 user with name:"+user.getName());
            throw new IllegalStateException("there is more then 1 user with name:"+user.getName());
        }

        findEntry.moveToFirst();
        String userName = DatabaseUtils.getCursorValue(findEntry,COLUMN_NAME);
        String userId = DatabaseUtils.getCursorValue(findEntry,_ID);
        return new User(userId,userName);
    }

    @Override
    public void createTable(SQLiteDatabase db) {
        String createUserTableString = new DataBaseTableBuilder()
                .addTableName(TABLE_NAME)
                .addIDColumn()
                .addParameter(COLUMN_NAME, TEXT, NOT_NULL)
                .addParameter(COLUMN_REMINDME, BOOLEAN,NOT_NULL,DEFAULT,TRUE)
                .build();
        db.execSQL(createUserTableString);
    }

    @Override
    public void dropTable(SQLiteDatabase db) {
        db.execSQL(dropTableString+TABLE_NAME);
    }
}
