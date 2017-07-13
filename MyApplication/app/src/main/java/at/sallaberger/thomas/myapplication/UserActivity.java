package at.sallaberger.thomas.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.util.Log;
import android.widget.Toast;

import model.User;

/**
 * provides the functionality for getting the currently active User<br/>
 * <b>use getCurrentUser()</b> <br/><br/>
 * Created by Sallaberger on 09.07.2017.
 */
public abstract class UserActivity extends Activity{
    public static final String EXTRA_USER = "idOfCurrentUser";
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        currentUser = getCurrentUserFromIntent();
    }

    private User getCurrentUserFromIntent(){
        User user = getIntent().getParcelableExtra(EXTRA_USER);
        if(user==null){
            String userWasNull = "user was null";
            Log.e(this.getClass().toString(),userWasNull);
            DebugToast.makeText(this,userWasNull, Toast.LENGTH_LONG).show();
            throw new IllegalStateException(userWasNull);
        }
        else{
            Log.i(this.getClass().toString(),"currentUser is:"+user.getName());
            DebugToast.showDebugToast(this,"currentUser is:"+user.getName(), Toast.LENGTH_SHORT);
            return user;
        }
    }

    protected User getCurrentUser(){
        return currentUser;
    }

    @Override
    public void startActivity(Intent intent) {
        intent.putExtra(EXTRA_USER,currentUser);
        super.startActivity(intent);
    }
}
