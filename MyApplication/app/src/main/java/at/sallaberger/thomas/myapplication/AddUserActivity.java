package at.sallaberger.thomas.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import model.User;

public class AddUserActivity extends Activity {

    private Button btnOk,btnCancel;
    private CheckBox cbRemindMe;
    private EditText etUsername;
    private TextView tvUsernameValid;

    private final int MIN_USERNAME_LENGTH = 4;

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
                    User deleteMe = new User(etUsername.getText().toString());
                    deleteMe.insertIntoDatabase(MainActivity.database);
                    Toast.makeText(getApplicationContext(),R.string.user_was_added,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),R.string.error_username_invalid,Toast.LENGTH_SHORT).show();
                    etUsername.setTextColor(Color.RED);
                }
            }
        });

        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etUsername.getText().toString().length()>=4){
                    tvUsernameValid.setVisibility(View.INVISIBLE);
                    etUsername.setTextColor(Color.BLACK);
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
