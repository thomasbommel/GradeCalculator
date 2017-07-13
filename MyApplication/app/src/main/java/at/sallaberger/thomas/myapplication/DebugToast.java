package at.sallaberger.thomas.myapplication;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Sallaberger on 09.07.2017.
 */

public class DebugToast extends Toast {

    private static Toast toast;
    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public DebugToast(Context context) {
        super(context);
    }

    public static void showDebugToast(Context context, CharSequence text, int duration){
        if(toast!=null){
            toast.cancel();
        }

        if(context.getResources().getBoolean(R.bool.debug_mode)){
            Log.i(DebugToast.class.toString(),"TOAST: "+text);
            toast = Toast.makeText(context,text,duration);
            toast.show();
        }else{
            Log.i(DebugToast.class.toString(),"debug disabled");
        }
    }

    public static void showDebugToast(Context context, @StringRes int resId, int duration){
        showDebugToast(context, context.getResources().getText(resId), duration);
    }
}
