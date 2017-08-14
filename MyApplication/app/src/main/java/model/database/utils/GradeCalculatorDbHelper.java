package model.database.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import model.database.tables.SchoolYearTable;
import model.database.tables.UserTable;

import static model.database.utils.DataBaseField.DataBaseFieldType.DATE;
import static model.database.utils.DataBaseField.DataBaseFieldType.FLOAT;
import static model.database.utils.DataBaseField.DataBaseFieldType.INTEGER;
import static model.database.utils.DataBaseField.DataBaseFieldType.TEXT;
import static model.database.utils.GradeCalculatorContract.GradingKey;
import static model.database.utils.GradeCalculatorContract.GradingScheme;
import static model.database.utils.GradeCalculatorContract.Subject;
import static model.database.utils.GradeCalculatorContract.SubjectOfYear;
import static model.database.utils.GradeCalculatorContract.Test;

/**
 * Created by Sallaberger on 07.07.2017.
 */

public class GradeCalculatorDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gradeCalculator_000001.db";
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
        Log.i(this.getClass().toString(),"--------- TABLE CREATION STARTED -------------\n");
        UserTable.getInstance().createTable(db);
        SchoolYearTable.getInstance().createTable(db);
        createSubjectTable(db);
        createSubjectOfYearTable(db);
        createGradingSchemeTable(db);
        createGradingKeyTable(db);
        createTestTable(db);
        Log.i(this.getClass().toString(),"--------- TABLE CREATION ENDED -------------\n");
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
        UserTable.getInstance().dropTable(db);
        SchoolYearTable.getInstance().dropTable(db);
        final String dropTable = "DROP TABLE IF EXISTS ";
        //TODO
        db.execSQL(dropTable+ Subject.TABLE_NAME);
        db.execSQL(dropTable+ GradingScheme.TABLE_NAME);
        db.execSQL(dropTable+ GradingKey.TABLE_NAME);
        db.execSQL(dropTable+ Test.TABLE_NAME);
        onCreate(db);
    }
}
