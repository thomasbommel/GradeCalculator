package at.sallaberger.thomas.myapplication.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import at.sallaberger.thomas.myapplication.activities.superactivities.UserActivity;
import at.sallaberger.thomas.myapplication.debug.DebugToast;
import at.sallaberger.thomas.myapplication.R;
import model.database.tables.UserTable;

public class UserSettingsUserActivity extends UserActivity {

    private Button btnDeleteUser;
    private TextView tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        tvUsername = (TextView) findViewById(R.id.activity_user_settings_username);
        tvUsername.setText(getCurrentUser().getName());

        btnDeleteUser = (Button) findViewById(R.id.activity_user_settings_btn_delete_user);
        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(UserSettingsUserActivity.this)
                        .setTitle(getString(R.string.btn_delete_user))
                        .setMessage(getCurrentUser().getName()+" "+getString(R.string.really_delete_question))
                        .setIcon(android.R.drawable.ic_menu_delete)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DebugToast.showDebugToast(UserSettingsUserActivity.this,getCurrentUser().getName()+" deleted",Toast.LENGTH_SHORT);
                                UserTable.getInstance().deleteFromDataBase(MainActivity.getInstance().getDatabase(),getCurrentUser());
                                startActivity(new Intent(UserSettingsUserActivity.this,MainActivity.class));
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent showUserActivityIntent = new Intent(getApplicationContext(),ShowUserActivity.class);
        startActivity(showUserActivityIntent);
    }


}
