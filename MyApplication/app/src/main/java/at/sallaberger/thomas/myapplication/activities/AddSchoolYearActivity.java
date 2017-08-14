package at.sallaberger.thomas.myapplication.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import at.sallaberger.thomas.myapplication.activities.superactivities.UserActivity;
import at.sallaberger.thomas.myapplication.debug.DebugToast;
import at.sallaberger.thomas.myapplication.listeners.MyTextChangeListener;
import at.sallaberger.thomas.myapplication.R;
import model.database.objects.SchoolYear;
import model.database.tables.SchoolYearTable;
import model.database.tables.UserTable;

public class AddSchoolYearActivity extends UserActivity {

    private static final int MIN_SCHOOLYEARNAME_LENGTH = 1;

    private EditText etSchoolYearName;
    private Button btnOk;
    private Button btnCancel;
    private TextView tvSchoolYearNameValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_school_year);

        tvSchoolYearNameValid = (TextView) findViewById(R.id.activity_add_schoolyear_tv_schoolyearname_validate);
        btnOk = (Button) findViewById(R.id.activity_add_schoolyear_btn_ok);
        btnCancel = (Button) findViewById(R.id.activity_add_schoolyear_btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etSchoolYearName = (EditText) findViewById(R.id.activity_add_schoolyear_et_schoolyearname);

        etSchoolYearName.addTextChangedListener(new MyTextChangeListener() {
            @Override
            public void afterTextChanged(Editable s) {
                String wantedSchoolYearName = etSchoolYearName.getText().toString();
                if(wantedSchoolYearName.length()>=MIN_SCHOOLYEARNAME_LENGTH){
                    if(schoolYearAlreadyExists(wantedSchoolYearName)){
                        etSchoolYearName.setTextColor(Color.RED);
                        tvSchoolYearNameValid.setVisibility(View.VISIBLE);
                        tvSchoolYearNameValid.setText(R.string.schoolyear_already_exists);
                    }else{
                        etSchoolYearName.setTextColor(Color.BLACK);
                        tvSchoolYearNameValid.setVisibility(View.INVISIBLE);
                    }
                }else {
                    tvSchoolYearNameValid.setVisibility(View.VISIBLE);
                    etSchoolYearName.setTextColor(Color.RED);
                }
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String schoolYearNameEditTextValue = etSchoolYearName.getText().toString();
                if(schoolYearNameEditTextValue != null && schoolYearNameEditTextValue.length()>=MIN_SCHOOLYEARNAME_LENGTH){
                    if(!schoolYearAlreadyExists(schoolYearNameEditTextValue)) {
                        SchoolYear insertMe = new SchoolYear(schoolYearNameEditTextValue, getCurrentUser().getId());
                        SchoolYearTable.getInstance().insertIntoDataBase(MainActivity.getInstance().getDatabase(), insertMe);
                        DebugToast.showDebugToast(getApplicationContext(), R.string.schoolyear_was_added, Toast.LENGTH_SHORT);
                        finish();
                    }else{
                        DebugToast.showDebugToast(getApplicationContext(),R.string.schoolyear_already_exists,Toast.LENGTH_SHORT);
                    }
                }else{
                    DebugToast.showDebugToast(getApplicationContext(),R.string.error_schoolyear_invalid,Toast.LENGTH_SHORT);
                    etSchoolYearName.setTextColor(Color.RED);
                }
            }
        });
    }

    private boolean schoolYearAlreadyExists(String schoolYearName){
        SchoolYearTable table = SchoolYearTable.getInstance();
        SchoolYear schoolYear = table.getFromDataBase(MainActivity.getInstance().getDatabase(), new SchoolYear(schoolYearName, getCurrentUser().getId()));
        return schoolYear != null;
    }






}
