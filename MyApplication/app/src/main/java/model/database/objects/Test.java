package model.database.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;

import model.database.tables.DataBaseTableEntity;
import model.database.tables.SchoolYearTable;
import model.database.tables.UserTable;

/**
 * Created by Sallaberger on 24.06.2017.
 */

public class Test extends DataBaseTableEntity{

    private String id;
    private String name;
    private String subjectId;
    private Date date;
    private float minPoints;
    private float maxPoints;
    private String gradingSchemeId;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(subjectId);
        dest.writeLong(date.getTime());
        dest.writeFloat(minPoints);
        dest.writeFloat(maxPoints);
        dest.writeString(gradingSchemeId);
    }

    public static final Parcelable.Creator<Test> CREATOR = new Parcelable.Creator<Test>() {
        public Test createFromParcel(Parcel in) {
            return new Test(in);
        }

        public Test[] newArray(int size) {
            return new Test[size];
        }
    };

    private Test(Parcel in){
        id = in.readString();
        name = in.readString();
        subjectId = in.readString();
        date = new Date(in.readLong());
        minPoints = in.readFloat();
        maxPoints = in.readFloat();
        gradingSchemeId = in.readString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public Date getDate() {
        return date;
    }

    public float getMinPoints() {
        return minPoints;
    }

    public float getMaxPoints() {
        return maxPoints;
    }

    public String getGradingSchemeId() {
        return gradingSchemeId;
    }
}
