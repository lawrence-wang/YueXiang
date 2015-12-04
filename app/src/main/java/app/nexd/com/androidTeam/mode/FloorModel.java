package app.nexd.com.androidTeam.mode;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lawrence on 2015/12/4.
 */
public class FloorModel implements Parcelable {
    private String floorId;
    private String floorName;

    public FloorModel(String floorId, String floorName) {
        this.floorId = floorId;
        this.floorName = floorName;
    }

    public FloorModel() {
    }

    public String getFloorSvg() {
        return this.floorId + ".svg";
    }

    public String getFloorName() {
        return floorName;
    }

    public String getFloorId() {
        return floorId;
    }

    protected FloorModel(Parcel in) {
        floorId = in.readString();
        floorName = in.readString();
    }

    public static final Creator<FloorModel> CREATOR = new Creator<FloorModel>() {
        @Override
        public FloorModel createFromParcel(Parcel in) {
            return new FloorModel(in);
        }

        @Override
        public FloorModel[] newArray(int size) {
            return new FloorModel[size];
        }
    };

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(floorId);
        dest.writeString(floorName);
    }
}
