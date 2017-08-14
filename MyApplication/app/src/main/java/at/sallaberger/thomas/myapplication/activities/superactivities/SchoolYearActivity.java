package at.sallaberger.thomas.myapplication.activities.superactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import at.sallaberger.thomas.myapplication.debug.DebugToast;
import model.database.objects.SchoolYear;
import model.database.objects.User;

/**
 * Created by Sallaberger on 13.07.2017.
 */

public class SchoolYearActivity extends UserActivity {

    public static final String EXTRA_SCHOOLYEAR = "idOfCurrentSchoolYear";
    private SchoolYear currentSchoolYear;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        currentSchoolYear = getCurrentSchoolYearFromIntent();
    }

    private SchoolYear getCurrentSchoolYearFromIntent(){
        SchoolYear schoolYear = getIntent().getParcelableExtra(EXTRA_SCHOOLYEAR);
        if(schoolYear==null){
            String schoolYearWasNull = "schoolYear was null";
            Log.e(this.getClass().toString(),schoolYearWasNull);
            DebugToast.makeText(this,schoolYearWasNull, Toast.LENGTH_LONG).show();
            throw new IllegalStateException(schoolYearWasNull);
        }
        else{
            Log.i(this.getClass().toString(),"intent currentSchoolYear is:"+schoolYear.getName()+ " id:"+schoolYear.getId()+" userid: "+schoolYear.getUserId());
            DebugToast.showDebugToast(this,"intent currentSchoolYear is:"+schoolYear.getName()+ " id:"+schoolYear.getId()+" userid: "+schoolYear.getUserId(), Toast.LENGTH_SHORT);
            return schoolYear;
        }
    }

    protected SchoolYear getCurrentSchoolYear(){
        return currentSchoolYear;
    }

    @Override
    public void startActivity(Intent intent) {
        intent.putExtra(EXTRA_SCHOOLYEAR,currentSchoolYear);
        super.startActivity(intent);
    }

}
