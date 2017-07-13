package model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import at.sallaberger.thomas.myapplication.MainActivity;
import database.DatabaseUtils;

/**
 * Created by Sallaberger on 24.06.2017.
 */

public class User extends DataBaseTableEntity {

    private String id;
    private String name;

    /**
     * <b>use only at create User</b>
     * @param name
     */
    public User(String name){
        this.name = name;
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(id);
        dest.writeString(name);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private User(Parcel in) {
        id = in.readString();
        name = in.readString();
    }
}
