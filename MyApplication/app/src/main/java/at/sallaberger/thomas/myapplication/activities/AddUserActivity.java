package at.sallaberger.thomas.myapplication.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import at.sallaberger.thomas.myapplication.debug.DebugToast;
import at.sallaberger.thomas.myapplication.listeners.MyTextChangeListener;
import at.sallaberger.thomas.myapplication.R;
import model.database.objects.User;
import model.database.tables.UserTable;

public class AddUserActivity extends Activity {

    private static final int MIN_USERNAME_LENGTH = 3;
    private Button btnOk;
    private Button btnCancel;
    private CheckBox cbRemindMe;
    private EditText etUsername;
    private TextView tvUsernameValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        etUsername = (EditText) findViewById(R.id.activity_add_user_et_username);
        tvUsernameValid = (TextView) findViewById(R.id.activity_add_user_tv_username_validate);
        cbRemindMe = (CheckBox) findViewById(R.id.activity_add_user_cb_remindMe);

        btnOk = (Button) findViewById(R.id.activity_add_user_btn_ok);
        btnCancel = (Button) findViewById(R.id.activity_add_user_btn_cancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNameEditTextValue = etUsername.getText().toString();

                if(userNameEditTextValue != null && userNameEditTextValue.length()>=MIN_USERNAME_LENGTH){
                    User insertMe = new User(etUsername.getText().toString());
                    if(UserTable.getInstance().getFromDataBase(MainActivity.getInstance().getDatabase(),insertMe)==null){
                        UserTable.getInstance().insertIntoDataBase(MainActivity.getInstance().getDatabase(), insertMe);
                        DebugToast.showDebugToast(getApplicationContext(),R.string.user_was_added,Toast.LENGTH_SHORT);
                        finish();
                    }else{
                        DebugToast.showDebugToast(getApplicationContext(),R.string.user_already_exists,Toast.LENGTH_SHORT);
                        etUsername.setTextColor(Color.RED);
                    }
                }else{
                    DebugToast.showDebugToast(getApplicationContext(),R.string.error_username_invalid,Toast.LENGTH_SHORT);
                    etUsername.setTextColor(Color.RED);
                }
            }
        });


        etUsername.addTextChangedListener(new MyTextChangeListener() {
            @Override
                public void afterTextChanged(Editable s) {
                    String wantedUserName = etUsername.getText().toString();
                    if(wantedUserName.length()>=4){
                        if(UserTable.getInstance().getFromDataBase(MainActivity.getInstance().getDatabase(),new User(wantedUserName)) != null){
                            etUsername.setTextColor(Color.RED);
                            tvUsernameValid.setVisibility(View.VISIBLE);
                            tvUsernameValid.setText(R.string.user_already_exists);
                        }else{
                            etUsername.setTextColor(Color.BLACK);
                            tvUsernameValid.setVisibility(View.INVISIBLE);
                        }
                    }else {
                        tvUsernameValid.setVisibility(View.VISIBLE);
                        etUsername.setTextColor(Color.RED);
                    }
                }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
