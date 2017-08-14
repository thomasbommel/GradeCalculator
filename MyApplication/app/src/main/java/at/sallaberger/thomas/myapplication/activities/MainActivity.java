package at.sallaberger.thomas.myapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import at.sallaberger.thomas.myapplication.R;
import at.sallaberger.thomas.myapplication.activities.superactivities.UserActivity;
import at.sallaberger.thomas.myapplication.debug.DebugToast;
import model.database.utils.GradeCalculatorDbHelper;
import model.database.objects.User;
import model.database.tables.UserTable;
import at.sallaberger.thomas.myapplication.recyclerview.adapters.UserListAdapter;

public class MainActivity extends Activity implements UserListAdapter.ListItemClickListener {

    private  UserListAdapter userListAdapter;
    private  RecyclerView rvUserList;


    private ImageButton btnAddUser;

    private  SQLiteDatabase database;
    private  List<User> userList = new ArrayList<>();

    //private static final boolean DELETE_OLD_USERDATA_FOR_TESTING = false;
    //private static final boolean ADD_TEST_USER_DATA = false;

    private static MainActivity instance;

    public static MainActivity getInstance(){
        if(instance == null){
            instance = new MainActivity();
        }
        return instance;
    }

    public SQLiteDatabase getDatabase(){
        return this.database;
    }

    public void refreshMainActivity(){
        userListAdapter.refresh(database);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(this.getLocalClassName(),"onCreate");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        GradeCalculatorDbHelper dbHelper = new GradeCalculatorDbHelper(this);
        database = dbHelper.getWritableDatabase();

        rvUserList = (RecyclerView) findViewById(R.id.activity_main_rv_user_list);
        LinearLayoutManager userListLayoutManager = new LinearLayoutManager(this);
        rvUserList.setLayoutManager(userListLayoutManager);
        rvUserList.setHasFixedSize(true);


        btnAddUser = (ImageButton) findViewById(R.id.activity_main_ibtn_addUser);

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openAddUserActivityIntent = new Intent(getApplicationContext(),AddUserActivity.class);
                startActivity(openAddUserActivityIntent);
            }
        });

        userListAdapter = new UserListAdapter(this, userList);
        rvUserList.setAdapter(userListAdapter);
        refreshMainActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(this.getLocalClassName(),"onResume");
        instance = this;
        refreshMainActivity();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        userList = UserTable.getInstance().getAllFromDataBase(database);

        Log.d(this.getClass().toString()," userlistsize "+userList.size());

        String toastMessage = "Item #" + clickedItemIndex + ", "+userList.get(clickedItemIndex).getName()+" clicked.";
        DebugToast.showDebugToast(this,toastMessage,Toast.LENGTH_SHORT);

        Intent openShowUserIntent  = new Intent(getApplicationContext(),ShowUserActivity.class);
        openShowUserIntent.putExtra(UserActivity.EXTRA_USER,userList.get(clickedItemIndex));
        startActivity(openShowUserIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(this.getClass().toString(),"======== ON DESTROY =========");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(this.getClass().toString(),"========== ON STOP =========");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
