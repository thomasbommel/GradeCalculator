package at.sallaberger.thomas.myapplication.activities;

import android.os.Bundle;

import at.sallaberger.thomas.myapplication.R;
import at.sallaberger.thomas.myapplication.activities.superactivities.SchoolYearActivity;

public class AddSubjectActivity extends SchoolYearActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
    }
}
