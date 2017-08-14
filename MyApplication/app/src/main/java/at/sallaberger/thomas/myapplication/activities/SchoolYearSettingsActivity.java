package at.sallaberger.thomas.myapplication.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import at.sallaberger.thomas.myapplication.R;
import at.sallaberger.thomas.myapplication.activities.superactivities.SchoolYearActivity;
import at.sallaberger.thomas.myapplication.debug.DebugToast;
import model.database.tables.SchoolYearTable;
import model.database.tables.UserTable;

public class SchoolYearSettingsActivity extends SchoolYearActivity {

    private Button btnDeleteSchoolYear;
    private TextView tvSchoolYearName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_year_settings);

        tvSchoolYearName = (TextView) findViewById(R.id.activity_school_year_settings_schoolyearname);
        tvSchoolYearName.setText(getCurrentSchoolYear().getName());

        btnDeleteSchoolYear = (Button) findViewById(R.id.activity_school_year_settings_btn_delete_schoolyear);
        btnDeleteSchoolYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SchoolYearSettingsActivity.this)
                        .setTitle(getString(R.string.btn_delete_schoolyear))
                        .setMessage(getCurrentSchoolYear().getName()+" "+getString(R.string.really_delete_question))
                        .setIcon(android.R.drawable.ic_menu_delete)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                SchoolYearTable.getInstance().deleteFromDataBase(MainActivity.getInstance().getDatabase(),getCurrentSchoolYear());
                                DebugToast.showDebugToast(getApplicationContext(),getCurrentSchoolYear().getName()+" deleted", Toast.LENGTH_SHORT);
                                startActivity(new Intent(getApplicationContext(),ShowUserActivity.class));
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });




    }



}
