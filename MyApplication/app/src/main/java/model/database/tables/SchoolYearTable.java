package model.database.tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import at.sallaberger.thomas.myapplication.debug.DebugToast;
import at.sallaberger.thomas.myapplication.activities.MainActivity;
import model.database.utils.DataBaseTableBuilder;
import model.database.utils.DatabaseUtils;
import model.database.objects.SchoolYear;
import model.database.objects.User;

import static model.database.utils.DataBaseField.DataBaseFieldType.INTEGER;
import static model.database.utils.DataBaseField.DataBaseFieldType.TEXT;

/**
 * Created by Sallaberger on 07.07.2017.
 */

public class SchoolYearTable implements DataBaseTable<SchoolYear> {
    public static final String TABLE_NAME = "schoolyear";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_USERID = "user_id";

    private static SchoolYearTable instance;

    private SchoolYearTable(){
        //prevent usage
    }

    public static SchoolYearTable getInstance(){
        if(instance==null){
            instance = new SchoolYearTable();
        }
        return instance;
    }

    @Override
    public void insertIntoDataBase(SQLiteDatabase db, SchoolYear year) {
        db.execSQL("INSERT INTO "+ TABLE_NAME+"("+COLUMN_NAME+","+COLUMN_USERID+") VALUES('" + year.getName() + "','"+year.getUserId()+"');");
        Log.d(this.getClass().toString(),"User '"+year.getName()+"' created");
        MainActivity.getInstance().refreshMainActivity();
    }

    @Override
    public void deleteFromDataBase(SQLiteDatabase db, SchoolYear year) {
        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE " + COLUMN_NAME + " = '"+year.getName() +"' AND "+COLUMN_USERID +" ='"+year.getUserId()+"';");
        Log.d(this.getClass().toString(),"SchoolYear '"+year.getName()+"' deleted");
        MainActivity.getInstance().refreshMainActivity();
    }

    public static void deleteFromDataBaseForUser(SQLiteDatabase db, User user) {
        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE " + COLUMN_USERID + " = '"+user.getId() +"';");
        Log.d(SchoolYearTable.class.toString(),"SchoolYears for User '"+user.getName()+"' deleted");
        MainActivity.getInstance().refreshMainActivity();
    }

    private static Cursor getAllSchoolYearCursor(SQLiteDatabase db){
        return db.query(TABLE_NAME,null,null,null,null,null,null, null);
    }

    @Override
    public List<SchoolYear> getAllFromDataBase(SQLiteDatabase db){
        final Cursor schoolYearCursor = getAllSchoolYearCursor(db);
        List<SchoolYear> schoolYearList = new ArrayList<>();

        try {
            schoolYearList.clear();
            while (schoolYearCursor.moveToNext()) {
                String id = schoolYearCursor.getString(schoolYearCursor.getColumnIndex(_ID));
                String name = schoolYearCursor.getString(schoolYearCursor.getColumnIndex(COLUMN_NAME));
                String userId = schoolYearCursor.getString(schoolYearCursor.getColumnIndex(COLUMN_USERID));

                if(id==null || name == null){
                    DebugToast.showDebugToast(MainActivity.getInstance().getApplicationContext(), "id is:\"+id+\", name = \"+name+\", one of them was null.", Toast.LENGTH_SHORT);
                    throw new IllegalStateException("id is:"+id+", name = "+name+", one of them was null.");
                }
                schoolYearList.add(0,new SchoolYear(id, name, userId));
            }
        }catch (Exception e){
            Log.e(this.getClass().toString(),e.getMessage());
        }finally {
            schoolYearCursor.close();
        }
        Log.i(this.getClass().toString(),"getAllFromDataBase schoolyearListSize "+schoolYearList.size());
        return schoolYearList;
    }

    @Override
    public SchoolYear getFromDataBase(SQLiteDatabase db, SchoolYear element) {
        Cursor findEntry = db.query(TABLE_NAME, null, COLUMN_NAME+"='"+element.getName()+"' AND "+COLUMN_USERID+"='"+element.getUserId()+"'", null, null, null, null);
        if(findEntry.getCount()==0){
            return null;
        }else if(findEntry.getCount()>1){
            Log.e(User.class.toString(),"there is more then 1 schoolyear with name:"+element.getName()+" for the current user.");
            throw new IllegalStateException("there is more then 1 schoolyear with name:"+element.getName()+" for the current user.");
        }

        findEntry.moveToFirst();
        String schoolYearname = DatabaseUtils.getCursorValue(findEntry,COLUMN_NAME);
        String schoolYearId = DatabaseUtils.getCursorValue(findEntry,_ID);
        String schoolYearUserId = DatabaseUtils.getCursorValue(findEntry,COLUMN_USERID);
        return new SchoolYear(schoolYearId,schoolYearname, schoolYearUserId);
    }

    private static Cursor getAllSchoolYearCursorForUser(SQLiteDatabase db, User user){
        return db.query(TABLE_NAME, null, COLUMN_USERID+"='"+user.getId()+"'", null, null, null, null);
    }


    public List<SchoolYear> getAllFromDataBaseForUser(SQLiteDatabase db, User user){
        final Cursor schoolYearCursor = getAllSchoolYearCursorForUser(db, user);
        List<SchoolYear> schoolYearList = new ArrayList<>();

        try {
            schoolYearList.clear();
            while (schoolYearCursor.moveToNext()) {
                String id = schoolYearCursor.getString(schoolYearCursor.getColumnIndex(_ID));
                String name = schoolYearCursor.getString(schoolYearCursor.getColumnIndex(COLUMN_NAME));
                String userId = schoolYearCursor.getString(schoolYearCursor.getColumnIndex(COLUMN_USERID));

                if(id==null || name == null){
                    DebugToast.showDebugToast(MainActivity.getInstance().getApplicationContext(), "id is:\"+id+\", name = \"+name+\", one of them was null.", Toast.LENGTH_SHORT);
                    throw new IllegalStateException("id is:"+id+", name = "+name+", one of them was null.");
                }
                schoolYearList.add(0,new SchoolYear(id, name, userId));
            }
        }catch (Exception e){
            Log.e(this.getClass().toString(),e.getMessage());
        }finally {
            schoolYearCursor.close();
        }
        Log.i(this.getClass().toString(),"getAllFromDataBase schoolyearListSize "+schoolYearList.size());
        return schoolYearList;
    }

    @Override
    public void dropTable(SQLiteDatabase db) {
        db.execSQL(dropTableString+TABLE_NAME);
    }

    @Override
    public void createTable(SQLiteDatabase db) {
        Log.i(this.getClass().toString(),"------------ table "+TABLE_NAME+  " created");
        String createSchoolYearTableString = new DataBaseTableBuilder()
                .addTableName(TABLE_NAME)
                .addIDColumn()
                .addParameter(COLUMN_NAME, TEXT)
                .addParameter(COLUMN_USERID, INTEGER)
                .build();
        db.execSQL(createSchoolYearTableString);
    }
}
