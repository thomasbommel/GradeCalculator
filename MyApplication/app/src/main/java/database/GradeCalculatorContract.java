package database;

import android.provider.BaseColumns;

/**
 * Created by Sallaberger on 24.06.2017.
 */

public class GradeCalculatorContract implements BaseColumns{

    private GradeCalculatorContract() {}

    public static class User extends GradeCalculatorContract  {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_REMINDME = "remindme";
    }

    public static class Schoolyear extends GradeCalculatorContract{
        public static final String TABLE_NAME = "schoolyear";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_USERID = "user_id";
    }

    public static class Subject extends GradeCalculatorContract{
        public static final String TABLE_NAME = "subject";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_GRADINGSCHEME_DEFAULT = "def_grading_scheme";
    }

    public static class SubjectOfYear extends GradeCalculatorContract{
        public static final String TABLE_NAME = "subject_of_year";
        public static final String COLUMN_YEARID = "year_id";
        public static final String COLUMN_SUBJECTID = "subject_id";
    }

    public static class GradingScheme extends GradeCalculatorContract{
        public static final String TABLE_NAME = "grading_scheme";
        public static final String COLUMN_NAME = "name";
    }

    public static class GradingKey extends GradeCalculatorContract{
        public static final String TABLE_NAME = "grading_key";
        public static final String COLUMN_GRADING_SCHEME_ID = "grading_scheme";
        public static final String COLUMN_MIN_PERCENT = "min_percent";
        public static final String COLUMN_MAX_PERCENT = "max_percent";
        public static final String COLUMN_GRADE = "grade";
    }

    public static class Test extends GradeCalculatorContract{
        public static final String TABLE_NAME = "test";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SUBJECTID = "subject_id";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_MIN_POINTS = "min_points";
        public static final String COLUMN_MAX_POINTS = "max_points";
        public static final String COLUMN_GRADING_SCHEME = "grading_scheme";
    }
}