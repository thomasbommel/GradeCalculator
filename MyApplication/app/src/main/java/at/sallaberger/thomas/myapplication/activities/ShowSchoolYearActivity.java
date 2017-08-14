package at.sallaberger.thomas.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import at.sallaberger.thomas.myapplication.R;
import at.sallaberger.thomas.myapplication.activities.superactivities.SchoolYearActivity;
import at.sallaberger.thomas.myapplication.activities.superactivities.UserActivity;

public class ShowSchoolYearActivity extends SchoolYearActivity {

    private TextView tv_schoolyearName;

    private ImageButton btn_schoolyearSettings;
    private ImageButton btn_addSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_school_year);

        tv_schoolyearName = (TextView) findViewById(R.id.activity_show_schoolyear_schoolyearname);
        //tv_schoolyearName.setText("cr "+getCurrentSchoolYear().getName());

        btn_schoolyearSettings = (ImageButton) findViewById(R.id.activity_show_schoolyear_settings);
        btn_schoolyearSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showSchoolYearSettingsIntent = new Intent(getApplicationContext(),SchoolYearSettingsActivity.class);
                startActivity(showSchoolYearSettingsIntent);
            }
        });

        btn_addSubject = (ImageButton) findViewById(R.id.activity_show_schoolyear_btn_addSubject);
        btn_addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addSubjectIntent = new Intent(getApplicationContext(),AddSubjectActivity.class);
                startActivity(addSubjectIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_schoolyearName.setText(getCurrentSchoolYear().getName());
    }

    @Override
    public void onBackPressed() {
        Intent showUserActivityIntent = new Intent(getApplicationContext(),UserSettingsUserActivity.class);
        startActivity(showUserActivityIntent);
    }
}
