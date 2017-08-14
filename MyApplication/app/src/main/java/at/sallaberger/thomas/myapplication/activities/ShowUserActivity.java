package at.sallaberger.thomas.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import at.sallaberger.thomas.myapplication.activities.superactivities.SchoolYearActivity;
import at.sallaberger.thomas.myapplication.activities.superactivities.UserActivity;
import at.sallaberger.thomas.myapplication.debug.DebugToast;
import at.sallaberger.thomas.myapplication.R;
import model.database.objects.SchoolYear;
import model.database.tables.SchoolYearTable;
import at.sallaberger.thomas.myapplication.recyclerview.adapters.SchoolYearListAdapter;

public class ShowUserActivity extends UserActivity implements SchoolYearListAdapter.ListItemClickListener {

    private SchoolYearListAdapter schoolYearListAdapter;
    private RecyclerView rvSchoolYearList;

    private ImageButton btn_userSettings;
    private ImageButton btn_addSchoolYear;
    private TextView tv_username;

    private List<SchoolYear> schoolYearList = new ArrayList<>();

    public void refreshShowUserActivity(){
        schoolYearListAdapter.refresh(MainActivity.getInstance().getDatabase(), getCurrentUser());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);

        rvSchoolYearList = (RecyclerView) findViewById(R.id.activity_show_user_rv_schoolyear_list);
        LinearLayoutManager schoolYearListManager = new LinearLayoutManager(this);
        rvSchoolYearList.setLayoutManager(schoolYearListManager);
        rvSchoolYearList.setHasFixedSize(true);
        tv_username = (TextView) findViewById(R.id.activity_show_user_username);


        btn_userSettings = (ImageButton) findViewById(R.id.activity_show_user_settings);
        btn_userSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showUserSettingsIntent = new Intent(getApplicationContext(),UserSettingsUserActivity.class);
                startActivity(showUserSettingsIntent);
            }
        });

        btn_addSchoolYear = (ImageButton) findViewById(R.id.activity_show_user_btn_addYear);
        btn_addSchoolYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addSchoolyearIntent = new Intent(getApplicationContext(),AddSchoolYearActivity.class);
                startActivity(addSchoolyearIntent);
            }
        });

        schoolYearListAdapter = new SchoolYearListAdapter(this, schoolYearList);
        rvSchoolYearList.setAdapter(schoolYearListAdapter);
        refreshShowUserActivity();
        //tv_username.setText("cre "+getCurrentUser().getName()+" "+getCurrentUser().getId()+". ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(this.getLocalClassName(),"onResume");
        refreshShowUserActivity();
        tv_username.setText(getCurrentUser().getName());
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        schoolYearList = SchoolYearTable.getInstance().getAllFromDataBase(MainActivity.getInstance().getDatabase());

        Log.d(this.getClass().toString()," schoolYearListsize "+schoolYearList.size());

        String toastMessage = "Item #" + clickedItemIndex + ", "+schoolYearList.get(clickedItemIndex).getName()+" clicked.";
        DebugToast.showDebugToast(this,toastMessage, Toast.LENGTH_SHORT);

       Intent openShowSchoolYearIntent  = new Intent(getApplicationContext(),ShowSchoolYearActivity.class);
       openShowSchoolYearIntent.putExtra(SchoolYearActivity.EXTRA_SCHOOLYEAR,schoolYearList.get(clickedItemIndex));
       startActivity(openShowSchoolYearIntent);
    }

    @Override
    public void onBackPressed() {
        Intent showMainActivityIntent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(showMainActivityIntent);
    }
}
