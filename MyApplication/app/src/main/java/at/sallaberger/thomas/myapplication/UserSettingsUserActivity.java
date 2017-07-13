package at.sallaberger.thomas.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import model.User;
import model.UserTable;

public class UserSettingsUserActivity extends UserActivity {

    private Button btnDeleteUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        btnDeleteUser = (Button) findViewById(R.id.activity_user_settings_btn_delete_user);
        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DebugToast.showDebugToast(getApplicationContext(),getCurrentUser().getName()+" deleted",Toast.LENGTH_SHORT);
                UserTable.getInstance().deleteFromDataBase(MainActivity.getInstance().getDatabase(),getCurrentUser());
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });





    }


}
