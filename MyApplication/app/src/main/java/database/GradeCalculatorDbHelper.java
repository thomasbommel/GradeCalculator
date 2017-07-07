package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static database.DataBaseField.DataBaseFieldType.*;
import static database.DataBaseField.Modifiers.*;
import static database.GradeCalculatorContract.*;

/**
 * Created by Sallaberger on 24.06.2017.
 */

public class GradeCalculatorDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gradeCalculator.db";
    private static final int DATABASE_VERSION = 1;

    public GradeCalculatorDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // --- ON CREATE ---------------------------------------------------
    @Override
    public void onCreate(SQLiteDatabase db) {
       createTables(db);
    }

    private void createTables(SQLiteDatabase db) {
        createUserTable(db);
        createSchoolYearTable(db);
    }

    private void createUserTable(SQLiteDatabase db) {
        String createUserTableString = new DataBaseTableBuilder()
                .addTableName(User.TABLE_NAME)
                .addIDColumn()
                .addParameter(User.COLUMN_NAME, TEXT, NOT_NULL)
                .addParameter(User.COLUMN_REMINDME, BOOLEAN,NOT_NULL,DEFAULT,TRUE)
                .build();
        db.execSQL(createUserTableString);
    }

    private void createSchoolYearTable(SQLiteDatabase db){
        String createSchoolYearTableString = new DataBaseTableBuilder()
                .addTableName(Schoolyear.TABLE_NAME)
                .addIDColumn()
                .addParameter(Schoolyear.COLUMN_NAME, TEXT)
                .addParameter(Schoolyear.COLUMN_USERID, INTEGER)
                .build();
        db.execSQL(createSchoolYearTableString);
    }

    private void createSubjectTable(SQLiteDatabase db){
        String createSubjectTableString = new DataBaseTableBuilder()
                .addTableName(Subject.TABLE_NAME)
                .addIDColumn()
                .addParameter(Subject.COLUMN_NAME, TEXT)
                .addParameter(Subject.COLUMN_GRADINGSCHEME_DEFAULT, INTEGER)
                .build();
        db.execSQL(createSubjectTableString);
    }

    private void createSubjectOfYearTable(SQLiteDatabase db){
        String createSubjectOfYearTableString = new DataBaseTableBuilder()
                .addTableName(SubjectOfYear.TABLE_NAME)
                .addParameter(SubjectOfYear.COLUMN_YEARID,INTEGER)
                .addParameter(SubjectOfYear.COLUMN_SUBJECTID, INTEGER)
                .build();
        db.execSQL(createSubjectOfYearTableString);
    }

    private void createGradingSchemeTable(SQLiteDatabase db){
        String createGradingSchemeTableString = new DataBaseTableBuilder()
                .addTableName(GradingScheme.TABLE_NAME)
                .addIDColumn()
                .addParameter(GradingScheme.COLUMN_NAME, TEXT)
                .build();
        db.execSQL(createGradingSchemeTableString);
    }

    private void createGradingKeyTable(SQLiteDatabase db){
        String createGradingGradingKeyTableString = new DataBaseTableBuilder()
                .addTableName(GradingKey.TABLE_NAME)
                .addIDColumn()
                .addParameter(GradingKey.COLUMN_GRADING_SCHEME_ID, INTEGER)
                .addParameter(GradingKey.COLUMN_MIN_PERCENT, FLOAT)
                .addParameter(GradingKey.COLUMN_MAX_PERCENT, FLOAT)
                .addParameter(GradingKey.COLUMN_GRADE, TEXT)
                .build();
        db.execSQL(createGradingGradingKeyTableString);
    }

    private void createTestTable(SQLiteDatabase db){
        String createTestTableString = new DataBaseTableBuilder()
                .addTableName(Test.TABLE_NAME)
                .addIDColumn()
                .addParameter(Test.COLUMN_NAME, TEXT)
                .addParameter(Test.COLUMN_SUBJECTID, INTEGER)
                .addParameter(Test.COLUMN_DATE, DATE)
                .addParameter(Test.COLUMN_MIN_POINTS, FLOAT)
                .addParameter(Test.COLUMN_MAX_POINTS, FLOAT)
                .addParameter(Test.COLUMN_GRADING_SCHEME,INTEGER)
                .build();
        db.execSQL(createTestTableString);
    }

    // --- ON UPGRADE ---------------------------------------------------
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ User.TABLE_NAME);//TODO change to the real updates, currently drops db, so all data is lost(!)
        db.execSQL("DROP TABLE IF EXISTS "+ Schoolyear.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Subject.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ GradingScheme.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ GradingKey.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Test.TABLE_NAME);
        onCreate(db);
    }
}
