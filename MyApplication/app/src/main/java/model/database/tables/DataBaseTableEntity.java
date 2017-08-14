package model.database.tables;

import android.os.Parcelable;

/**
 * Created by Sallaberger on 07.07.2017.
 */

public abstract class DataBaseTableEntity implements Parcelable{

    /**
     * never use this method
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

}
