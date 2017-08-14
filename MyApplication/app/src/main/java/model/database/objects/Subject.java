package model.database.objects;

import android.os.Parcel;
import android.os.Parcelable;

import model.database.tables.DataBaseTableEntity;

/**
 * Created by Sallaberger on 24.06.2017.
 */

public class Subject extends DataBaseTableEntity{

    private String id;
    private String name;
    private String defaultGradingSchemeId;
    private String schoolyearId;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(defaultGradingSchemeId);
        dest.writeString(schoolyearId);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Subject> CREATOR = new Parcelable.Creator<Subject>() {
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Subject(Parcel in) {
        id = in.readString();
        name = in.readString();
        defaultGradingSchemeId = in.readString();
        schoolyearId = in.readString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDefaultGradingSchemeId() {
        return defaultGradingSchemeId;
    }

    public String getSchoolyearId() {
        return schoolyearId;
    }
}
