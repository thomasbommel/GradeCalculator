package at.sallaberger.thomas.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import database.GradeCalculatorContract;
import database.GradeCalculatorDbHelper;
import model.User;
import recyclerview.UserListAdapter;

public class MainActivity extends Activity implements UserListAdapter.ListItemClickListener {

    private static UserListAdapter userListAdapter;
    private static RecyclerView rvUserList;
    private Toast toast;

    private Button btnAddUser, btnDeleteUser;

    public static SQLiteDatabase database;
    private static List<User> userList = new ArrayList<>();

    private static final boolean DELETE_OLD_USERDATA_FOR_TESTING = false;
    private static final boolean ADD_TEST_USER_DATA = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(this.getLocalClassName(),"onCreate");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        rvUserList = (RecyclerView) findViewById(R.id.activity_main_rv_user_list);
        LinearLayoutManager userListLayoutManager = new LinearLayoutManager(this);
        rvUserList.setLayoutManager(userListLayoutManager);
        rvUserList.setHasFixedSize(true);


        btnAddUser = (Button) findViewById(R.id.activity_main_btn_addUser);
        btnDeleteUser = (Button) findViewById(R.id.activity_main_btn_deleteUser);

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openAddUserActivityIntent = new Intent(getApplicationContext(),AddUserActivity.class);
                startActivity(openAddUserActivityIntent);
            }
        });

        /*
        btnDeleteUser.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        User deleteMe = new User("deleteme");
                        deleteMe.deleteFromDatabase(database);
                        userListAdapter.refresh(database);
                    }
                }
        );
        */

        GradeCalculatorDbHelper dbHelper = new GradeCalculatorDbHelper(this);

        database = dbHelper.getWritableDatabase();

        /*
        if (DELETE_OLD_USERDATA_FOR_TESTING) {
            database.execSQL("DROP TABLE "+GradeCalculatorContract.User.TABLE_NAME);
            database.execSQL("DROP TABLE "+GradeCalculatorContract.Schoolyear.TABLE_NAME);
        }

        if (ADD_TEST_USER_DATA) {
            userList = getMockUserList();
            for (User user : userList) {
                user.insertIntoDatabase(database);
            }
        }
        */

        userListAdapter = new UserListAdapter(this, userList);
        rvUserList.setAdapter(userListAdapter);
        refreshMainActivity();
    }

    @Override
    protected void onResume() {
        Log.i(this.getLocalClassName(),"onResume");
        super.onResume();

        refreshMainActivity();
    }

    public static void refreshMainActivity(){
        userListAdapter.refresh(database);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        if (toast != null) {
            toast.cancel();
        }
        String toastMessage = "Item #" + clickedItemIndex + ", "+userList.get(clickedItemIndex).getName()+" clicked.";
        toast = Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT);
        toast.show();
    }

    public SQLiteDatabase getDatabase(){
        return database;
    }





}
