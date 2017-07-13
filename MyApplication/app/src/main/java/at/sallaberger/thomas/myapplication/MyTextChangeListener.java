package at.sallaberger.thomas.myapplication;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * use for editText, you just have to implement the afterTextChanged(Editable s) method
 *
 * Created by Sallaberger on 13.07.2017.
 */
public abstract class MyTextChangeListener implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //not needed
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //not needed
    }
}
