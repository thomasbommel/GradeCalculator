package model.database.objects;

import android.os.Parcel;
import android.os.Parcelable;

import model.database.tables.DataBaseTableEntity;

/**
 * Created by Sallaberger on 07.07.2017.
 */

public class SchoolYear extends DataBaseTableEntity {
    private String id;
    private String name;
    private String userId;

    public SchoolYear(String id, String name, String userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    /**
     * <b>use only at creation (!!!)</b>
     * @param name
     * @param userId
     */
    public SchoolYear(String name, String userId) {
        this.name = name;
        this.userId = userId;
    }

    public String getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getUserId(){
        return this.userId;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(userId);
    }

    public static final Parcelable.Creator<SchoolYear> CREATOR = new Parcelable.Creator<SchoolYear>() {
        public SchoolYear createFromParcel(Parcel in) {
            return new SchoolYear(in);
        }

        public SchoolYear[] newArray(int size) {
            return new SchoolYear[size];
        }
    };

    private SchoolYear(Parcel in){
        id = in.readString();
        name = in.readString();
        userId = in.readString();
    }
}
